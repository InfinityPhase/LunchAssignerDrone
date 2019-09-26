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
	public static final int ASSIGNMENT_PEOPLE = 4;
	public static final int BACKUP_PEOPLE = 2;
	
	// Value constants
	public static final double DEFAULT_VALUE = 1.0;
	public static final double LEADERSHIP_VALUE = 2.0;
	public static final double ASSIGNMENT_VALUE = -0.5;

	// YEAR, MONTH, DAY
	public static final LocalDate DATE_START = LocalDate.of( 2019, Month.OCTOBER, 7 );
	public static final LocalDate DATE_END = LocalDate.of( 2019, Month.NOVEMBER, 30 );
	public static final LocalDate DATE_TODAY = LocalDate.now();
	
	// Blacklist of days for rotations
	public static final DayOfWeek[] DAY_NO_ROTATIONS = { DayOfWeek.SATURDAY, DayOfWeek.SUNDAY };
	public static final LocalDate[] DATE_NO_ROTATIONS = { 
		LocalDate.of(2019, Month.SEPTEMBER, 2), 
		LocalDate.of(2019, Month.NOVEMBER, 11), 
		LocalDate.of(2019, Month.NOVEMBER, 27), 
		LocalDate.of(2020, Month.JANUARY, 20), 
		LocalDate.of(2020, Month.MAY, 25) }; 
	public static final LocalDate[][] RANGE_NO_ROTATIONS = { 
		{LocalDate.of(2019, Month.SEPTEMBER, 3), LocalDate.of(2019, Month.OCTOBER, 4)}, 
		{LocalDate.of(2019, Month.NOVEMBER, 27), LocalDate.of(2019, Month.NOVEMBER, 29)}, 
		{LocalDate.of(2019, Month.DECEMBER, 18), LocalDate.of(2019, Month.DECEMBER, 20)},
		{LocalDate.of(2019, Month.DECEMBER, 23), LocalDate.of(2020, Month.JANUARY, 6)}, 
		{LocalDate.of(2020, Month.FEBRUARY, 17), LocalDate.of(2020, Month.FEBRUARY, 21)}, 
		{LocalDate.of(2020, Month.APRIL, 13), LocalDate.of(2020, Month.APRIL, 17)}, 
		{LocalDate.of(2020, Month.JUNE, 2), LocalDate.of(2020, Month.JUNE, 4)} }; // Inclusive
	
	public static final String[] DEFAULT_ASSIGNMENT_HEADER = { "Date", "Day Of Week", "Person A", "Person B", "Person C", "Person D", "Backup A", "Backup B", "Status" };

}
