package Main;

import java.io.File;
import java.lang.reflect.Field;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Spreadsheet.AssignmentCSVReader;
import Spreadsheet.AssignmentCSVWriter;
import Spreadsheet.CSVDay;
import Spreadsheet.PeopleCSVReader;

@SuppressWarnings("unused")
public class Assigner {
	/*
	 * Hello there future maintainer! You have my condolences. While this program could be
	 * considerably worse, there are... Many aspects that can be improved. To alter the
	 * number of people, for example, is more complicated than simply changing that
	 * inviting looking integer in the constants file. You also must add/remove variables
	 * from CSVDay, from the DEFAULT_HEADER, and from AssignmentCSVWriter/Reader. Possibly
	 * more.
	 * 
	 * Furthermore, the use of the name of each person is important to keep consisent.
	 * When fetching data from the CSV, keep the order of the name in mind. If it is
	 * changed, you may create extra people by accident. Don't do that.
	 * 
	 * The design goal of this program was "Minimum Viable Product", and the design
	 * reflects this more than I would prefer. As I said, I'm sorry. Good Luck.
	 */

	// wget --no-check-certificate --output-document=LunchPeople.csv 'https://docs.google.com/spreadsheet/ccc?key=<KEY_HERE>&output=csv'

	public static List<Person> people;
	static List<Day> days;
	static Map<DayOfWeek, Double> ratio;

	public static void main( String[] args ) {
		long dayCount = 0;

		// Init lists
		days = new ArrayList<Day>();
		people = new ArrayList<Person>();

		// Init CSV stuff
		PeopleCSVReader peopleData = new PeopleCSVReader( Constants.PEOPLE_CSV );
		AssignmentCSVWriter assignmentWriter = new AssignmentCSVWriter( Constants.ASSIGNMENT_CSV );
		AssignmentCSVReader assignmentReader = new AssignmentCSVReader( Constants.PREV_ASSIGNMENT_CSV );

		/* LOAD DAYS */

		dayCount = Constants.DATE_START.until( Constants.DATE_END, ChronoUnit.DAYS );

		// Init all the days until the Constants.end date
		for( long i = 0; i < dayCount; ++i ) {
			days.add( new Day( Constants.DATE_START.plusDays(i) ) );
		}

		// Remove weekends
		List<Day> toRemove = new ArrayList<Day>();
		for( Day d : days ) {
			if( contains( Constants.DAY_NO_ROTATIONS, d.getDayOfWeek() ) ) {
				toRemove.add( d );
			}
		}
		days.removeAll( toRemove );

		System.out.println("TOTAL DAYS: " + days.size());

		/* LOAD PEOPLE */

		Person[] tmpPeople = peopleData.getPeople();
		for( Person p : tmpPeople ) {
			people.add( p );
		}

		people = removeDuplicatePeople( people );

		System.out.println("TOTAL PEOPLE: " + people.size());

		// Load previous data into person storage
		File f = new File( Constants.PREV_ASSIGNMENT_CSV);
		if(  f.exists() ) {
			CSVDay[] prevAssignments = assignmentReader.getAllDayData();
			for( CSVDay d : prevAssignments ) {
				Day dd = d.getDay();

				// Who needs efficency, amirite?
				for( Person p : dd.assignments ) {
					if( dd.present.contains( p ) ) {
						p.present(dd);
					} else {
						p.absent(dd);
					}
				}

				for( Person p : dd.backups ) {
					if( dd.present.contains( p ) ) {
						p.present(dd);
					} else {
						p.absent(dd);
					}
				}
			}
		}

		/* CALCULATE ASSIGNMENTS OF DAYS */

		ratio = calculateDayOfWeekRatio( people );

		// Assign days in a poor, disorderly fasion
		int currentPerson = 0; // Used to try and balance assignments
		double totalReliability = sumReliability( people );
		for( Day d : days ) {
			for( int i = 0; i < people.size(); ++i ) {
				Person p = people.get(i);
				double ratio = p.getScore(Constants.DATE_TODAY) / totalReliability;
				if( p.avalible( d.getDate() ) ) {
					if( ( 1.0 * p.assignedDays.size() / ( days.size() * Constants.MINIMUM_PEOPLE ) ) <= ratio ) {
						d.assignments.add( p );
						p.assignedDays.add( d.getDate() );

						if( currentPerson >= people.size() - 1 ) {
							currentPerson = 0; // Wrap around, don't go out of index
						} else {
							++currentPerson; // Don't assign this person for a little while
						}
					}
				}

				if( d.assignments.size() >= Constants.MINIMUM_PEOPLE ) {
					break; // Don't put more than 3 people on each day
				}

			}
		}

		// Save results
		for( Day d : days ) {
			assignmentWriter.addDay( d );
		}
		//assignmentWriter.commitRecords(); // Writes to file

//		printAssignmentsLessThan( days, 2 );
		printAssignmentRange( people );
	}
	
