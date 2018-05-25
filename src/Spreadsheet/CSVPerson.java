package Spreadsheet;

import java.time.DayOfWeek;

import com.opencsv.bean.CsvBindByName;

import Main.Assigner;

public class CSVPerson {
	
	@CsvBindByName(column = "First Name", required = true)
	protected String firstName;
	
	@CsvBindByName(column = "Last Name", required = true)
	protected String lastName;
	
	@CsvBindByName(column = "Email", required = false)
	protected String email;
	
	@CsvBindByName(column = "Period 3", required = false)
	protected String per3;
	
	@CsvBindByName(column = "Period 4", required = false)
	protected String per4;
	
	@CsvBindByName(column = "Days Avalible", required = true)
	protected String avalible;
	
	@CsvBindByName(column = "Notes", required = false)
	protected String notes;
	
	@CsvBindByName(column = "Leadership", required = false)
	protected String leadership;
	
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
	
	public String getNotes() {
		return notes;
	}
	
	public DayOfWeek[] getAvalible() {
		return Assigner.convertDays( avalible );
	}
	
	public String getLeadership() {
		return leadership;
	}

}
