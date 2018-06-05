package Main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public final class Constants {
	
	// General
	public static final int ASSIGNMENT_PEOPLE = 3;
	public static final int BACKUP_PEOPLE = 2;
	public static final String ASSIGNMENT_PREFIX = "Assignments";
	public static final String DATA_STORE_DIR = "credentials/wellshitthissucks.json";
	public static final String[] DEFAULT_ASSIGNMENT_HEADER = { "Date", "Day Of Week", "Person A", "Person B", "Person C", "Backup A", "Backup B", "Status" };
	public static final DateTimeFormatter FORMMATER = DateTimeFormatter.ofPattern("MM/dd/uuuu");
	
	// Value constants
	public static final double DEFAULT_VALUE = 1.0;
	public static final double LEADERSHIP_VALUE = 0.5;

	// YEAR, MONTH, DAY
	public static final LocalDate DATE_START = LocalDate.of( 2018, Month.OCTOBER, 1 );
	public static final LocalDate DATE_END = LocalDate.of( 2019, Month.MARCH, 1 );
	public static final LocalDate DATE_TODAY = LocalDate.now();
	
	// Blacklist of days for rotations
	public static final DayOfWeek[] DAY_NO_ROTATIONS = { DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, DayOfWeek.MONDAY };
	public static final LocalDate[] DATE_NO_ROTATIONS = {}; // Not implemented
	public static final LocalDate[][] RANGE_NO_ROTATIONS = {}; // Not implemented
		
	// CSV Settings
	public static final String DEFAULT_KEY = "1dBnoFECF_iG5YZ9TXmxGaVQlNrnbpR_OyWDT8uFKyh0";
	public static final String PEOPLE_CSV = "Data";
	public static final String PEOPLE_CSV_LOCAL = "People.csv";
	public static final String DEFAULT_CSV_NAME = "NAMEHERE";
	public static final String ASSIGNMENT_CSV = "Assignments.csv";
	public static final String PREV_ASSIGNMENT_CSV = "Old_Assignments.csv";
	public static final String DEFALT_SHEET_NAME = "";
	
}
