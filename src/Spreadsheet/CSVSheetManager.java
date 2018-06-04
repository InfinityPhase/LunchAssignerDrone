package Spreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;

import Main.Constants;

public class CSVSheetManager {
	private static List<String> scopes = Arrays.asList( SheetsScopes.SPREADSHEETS );

	private HttpTransport transport;
	private JacksonFactory jsonFactory;
	private FileDataStoreFactory dataStoreFactory;
	private Sheets service;

	private String sheetID;

	public CSVSheetManager( String sheetID ) {
		this.sheetID = sheetID;

		try {
			transport = GoogleNetHttpTransport.newTrustedTransport();
			dataStoreFactory = new FileDataStoreFactory( new File( Constants.DATA_STORE_DIR ) );
			jsonFactory = JacksonFactory.getDefaultInstance();

			service = getSheetsService();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

	/* UPLOAD / DOWNLOAD FILES */

	public boolean upload( String sheetName ) {
		return upload( sheetName, sheetName );
	}

	public boolean upload( String localName, String remoteName ) {
		return false; // Placeholder
	}

	public boolean download( String sheetName ) {
		return download( sheetName, sheetName );
	}
	
	/**
	 * Saves the sheet from the spreadsheet with the id {@code sheetID} and name {@code remoteName}
	 * to {@code localName}. Will create a new file on the filesystem, overwriting any files
	 * with the same name.
	 * @param localName The location to save the sheet to locally
	 * @param remoteName The name of the sheet server-side
	 * @return True if the operation was successful, false if an error was thrown
	 */
	public boolean download( String localName, String remoteName ) {
		try {
			FileOutputStream out = new FileOutputStream( localName );
			service.spreadsheets().get( sheetID ).executeAndDownloadTo( out );
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public List<String> allSheetNames() {
		List<String> result = new ArrayList<>();
		
		try {
			Spreadsheet response1 = service.spreadsheets().get( sheetID ).setIncludeGridData( false ).execute();
			
			for( Sheet s : response1.getSheets() ) {
				result.add( s.getProperties().getTitle() );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/* GOOGLE AUTH STUFF */

	private Sheets getSheetsService() throws IOException { // TODO Replace with oneliner
		Credential credential = authorize();
		return new Sheets.Builder(transport, jsonFactory, credential)
				.setApplicationName("INSERT_YOUR_APPLICATION_NAME")
				.build();
	}

	private Credential authorize() throws IOException {
		// Load client secrets.
		File cfile = new File("certs/cert.json");
		cfile.createNewFile();
		jsonFactory = JacksonFactory.getDefaultInstance();
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory , new InputStreamReader(new FileInputStream(cfile)));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow =
				new GoogleAuthorizationCodeFlow.Builder(
						transport, jsonFactory, clientSecrets, scopes)
				.setDataStoreFactory(dataStoreFactory)
				.setAccessType("offline")
				.build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		return credential;
	}

}
