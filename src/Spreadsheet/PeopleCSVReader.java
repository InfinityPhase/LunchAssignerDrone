package Spreadsheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import Main.Assigner;
import Main.Person;

public class PeopleCSVReader {
	// TODO Use @CSVBindByName() annotation to do all this shit
	private String fileName;

	private int firstNameIndex;
	private int lastNameIndex;
	private int emailIndex;
	private int per3Index;
	private int per4Index;
	private int avalibleIndex;
	private int noteIndex;
	private int leadershipIndex;


	public PeopleCSVReader( String fileName ) {
		this.fileName = fileName;

		initIndex();
	}

	public PeopleCSVReader( File file ) {
		this( file.getAbsolutePath() );
	}

	@SuppressWarnings("unused")
	public Person[] getPeople() {
		List<Person> people = new ArrayList<>();

		try( CSVReader reader = new CSVReader( new FileReader( fileName ) ); ) {
			String[] line;
			boolean first = true;

			while( ( line = reader.readNext() ) != null ) {
				String nameLast = "";
				String nameFirst = "";
				String email = "";
				String per3 = "";
				String per4 = "";
				String avalible = "";
				String note = "";
				String leadership = "";

				if( first ) {
					first = false;
					continue;
				}

				for( int i = 0; i < line.length; ++i ) {
					String s = line[i];

					if( i == firstNameIndex ) {
						nameFirst = s;
					} else if( i == lastNameIndex ) {
						nameLast = s;
					} else  if( i == emailIndex ) {
						email = s;
					} else if( i == per3Index ) {
						per3 = s;
					} else if( i == per4Index ) {
						per4 = s;
					} else if( i == noteIndex ) {
						note = s;
					} else if( i == avalibleIndex ) {
						avalible = s; // TODO MAKE WORK
					} else if( i == leadershipIndex ) {
						leadership = s;
					}
				}

				people.add( new Person( nameFirst + " " + nameLast, Assigner.convertDays( avalible ), email, note, per3, per4, isLeadership(leadership) ) );

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return people.toArray( new Person[ people.size() ] );		
	}

	public boolean initIndex() {
		try( CSVReader reader = new CSVReader( new FileReader( fileName ) ); ) {
			String[] line = reader.readNext(); // Read the header line

			for( int i = 0; i < line.length; ++i ) {
				String s = line[i];

				if( s.toLowerCase().contains("first") ) {
					firstNameIndex = i;
				} else if( s.toLowerCase().contains("last") ) {
					lastNameIndex = i;
				} else if( s.toLowerCase().contains("email") ) {
					emailIndex = i;
				} else if( s.toLowerCase().contains("third") ) {
					per3Index = i;
				} else if( s.toLowerCase().contains("fourth") ) {
					per4Index = i;
				} else if( s.toLowerCase().contains("days") ) {
					avalibleIndex = i;
				} else if( s.toLowerCase().contains("notes") ) {
					noteIndex = i;
				} else if( s.toLowerCase().contains("leadership") ) {
					leadershipIndex = i;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}
	
	private boolean isLeadership( String line ) {
		if( line.equalsIgnoreCase( "y" ) || line.equalsIgnoreCase( "yes" ) || line.equalsIgnoreCase( "leadership" ) ) {
			return true;
		}
		
		return false;
	}

}
