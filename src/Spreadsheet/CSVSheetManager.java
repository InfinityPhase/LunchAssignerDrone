package Spreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
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

public class CSVSheetManager {
	private static List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
	private static final String DATA_STORE_DIR = "credentials/wellshitthissucks.json";

	private static HttpTransport transport;
	private static JacksonFactory jsonFactory;
	private static FileDataStoreFactory dataStoreFactory;
	private Sheets service;

	public CSVSheetManager() {
		try {
			transport = GoogleNetHttpTransport.newTrustedTransport();
			dataStoreFactory = new FileDataStoreFactory( new File(DATA_STORE_DIR) );
			jsonFactory = JacksonFactory.getDefaultInstance();

			service = getSheetsService();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
	
	/* GOOGLE AUTH STUFF */

	private Sheets getSheetsService() throws IOException {
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
