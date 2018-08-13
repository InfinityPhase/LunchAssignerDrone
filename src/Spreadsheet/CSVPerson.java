package Spreadsheet;

import java.time.DayOfWeek;

import com.univocity.parsers.annotations.BooleanString;
import com.univocity.parsers.annotations.LowerCase;
import com.univocity.parsers.annotations.Parsed;

import Main.Assigner;

public class CSVPerson {

	@Parsed(field = "First Name")
	protected String firstName;

	@Parsed(field = "Last Name")
	protected String lastName;

	@Parsed(field = "Email")
	protected String email;

	@Parsed(field = "Period 3")
	protected String per3;

	@Parsed(field = "Period 4")
	protected String per4;

	@Parsed(field = "Days Avalible")
	protected String avalible;

	@LowerCase
	@BooleanString(falseStrings = {"no", "n", "null", "false"}, trueStrings = {"yes", "y", "true"})
	@Parsed(field = "Leadership")
	protected boolean leadership;

	@Parsed(field = "Notes")
	protected String notes;

	public String getName() {
		return firstName + " " + lastName;
	}

	public String getEmail() {
		return email;
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

	public String getNotes() {
		return notes;
	}

	public DayOfWeek[] getAvalible() {
		return Assigner.convertDays( avalible );
	}

}
