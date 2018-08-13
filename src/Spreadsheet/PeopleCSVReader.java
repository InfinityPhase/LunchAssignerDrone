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
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import Main.Person;

public class PeopleCSVReader {
	private String fileName;

	private CsvParserSettings parserSettings;
	private BeanListProcessor<Person> rowProcessor;

	public PeopleCSVReader( String fileName ) {
		this.fileName = fileName;

		this.rowProcessor = new BeanListProcessor<Person>(Person.class);

		this.parserSettings = new CsvParserSettings();
//		parserSettings.setIgnoreLeadingWhitespaces(true);
//		parserSettings.setIgnoreTrailingWhitespaces(true);
//		parserSettings.setEmptyValue("");
//		parserSettings.setKeepQuotes(false);
//		parserSettings.setNullValue("");
		parserSettings.setProcessor(rowProcessor);
		parserSettings.setHeaderExtractionEnabled(true);
	}

	public PeopleCSVReader( File file ) {
		this( file.getAbsolutePath() );
	}

	public List<Person> getPeople() {
		List<Person> people = new ArrayList<>();

		try( BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( new File( fileName ) ) ) ); ) {
			CsvParser parser = new CsvParser( parserSettings );

			parser.parseAll(reader, 50);
			people = rowProcessor.getBeans();

			parser.stopParsing();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return people;	
	}

}
