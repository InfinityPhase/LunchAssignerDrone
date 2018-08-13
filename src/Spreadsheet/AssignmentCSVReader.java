package Spreadsheet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import Main.Constants;
import Main.Day;

public class AssignmentCSVReader {
	private String fileName;
	private String[] header;
	private CsvParserSettings parserSettings;
	private BeanListProcessor<CSVDay> rowProcessor;

	private List<CSVDay> csvDays;

	public AssignmentCSVReader( String fileName, String[] header ) {
		this.fileName = fileName;
		this.header = header;
		this.csvDays = new ArrayList<>();
		
		this.rowProcessor = new BeanListProcessor<CSVDay>(CSVDay.class);
		
		this.parserSettings = new CsvParserSettings();
		parserSettings.setIgnoreLeadingWhitespaces(true);
		parserSettings.setIgnoreTrailingWhitespaces(true);
		parserSettings.setEmptyValue("");
		parserSettings.setKeepQuotes(false);
		parserSettings.setNullValue("");
		parserSettings.setProcessor(rowProcessor);
		parserSettings.setHeaderExtractionEnabled(true);
		
	}

	public AssignmentCSVReader( String fileName ) {
		this( fileName, Constants.DEFAULT_ASSIGNMENT_HEADER );
	}

	public AssignmentCSVReader() {
		this( Constants.DEFAULT_CSV_NAME );
	}

	@Deprecated
	public CSVDay[] getAllDayDataArray() {
		if( csvDays.size() == 0 ) {
			loadDayData();
		}

		return csvDays.toArray( new CSVDay[ csvDays.size() ] );
	}
	
	public List<CSVDay> getAllDayData() {
		if( csvDays.size() == 0 ) { // Only calculate this once.
			loadDayData();
		}

		return csvDays;
	}
	
	public void loadDayData() {
		try( BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( new File( fileName ) ) ) ); ) {
			CsvParser parser = new CsvParser( parserSettings );

			parser.parseAll(reader, 50);
			csvDays = rowProcessor.getBeans();
			
			parser.stopParsing();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Day[] convertCSVDays( CSVDay[] csvDays ) {
		Day[] days = new Day[ csvDays.length ];
		
		for( int i = 0, n = csvDays.length; i < n; ++i ) {
			days[i] = csvDays[i].getDay();
		}
		
		return days;
	}

}
