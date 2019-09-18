package Main;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public final class Constants {

    // General
    public static final String PEOPLE_CSV = "LunchPeople.csv";
    public static final String DEFAULT_CSV_NAME = "DefaultName";
    public static final String ASSIGNMENT_CSV = "Assignments.csv";
    public static final String PREV_ASSIGNMENT_CSV = "Old_Assignments.csv";
    public static final int ASSIGNMENT_PEOPLE = 4;
    public static final int BACKUP_PEOPLE = 2;

    // Value constants
    public static final double DEFAULT_VALUE = 1.0;
    public static final double LEADERSHIP_VALUE = 0.0;
    public static final double ASSIGNMENT_VALUE = -7.0;

    // YEAR, MONTH, DAY
    public static final LocalDate DATE_START = LocalDate.of( 2019, Month.MAY, 1 );
    public static final LocalDate DATE_END = LocalDate.of( /*2019, Month.JUNE, 3*/ 2019, Month.JULY, 1 );
    public static final LocalDate DATE_TODAY = LocalDate.now();

    // Blacklist of days for rotations
    public static final DayOfWeek[] DAY_NO_ROTATIONS = {DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
    public static final LocalDate[] DATE_NO_ROTATIONS = {LocalDate.of( 2018, Month.SEPTEMBER, 3 ), LocalDate.of( 2018, Month.NOVEMBER, 12 ),
        LocalDate.of( 2018, Month.NOVEMBER, 21 ), LocalDate.of( 2019, Month.JANUARY, 21 ), LocalDate.of( 2019, Month.MAY, 27 )};
    public static final LocalDate[][] RANGE_NO_ROTATIONS = {{LocalDate.of( 2018, Month.OCTOBER, 1 ), LocalDate.of( 2018, Month.OCTOBER, 5 )},
        {LocalDate.of( 2018, Month.OCTOBER, 22 ), LocalDate.of( 2018, Month.NOVEMBER, 2 )}, {LocalDate.of( 2018, Month.NOVEMBER, 22 ), LocalDate.of( 2018, Month.NOVEMBER, 23 )},
        {LocalDate.of( 2018, Month.DECEMBER, 19 ), LocalDate.of( 2018, Month.DECEMBER, 21 )}, {LocalDate.of( 2018, Month.DECEMBER, 24 ), LocalDate.of( 2019, Month.JANUARY, 7 )},
        {LocalDate.of( 2019, Month.FEBRUARY, 18 ), LocalDate.of( 2019, Month.FEBRUARY, 22 )}, {LocalDate.of( 2019, Month.MARCH, 4 ), LocalDate.of( 2019, Month.JANUARY, 15 )},
        {LocalDate.of( 2019, Month.APRIL, 8 ), LocalDate.of( 2019, Month.APRIL, 12 )}, {LocalDate.of( 2019, Month.JUNE, 4 ), LocalDate.of( 2019, Month.JUNE, 6 )}}; // Inclusive

    public static final String[] DEFAULT_ASSIGNMENT_HEADER = {"Date", "Day Of Week", "Person A", "Person B", "Person C", "Person D", "Backup A", "Backup B", "Status"};

}
