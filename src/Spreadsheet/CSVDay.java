package Spreadsheet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import  com.univocity.parsers.annotations.*;

import Main.Assigner;
import Main.Day;
import Main.Person;

public class CSVDay {
	
	@Parsed(field = "Date", defaultNullRead = "")
	private String date;
	
	@Parsed(field = "Day Of Week", defaultNullRead = "")
	private String dayOfWeek;

	@Parsed(field = "Person A", defaultNullRead = "")
	private String personA;

	@Parsed(field = "Person B", defaultNullRead = "")
	private String personB;

	@Parsed(field = "Person C", defaultNullRead = "")
	private String personC;

	@Parsed(field = "Backup A", defaultNullRead = "")
	private String backupA;

	@Parsed(field = "Backup B", defaultNullRead = "")
	private String backupB;

	@Parsed(field = "Status", defaultNullRead = "")
	private String status;
	
	/* NOTE Okay. This is important.
	 * YOu see this constructor? This is bad.
	 * This breaks the usage of reading in CSV
	 * stuff, and will result in one heck of a 
	 * strange error that will then result in 
	 * frustration. That will lead to hair loss,
	 * and you will be bald in 20 days. Do not
	 * attempt.
	 * 
	 * This has been left to warn others of this
	 * pitfall. Beware.
	 */
	/*public CSVDay( Day day ) { 
		this.date = day.getDate().toString();
		this.dayOfWeek = day.getDate().getDayOfWeek().toString();
		
		int len = day.assignments.size();
		
		if( len >= 3 ) {
			this.personA = day.assignments.get(0).name;
			this.personB = day.assignments.get(1).name;
			this.personC = day.assignments.get(2).name;
		} else if( len >= 2 ) {
			this.personA = day.assignments.get(0).name;
			this.personB = day.assignments.get(1).name;
			this.personC = "";
		} else if( len >= 1 ) {
			this.personA = day.assignments.get(0).name;
			this.personB = "";
			this.personC = "";
		}
		
		len = day.backups.size();
		
		if( len >= 2 ) {
			this.backupA = day.backups.get(0).name;
			this.backupB = day.backups.get(1).name;
		} else if( len >= 1 ) {
			this.backupA = day.backups.get(0).name;
			this.backupB = "";
		}
		
		this.status = "";
		
	}*/
	
	public boolean getStatus( String name ) {
		// Don't think too much about it, because you'll realize how stupid this system is.
		if( name.compareTo( personA ) == 0 ) {
			return (status.length() >= 0 ? status.charAt(0) == 'y' : false );
		} else if( name.compareTo( personB ) == 0 ) {
			return (status.length() >= 1 ? status.charAt(1) == 'y' : false );
		} else if( name.compareTo( personC ) == 0 ) {
			return (status.length() >= 2 ? status.charAt(2) == 'y' : false );
		} else if( name.compareTo( backupA ) == 0 ) {
			return (status.length() >= 3 ? status.charAt(3) == 'y' : false );
		} else if( name.compareTo( backupB ) == 0 ) {
			return (status.length() >= 4 ? status.charAt(4) == 'y' : false );
		} else {
			return false;
		}
	}
	
	public boolean getStatus( Person p ) {
		if( p == null ) {
			System.out.println("SHIT");
			System.out.println(p);
		}
		return getStatus( p.getName() );
	}
	
	public Day getDay() {
		List<Person> assignments = new ArrayList<>();
		List<Person> backups = new ArrayList<>();
		List<Person> present = new ArrayList<>();
		
		Person tmp = null; // There's probably a better way to do this...
		tmp = Assigner.searchPersonList( Assigner.people, this.personA );
		if( tmp != null ) {
			assignments.add( tmp );
		}
		
		tmp = Assigner.searchPersonList( Assigner.people, this.personB );
		if( tmp != null ) {
			assignments.add( tmp );
		}
		
		tmp = Assigner.searchPersonList( Assigner.people, this.personC );
		if( tmp != null ) {
			assignments.add( tmp );
		}
		
		tmp = Assigner.searchPersonList( Assigner.people, this.backupA );
		if( tmp != null ) {
			backups.add( tmp );
		}
		
		tmp = Assigner.searchPersonList( Assigner.people, this.backupB );
		if( tmp != null ) {
			backups.add( tmp );
		}
		
		for( Person p : assignments ) {
			if( getStatus(p) ) {
				present.add(p);
			}
		}
		
		for( Person p : backups ) {
			if( getStatus(p) ) {
				present.add(p);
			}
		}
		
		return new Day( LocalDate.parse(date), assignments, backups, present );
	}
	
	/* GETTERS */

	public String getDate() {
		return date;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public String getPersonA() {
		return personA;
	}

	public String getPersonB() {
		return personB;
	}

	public String getPersonC() {
		return personC;
	}

	public String getBackupA() {
		return backupA;
	}

	public String getBackupB() {
		return backupB;
	}

	public String getStatus() {
		return status;
	}
	
	/* BETTER GETTERS */
	
	public LocalDate getLocalDate() {
		return null;
	}
	
	public DayOfWeek getDayOfWeekEnum() {
		return DayOfWeek.valueOf(dayOfWeek);
	}

}
