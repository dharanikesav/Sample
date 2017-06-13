package com.tcs.saf.utilities;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.DataProvider;

import com.tcs.saf.exceptions.DataSheetException;
import com.tcs.saf.exceptions.DatabaseConnectivityException;
import com.tcs.saf.exceptions.InvalidBrowserException;
import com.tcs.saf.base.BaseTest;
import jxl.read.biff.BiffException;

/**
 * @author TCS
 * 
 */
public class TestDataProvider {

	/**
	 * @param tableName
	 *            - name of the database tableName from which data needs to be
	 *            fetched
	 * @param testCaseName
	 *            - name of the test case
	 * @return Object Array of HashMap which contains key value as column
	 *         heading and its corresponding value as value pair
	 * @throws DatabaseConnectivityException
	 * @throws SQLException
	 * @throws InvalidBrowserException 
	 */
	@DataProvider(name = "dataBase")
	public Object[][] getDatabaseData(String tableName, String testCaseName)
			throws DatabaseConnectivityException, SQLException, InvalidBrowserException {

		DatabaseConnection dbConnection = new DatabaseConnection(
				BaseTest.databaseType, BaseTest.databaseName,
				BaseTest.databaseUserName, BaseTest.databasePassword,BaseTest.databaseServerIP);
		dbConnection.getConnection();
		Object[][] dataMap = (Object[][]) null;

		// DatabaseConnection dbConnection = new DatabaseConnection();
		dataMap = dbConnection.getDataFromDatabase(tableName, testCaseName);
		return dataMap;
	}

	
	@DataProvider(name = "dataBase")
	public Object[][] getDataFromSingleRow(String tableName, String testCaseName, int rowNo)
			throws DatabaseConnectivityException, SQLException, InvalidBrowserException {

		DatabaseConnection dbConnection = new DatabaseConnection(
				BaseTest.databaseType, BaseTest.databaseName,
				BaseTest.databaseUserName, BaseTest.databasePassword,BaseTest.databaseServerIP);
		dbConnection.getConnection();
		Object[][] dataMap = (Object[][]) null;

		// DatabaseConnection dbConnection = new DatabaseConnection();
		dataMap = dbConnection.getDataFromSingleRow(tableName, testCaseName,rowNo);

		return dataMap;
	}

	
	/**
	 * This method will return a 2-dimensional object array with hashmap data in
	 * each object. The data fetched is from specified column name of a
	 * table,where test case name is that of class name for a specific row
	 * 
	 * @param tableName
	 * @param testCaseName
	 * @param rowNo
	 * @param columnHeading
	 * @return
	 * @throws DatabaseConnectivityException
	 * @throws SQLException
	 * @throws InvalidBrowserException 
	 */
	@DataProvider(name = "dataBase")
	public Object[][] getDataFromColumnRow(String tableName,
			String testCaseName, int rowNo, String columnHeading)
			throws DatabaseConnectivityException, SQLException, InvalidBrowserException {

		DatabaseConnection dbConnection = new DatabaseConnection(
				BaseTest.databaseType, BaseTest.databaseName,
				BaseTest.databaseUserName, BaseTest.databasePassword,BaseTest.databaseServerIP);
		dbConnection.getConnection();
		Object[][] dataMap = (Object[][]) null;

		// DatabaseConnection dbConnection = new DatabaseConnection();
		dataMap = dbConnection.getDataFromColumnRow(tableName, testCaseName, rowNo,
				columnHeading);
		return dataMap;
	}

	/**
	 * This method will return a 2-dimensional object array with hashmap data in
	 * each object. The data fetched is from specified column name of a database
	 * table.
	 * 
	 * @param tableName
	 * @param testCaseName
	 * @param columnHeading
	 * @return
	 * @throws DatabaseConnectivityException
	 * @throws SQLException
	 * @throws InvalidBrowserException 
	 */
	@DataProvider(name = "dataBase")
	public Object[][] getDataFromColumnHeading(String tableName,
			String testCaseName, String columnHeading)
			throws DatabaseConnectivityException, SQLException, InvalidBrowserException {

		DatabaseConnection dbConnection = new DatabaseConnection(
				BaseTest.databaseType, BaseTest.databaseName,
				BaseTest.databaseUserName, BaseTest.databasePassword,BaseTest.databaseServerIP);
		dbConnection.getConnection();
		Object[][] dataMap = (Object[][]) null;

		// DatabaseConnection dbConnection = new DatabaseConnection();
		dataMap = dbConnection.getColumnDataFromDatabase(tableName,
				testCaseName, columnHeading);
		return dataMap;
	}

	/**
	 * This method will return a 2-dimensional object array with hashmap data in
	 * each object. The data is fetched for a test case name from row values
	 * specified between from row no and to row now. Both row numbers are
	 * inclusive.
	 * 
	 * @param tableName
	 * @param testCaseName
	 * @param fromRowNo
	 * @param toRowNo
	 * @return
	 * @throws DatabaseConnectivityException
	 * @throws SQLException
	 * @throws InvalidBrowserException 
	 */
	@DataProvider(name = "dataBase")
	public Object[][] getDataFromRows(String tableName, String testCaseName,
			int fromRowNo, int toRowNo) throws DatabaseConnectivityException,
			SQLException, InvalidBrowserException {

		DatabaseConnection dbConnection = new DatabaseConnection(
				BaseTest.databaseType, BaseTest.databaseName,
				BaseTest.databaseUserName, BaseTest.databasePassword,BaseTest.databaseServerIP);
		dbConnection.getConnection();
		Object[][] dataMap = (Object[][]) null;

		// DatabaseConnection dbConnection = new DatabaseConnection();
		dataMap = dbConnection.getDataFromRows(tableName, testCaseName,
				fromRowNo, toRowNo);
		return dataMap;
	}

	/**
	 * This function returns the data from excel sheet for the specified class
	 * 
	 * @param externalSheetPath
	 * @param sheetName
	 * @return object array
	 * @throws NoDataInInputSheetException
	 * @throws IncorrectSheetPathException
	 * @throws InvalidSheetHeadingException
	 * @throws IOException@throws DataSheetException
	 */

	@DataProvider(name = "dataSheet")
	public Object[][] getTestDataFromExcel(String externalSheetPath,
			String sheetName) throws IOException,
			 DataSheetException,
			 BiffException {

		Object[][] dataMap = (Object[][]) null;

		DataSheet dataSheetObj = new DataSheet();
		dataMap = dataSheetObj.readFromSheet(externalSheetPath, sheetName);

		return dataMap;
	}

}