	/* INFO PRINTERS */

	private static void printAssignments( List<Day> days ) {
		for( Day d : days ) {
			System.out.println("DAY: " + d.getDayOfWeek());
			System.out.println("==> PEOPLE:");
			for( Person p : d.assignments ) {
				System.out.println("====> NAME: " + p.name);
			}
		}
	}

	private static void printAssignments( List<Day> days, DayOfWeek weekday ) {
		for( Day d : days ) {
			if( d.getDayOfWeek() == weekday ) {
				System.out.println("DAY: " + d.getDayOfWeek());
				System.out.println("==> PEOPLE:");
				for( Person p : d.assignments ) {
					System.out.println("====> NAME: " + p.name);
				}
			}
		}
	}

	private static void printAssignmentsLessThan( List<Day> days, int assignments ) {
		for( Day d : days ) {
			if( d.assignments.size() <= assignments ) {
				System.out.println("DAY: " + d.getDate().toString() + " (" + d.getDayOfWeek() + ")");
				System.out.println("==> PEOPLE:");
				for( Person p : d.assignments ) {
					System.out.println("====> NAME: " + p.name);
				}
			}
		}
	}
	
	private static void printAssignmentRange( Person p ) {
		System.out.println("PERSON: " + p.name);
		List<LocalDate> days = p.assignedDays;
		Collections.sort( days );
		
		System.out.println("=> first: " + days.get(0));
		System.out.println("=> last: " + days.get(days.size()-1));
//		System.out.println("=> Range: ");

	}
	
	private static void printAssignmentRange( List<Person> ppl ) {
		for( Person p : ppl ) {
			printAssignmentRange(p);
		}
	}

	private static void printPeople( List<Person> people ) {
		System.out.println("PEOPLE IN SYSTEM: " + people.size());
		for( Person p : people ) {
			System.out.println("==> NAME: " + p.name);
			System.out.println("====> RATE: " + p.getScore(Constants.DATE_TODAY));
			System.out.println("====> DAYS: " + p.assignedDays.size());
		}
	}

	private static void printPeople( List<Person> people, DayOfWeek weekday ) {
		System.out.println("PEOPLE IN SYSTEM: " + people.size());
		for( Person p : people ) {
			if( p.avalible( weekday ) ) {
				System.out.println("==> NAME: " + p.name);
				System.out.println("====> RATE: " + p.getScore(Constants.DATE_TODAY));
				System.out.println("====> DAYS: " + p.assignedDays.size());
			}
		}
	}
	
	private static void printPersonAssignmentDates( Person p ) {
		System.out.println(p.name);
		for( LocalDate d : p.assignedDays ) {
			System.out.println("=>" + d.toString());
		}
	}
	
	private static void printAssignmentsofPerson( List<Person> people ) {
		for( Person p : people ) {
			printPersonAssignmentDates(p);
		}
	}

	private static void printRatio( Map<DayOfWeek, Double> ratio ) {
		for( DayOfWeek day : ratio.keySet() ) {
			System.out.println("DAY: " + day.toString());
			System.out.println("RATIO: " + ratio.get(day));
		}
	}
	
	/* UTILITIES */
	
