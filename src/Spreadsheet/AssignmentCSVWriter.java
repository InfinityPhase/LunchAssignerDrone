package Spreadsheet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.opencsv.CSVWriter;

import Main.Constants;
import Main.Day;
import Main.Person;

public class AssignmentCSVWriter {
	private String fileName;
	private String[] header;

	private List<Day> assignments;

	public AssignmentCSVWriter( String fileName, String[] header ) {
		this.fileName = fileName;
		this.header = header;

		assignments = new ArrayList<>();
	}

	public AssignmentCSVWriter( String fileName ) {
		this( fileName, Constants.DEFAULT_ASSIGNMENT_HEADER );
	}
	
	public AssignmentCSVWriter() {
		this( Constants.DEFAULT_CSV_NAME );
	}

	public void addDay( Day day ) {
		// NOTE Duplicate days are possible, you have been warned
		assignments.add( day );
	}
	
	public boolean dateExists( LocalDate date ) {
		for( Day d : assignments ) {
			if( d.getDate().isEqual( date ) ){
				return true;
			}
		}
		
		return false;
	}

	/* COMMIT */

	public boolean commitRecords() {
		assignments = sortByDate( assignments );

		try( CSVWriter writer = new CSVWriter( new BufferedWriter( new FileWriter( fileName, false ) ), 
				CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, 
				CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END); ) {
			// We assume this file should be empty
			writer.writeNext( header );
			
			List<String[]> lines = new ArrayList<>();
			for( Day d : assignments ) {
				String[] line = new String[8];
				line[0] = d.getDate().toString();
				line[1] = d.getDate().getDayOfWeek().toString();
				
				line[2] = getNameOrBlank( d.assignments, 0 );
				line[3] = getNameOrBlank( d.assignments, 1 );
				line[4] = getNameOrBlank( d.assignments, 2 );

				line[5] = getNameOrBlank( d.backups, 0 );
				line[6] = getNameOrBlank( d.backups, 1 );
				
				line[7] = d.getStatusString();

				lines.add(line);
			}
			
			writer.writeAll( lines );
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/* UTILS */

	private List<Day> sortByDate( List<Day> list ) {
		Collections.sort( list );
		return list;
	}
	
	private String getNameOrBlank( List<Person> list, int index ) {
		if( list.size() > index ) {
			return list.get(index).name;
		} else {
			return "";
		}
	}

}
