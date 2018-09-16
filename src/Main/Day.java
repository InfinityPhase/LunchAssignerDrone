package Main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;

public class Day implements Comparable<Day> {
	public List<Person> assignments; // People who should be here
	public List<Person> backups;
	public List<Person> present; // People who were actually here
	private LocalDate date;

	public Day( LocalDate date, List<Person> assingments, List<Person> backups, List<Person> present ) {
		this.assignments = assingments;
		this.backups = backups;
		this.present = present;
		this.date = date;
	}
	
	public Day( LocalDate date, List<Person> assingments, List<Person> backups, String status ) {		
		this( date, assingments, backups, calcPresent( appendLists( assingments, backups ), status ) );
	}

	public Day( LocalDate date, List<Person> assingments ) {
		this( date, assingments, new ArrayList<Person>(), new ArrayList<Person>() );
	}

	public Day( LocalDate date ) {
		this( date, new ArrayList<Person>() );
	}

	public DayOfWeek getDayOfWeek() {
		return date.getDayOfWeek();
	}

	public boolean isDone( LocalDate currentDate ) {
		return ( currentDate.isAfter(date) ? true : false );
	}

	public LocalDate getDate() {
		return date;
	}
	
	public boolean wasPresent( Person person ) {
		return present.contains( person );
	}
	
	public Person[] present() {
		return present.toArray( new Person[ present.size() ] );
	}
	
	/* UTILS */
	
	private static List<Person> calcPresent( List<Person> allPeople, String status ) {
		List<Person> present = new ArrayList<>();
		
		for( int i = 0, n = allPeople.size(); i < n; ++i ) {
			if( status.length() > i && status.charAt(i) == 'y' ) {
				present.add( allPeople.get(i) );
			}
		}
		
		return present;
	}
	
	private static List<Person> appendLists( List<Person> first, List<Person> second ) {
		// Ugh, I shouldn't have to do this...
		first.addAll(second);
		return first;
	}
	
	/* Comparator function */

	@Override
	public int compareTo( Day day ) {
		// Sorts by date, then number of people assigned, then number of people present
		int comp = this.date.compareTo( day.date );
		
		if( comp != 0 ) {
			return comp;
		} else {
			comp = assignments.size() - day.assignments.size();
			
			if( comp != 0 ) {
				return comp;
			} else {
				comp = backups.size() - day.backups.size();
				
				if( comp != 0 ) {
					return comp;
				} else {
					return present.size() - day.present.size();
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return "Day{date=" + date.toString() + ", assignments=" + assignments.toString() + ", backups=" + backups.toString() + ", present=" + present.toString() + "}";
	}

}