	private static double sumReliability( List<Person> people ) {
		double result = 0;

		for( Person p : people ) {
			result += p.getScore();
		}

		return result;
	}

	private static List<Person> removeDuplicatePeople( List<Person> people ) {
		List<Person> remove = new ArrayList<>();

		for( int i = 0, n = people.size(); i < n; ++i ) {
			for( int j = i+1; j < n; ++j ) {
				if( people.get(i).name.equalsIgnoreCase( people.get(j).name ) ) {
					remove.add( people.get(j) ); // Remove the duplicate occurance
				}
			}
		}

		people.removeAll( remove );

		return people;
	}

	private static Map<DayOfWeek, Double> calculateDayOfWeekRatio( List<Person> people ) {
		Map<DayOfWeek, Double> result = new HashMap<>();
		int totalCount = 0;

		for( Person p : people ) {
			for(DayOfWeek day : p.avalibleDays) {
				// We cannot add 1 to null, so check if it is null first
				result.put( day, ( result.get(day)!=null ? result.get(day) : 0 ) + 1 );
				++totalCount;
			}
		}

		for( DayOfWeek day : result.keySet() ) {
			result.put( day, result.get(day) / totalCount );
		}

		return result;
	}

	public static boolean contains( DayOfWeek[] array, DayOfWeek element ) {
		for( DayOfWeek d : array ) {
			if( d.equals( element ) ) {
				return true;
			}
		}

		return false;
	}

	/* UTILITIES USED ELSEWHERE */

	public static String[] commaSeperation( String line ) {
		List<String> words = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		boolean quoted = false;
		for( int i = 0, n = line.length(); i < n; i++ ) { 
			char c = line.charAt(i);

			if( c == '"' || c == '\'' ) {
				quoted = !quoted;
				continue;
			}

			if( quoted ) {
				sb.append(c);
			} else {
				if( c == ',' ) {
					words.add( sb.toString() );
					sb.delete( 0, sb.length() ); // Clear the stringbuilder
					++i; // Skip the next space
				} else {
					sb.append(c);
				}
			}

			if( i == ( n - 1 ) ) {
				words.add( sb.toString() );
			}
		}

		return words.toArray( new String[ words.size() ] );
	}

	public static DayOfWeek[] convertDays( String days ) {
		return convertDays( commaSeperation( days ) );
	}

	public static DayOfWeek[] convertDays( String[] days ) {
		DayOfWeek[] result = new DayOfWeek[ days.length ];
		int current = 0;

		for( String s : days ) {
			switch( s.toLowerCase() ){
			case "monday":
				result[current] = DayOfWeek.MONDAY;
				++current;
				break;
			case "tuesday":
				result[current] = DayOfWeek.TUESDAY;
				++current;
				break;
			case "wednesday":
				result[current] = DayOfWeek.WEDNESDAY;
				++current;
				break;
			case "thursday":
				result[current] = DayOfWeek.THURSDAY;
				++current;
				break;
			case "friday":
				result[current] = DayOfWeek.FRIDAY;
				++current;
				break;
			case "saturday":
				result[current] = DayOfWeek.SATURDAY;
				++current;
				break;
			case "sunday":
				result[current] = DayOfWeek.SUNDAY;
				++current;
				break;
			default:
				result[current] = DayOfWeek.FRIDAY;
				++current;
				break;
			}
		}

		return result;
	}

	public static String formattedToString( Object o ) { // Kinda broken
		StringBuilder sb = new StringBuilder(o.getClass().getSimpleName());

		sb.append("{");

		for( Field f : o.getClass().getDeclaredFields() ) {
			sb.append( f.getName() );

			try {
				sb.append( "=" + f.get( o ).toString() );
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

			sb.append(", "); // Add seperator
		}

		return sb.substring( 0, ( sb.length() - 2 ) ) + "}";
	}

	public static Person searchPersonList( List<Person> list, String name ) {
		for( Person p : list ) {
			if( p.name.compareTo( name ) == 0 ) {
				return p;
			}
		}

		return null;
	}
}
