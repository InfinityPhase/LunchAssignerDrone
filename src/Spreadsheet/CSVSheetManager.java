package Spreadsheet;

import Main.Constants;

public class CSVSheetManager {
	private String sheetID;
	private String token;
	private String fileName;
	
	public CSVSheetManager( String sheetID, String token, String fileName ) {
		this.sheetID = sheetID;
		this.token = token;
		this.fileName = fileName;
	}
	
	public CSVSheetManager( String sheetID, String token ) {
		this( sheetID, token, Constants.DEFAULT_CSV_NAME );
	}
	
	public boolean download( String fileName ) {
		return false;
	}
	
	public boolean download() {
		return download( fileName );
	}
	
	public boolean upload( String fileName ) {
		return false;
	}
	
	public boolean upload() {
		return upload( fileName );
	}

}
