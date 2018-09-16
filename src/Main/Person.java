package Main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.univocity.parsers.annotations.BooleanString;
import com.univocity.parsers.annotations.LowerCase;
import com.univocity.parsers.annotations.Parsed;

@SuppressWarnings("unused")
public class Person {
	public List<LocalDate> blacklistDate = new ArrayList<>(); // Specific dates that don't work

	// Self explanatory, hopefully
	public List<LocalDate> assignedDays = new ArrayList<>();
	public List<LocalDate> backupDays = new ArrayList<>();
	private List<LocalDate> presentDays = new ArrayList<>();
	private List<LocalDate> missedDays = new ArrayList<>();

	/* CSV LOADERS */ 
	@Parsed(field = {"First Name", "What is your first name?"})
	private String firstName;

	@Parsed(field = {"Last Name", "What is your last name?"})
	private String lastName;

	@Parsed(field = {"Email", "What is your email?"})
	private String email;

	@Parsed(field = {"Period 3", "What is your third period classroom?"})
	private String per3;

	@Parsed(field = {"Period 4", "What is your fourth period classroom?"})
	private String per4;

	@Parsed(field = {"Days Avalible", "Which of the following days are you available?"})
	private String avalible;

	@LowerCase
	@BooleanString(falseStrings = {"no", "n", "null", "false"}, trueStrings = {"yes", "y", "true"})
	@Parsed(field = "Leadership")
	private boolean leadership;

	@Parsed(field = {"Notes", "Any other notes?"})
	private String notes;

	// For optimizations
	private LocalDate prevDateQueired = LocalDate.MIN;;
	private int prevCountReturned = 0;

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
		
		for( LocalDate d : backupDays ) {
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
		assignedDays.add( date );
	}

	public void absent( Day day ) {
		absent( day.getDate() );
	}
	
	public void absent( LocalDate date ) {
		missedDays.add( date );
		assignedDays.add( date );
	}

	public boolean avalible( DayOfWeek day ) {
		for( DayOfWeek d : this.getAvailibleDays() ) {
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
	
	public int futureBackupCount( LocalDate date ) {
		// TODO can be optimized, bc all days past the first match should also match
		if( !date.isEqual(prevDateQueired) ) {
			int count = 0;

			for( LocalDate d : backupDays ) {
				if( d.isAfter( date ) ) {
					//backupDays.size() - backupDays.indexOf(d);
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
		Collections.sort( backupDays );
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
		return notes;
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
	
	public String getName() {
		return firstName + " " + lastName;
	}
	
	public DayOfWeek[] getAvailibleDays() {
		return Assigner.convertDays( avalible );
	}
	
	/* OBJECT UTILS */

	@Override
	public String toString() {
		return "Person{name=" + getName() + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assignedDays == null) ? 0 : assignedDays.hashCode());
		result = prime * result + ((avalible == null) ? 0 : avalible.hashCode());
		result = prime * result + ((backupDays == null) ? 0 : backupDays.hashCode());
		result = prime * result + ((blacklistDate == null) ? 0 : blacklistDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + (leadership ? 1231 : 1237);
		result = prime * result + ((missedDays == null) ? 0 : missedDays.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((per3 == null) ? 0 : per3.hashCode());
		result = prime * result + ((per4 == null) ? 0 : per4.hashCode());
		result = prime * result + ((presentDays == null) ? 0 : presentDays.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Person))
			return false;
		Person other = (Person) obj;
		if (assignedDays == null) {
			if (other.assignedDays != null)
				return false;
		} else if (!assignedDays.equals(other.assignedDays))
			return false;
		if (avalible == null) {
			if (other.avalible != null)
				return false;
		} else if (!avalible.equals(other.avalible))
			return false;
		if (backupDays == null) {
			if (other.backupDays != null)
				return false;
		} else if (!backupDays.equals(other.backupDays))
			return false;
		if (blacklistDate == null) {
			if (other.blacklistDate != null)
				return false;
		} else if (!blacklistDate.equals(other.blacklistDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (leadership != other.leadership)
			return false;
		if (missedDays == null) {
			if (other.missedDays != null)
				return false;
		} else if (!missedDays.equals(other.missedDays))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (per3 == null) {
			if (other.per3 != null)
				return false;
		} else if (!per3.equals(other.per3))
			return false;
		if (per4 == null) {
			if (other.per4 != null)
				return false;
		} else if (!per4.equals(other.per4))
			return false;
		if (presentDays == null) {
			if (other.presentDays != null)
				return false;
		} else if (!presentDays.equals(other.presentDays))
			return false;
		return true;
	}

	

//	@Override
//	public boolean equals(Object obj) {
//		if( !( obj instanceof Person ) ) {
//			return false;
//		} else  if( obj == this ) {
//			return true;
//		}
//		
//		Person p = (Person) obj;
//		
//		return new EqualsBuilder().append(name, p.name).append(avalibleDays, p.avalibleDays)
//				.append(blacklistDate, p.blacklistDate).append(assignedDays, p.assignedDays)
//				.append(presentDays, p.getPresentDays()).append(missedDays, p.getMissedDays())
//				.append(email, p.getEmail()).append(note, p.getNote()).append(per3, p.getPer3())
//				.append(per4, p.getPer4()).append(leadership, p.getLeadership()).append(backupDays, p.backupDays)
//				.isEquals();
//	}

}
