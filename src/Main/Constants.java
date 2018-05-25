package Main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public final class Constants {
	// General
	public static final String PEOPLE_CSV = "LunchPeople.csv";
	public static final String DEFAULT_CSV_NAME = "NAMEHERE";
	public static final String ASSIGNMENT_CSV = "Assignments.csv";
	public static final String PREV_ASSIGNMENT_CSV = "Old_Assignments.csv";
	public static final int ASSIGNMENT_PEOPLE = 3;
	public static final int BACKUP_PEOPLE = 2;
	
	// Value constants
	public static final Double DEFAULT_VALUE = 1.0;
	public static final Double LEADERSHIP_VALUE = 0.5;

	// YEAR, MONTH, DAY
	public static final LocalDate DATE_START = LocalDate.of( 2018, Month.OCTOBER, 1 );
	public static final LocalDate DATE_END = LocalDate.of( 2019, Month.MARCH, 1 );
	public static final LocalDate DATE_TODAY = LocalDate.now();
	
	// Blacklist of days for rotations
	public static final DayOfWeek[] DAY_NO_ROTATIONS = { DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, DayOfWeek.MONDAY };
	public static final LocalDate[] DATE_NO_ROTATIONS = {}; // Not implemented
	public static final LocalDate[][] RANGE_NO_ROTATIONS = {}; // Not implemented
	
	public static final String[] DEFAULT_ASSIGNMENT_HEADER = { "Date", "Day Of Week", "Person A", "Person B", "Person C", "Backup A", "Backup B", "Status" };

}
