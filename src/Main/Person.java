package Main;

import java.lang.reflect.Field;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Person {
	public String name; // What is your name, friend?
	public DayOfWeek[] avalibleDays; // Weekdays that work
	public List<LocalDate> blacklistDate; // Specific dates that don't work

	// Self explanatory, hopefully
	public List<Day> assignedDays; // Perhaps these should store LocalDate objects instead
	private List<Day> presentDays;
	private List<Day> missedDays;

	// TODO Implement later...
	private String email;
	private String note;
	private String per3;
	private String per4;

	// For optimizations
	private LocalDate prevDateQueired;
	private int prevCountReturned;

	public Person( String name, DayOfWeek[] avalibleDays, List<Day> presentDays, List<Day> assignedDays, List<Day> missedDays, List<LocalDate> blacklistDate ) {
		this.name = name;
		this.avalibleDays = avalibleDays;
		this.presentDays = presentDays;
		this.assignedDays = assignedDays;
		this.missedDays = missedDays;
		this.blacklistDate = blacklistDate;
		
		this.prevCountReturned = 0;
		this.prevDateQueired = LocalDate.MIN; // Why not?
	}

	public Person( String name, DayOfWeek[] days ) {
		this( name, days, new ArrayList<Day>(), new ArrayList<Day>(), new ArrayList<Day>(), new ArrayList<LocalDate>() );
	}

	public Person( String name ) {
		this( name, new DayOfWeek[] { DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY } );
	}

	public double getScore() { // TODO imprelement sqrt curve?
		return getRate();
	}

	public double getScore( LocalDate date ) {
		return getRate( date );
	}

	public double getRate() {
		if( assignedDays.size() == 0 ) { return 1; }
		return 1.0 * presentDays.size() / assignedDays.size();
	}

	public double getRate( LocalDate date ) {
		List<Day> pastAssignments = new ArrayList<>();

		for( Day d : assignedDays ) {
			if( d.isDone(date) ) {
				pastAssignments.add(d);
			}
		}

		if( pastAssignments.size() == 0 ) { return 1; }
		return 1.0 * presentDays.size() / pastAssignments.size();
	}

	public void present( Day day ) {
		presentDays.add( day );
	}

	public void absent( Day day ) {
		missedDays.add( day );
	}

	public boolean avalible( DayOfWeek day ) {
		for( DayOfWeek d : avalibleDays ) {
			if( d.equals( day ) ) {
				return true;
			}
		}

		return false;
	}

	public boolean avalible( LocalDate date ) {
		if( blacklistDate.contains( date ) ) {
			return false;
		}

		return avalible( date.getDayOfWeek() );
	}

	public int futureAssignmentCount( LocalDate date ) {
		// TODO can be optimized, bc all days past the first match should also match
		if( !date.isEqual(prevDateQueired) ) {
			int count = 0;

			for( Day d : assignedDays ) {
				if( d.getDate().isAfter( date ) ) {
					++count;
				}
			}

			this.prevCountReturned = count;
			this.prevDateQueired = date;

			return count;
		} else {
			return prevCountReturned;
		}
	}

	@Override
	public String toString() {
		return "Person{name=" + name + "}";
	}

}
