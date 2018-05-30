package Main;

import java.lang.reflect.Field;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@SuppressWarnings("unused")
public class Person {
	public String name; // What is your name, friend?
	public DayOfWeek[] avalibleDays; // Weekdays that work
	public List<LocalDate> blacklistDate; // Specific dates that don't work

	// Self explanatory, hopefully
	public List<LocalDate> assignedDays;
	private List<LocalDate> presentDays;
	private List<LocalDate> missedDays;

	// TODO Implement later...
	private String email;
	private String note;
	private String per3;
	private String per4;
	private boolean leadership;

	// For optimizations
	private LocalDate prevDateQueired;
	private int prevCountReturned;

	public Person( String name, DayOfWeek[] avalibleDays, List<LocalDate> presentDays, List<LocalDate> assignedDays, List<LocalDate> missedDays, List<LocalDate> blacklistDate, String email, String note, String per3, String per4, boolean leadership ) {
		this.name = name;
		this.avalibleDays = avalibleDays;
		this.presentDays = presentDays;
		this.assignedDays = assignedDays;
		this.missedDays = missedDays;
		this.blacklistDate = blacklistDate;
		
		this.email = email;
		this.note = note;
		this.per3 = per3;
		this.per4 = per4;
		this.leadership = leadership;
		
		this.prevCountReturned = 0;
		this.prevDateQueired = LocalDate.MIN; // Why not?
	}
	
	public Person( String name, DayOfWeek[] days, String email, String note, String per3, String per4, boolean leadership ) {
		this( name, days, new ArrayList<LocalDate>(), new ArrayList<LocalDate>(), new ArrayList<LocalDate>(), new ArrayList<LocalDate>(), email, note, per3, per4, leadership );
	}
	
	public Person( String name, DayOfWeek[] days, String email, String note, String per3, String per4 ) {
		this( name, days, email, note, per3, per4, false );
	}

	public Person( String name, DayOfWeek[] days ) {
		this( name, days, "", "", "", "" );
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
		List<LocalDate> pastAssignments = new ArrayList<>();

		for( LocalDate d : assignedDays ) {
			if( d.isBefore(date) ) {
				pastAssignments.add(d);
			}
		}

		if( pastAssignments.size() == 0 ) { return 1; }
		return 1.0 * presentDays.size() / pastAssignments.size();
	}

	public void present( Day day ) {
		present( day.getDate() );
	}
	
	public void present( LocalDate date ) {
		presentDays.add( date );
	}

	public void absent( Day day ) {
		absent( day.getDate() );
	}
	
	public void absent( LocalDate date ) {
		missedDays.add( date );
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

			for( LocalDate d : assignedDays ) {
				if( d.isAfter( date ) ) {
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
	
	public void sortAllAssignments() {
		Collections.sort( assignedDays );
		Collections.sort( missedDays );
		Collections.sort( presentDays );
	}
	
	/* GETTERS */
	
	public List<LocalDate> getPresentDays() {
		return presentDays;
	}

	public List<LocalDate> getMissedDays() {
		return missedDays;
	}

	public String getEmail() {
		return email;
	}

	public String getNote() {
		return note;
	}

	public String getPer3() {
		return per3;
	}

	public String getPer4() {
		return per4;
	}
	
	public boolean getLeadership() {
		return leadership;
	}
	
	/* OBJECT UTILS */

	@Override
	public String toString() {
		return "Person{name=" + name + "}";
	}

	@Override
	public int hashCode() {		
		return new HashCodeBuilder(17, 37).append(name).append(assignedDays).append(blacklistDate)
				.append(avalibleDays).append(missedDays).append(presentDays).append(note)
				.append(per3).append(per4).append(leadership).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if( !( obj instanceof Person ) ) {
			return false;
		} else  if( obj == this ) {
			return true;
		}
		
		Person p = (Person) obj;
		
		return new EqualsBuilder().append(name, p.name).append(avalibleDays, p.avalibleDays)
				.append(blacklistDate, p.blacklistDate).append(assignedDays, p.assignedDays)
				.append(presentDays, p.getPresentDays()).append(missedDays, p.getMissedDays())
				.append(email, p.getEmail()).append(note, p.getNote()).append(per3, p.getPer3())
				.append(per4, p.getPer4()).append(leadership, p.getLeadership()).isEquals();
	}

}
