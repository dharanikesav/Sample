package com.tcs.saf.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.tcs.saf.exceptions.DataSheetException;
import com.tcs.saf.exceptions.DatabaseConnectivityException;
import com.tcs.saf.exceptions.InvalidBrowserException;
import com.tcs.saf.base.BaseTest;

/**
 * @author TCS
 * 
 */
public class DatabaseConnection {

	static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String MYSQL_URL = "jdbc:mysql://";
	static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:";

	private Connection connection = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
	private String userName = "";
	private String password = "";
	private String dbName = "";
	private String dbType = "";
	private String dbServerIP = "";

	Logger logger = Log.getInstance(Thread.currentThread().getStackTrace()[1]
			.getClassName());

	/**
	 * 
	 */
	public DatabaseConnection() {
		super();

	}

	/**
	 * @param userName
	 *            -Database username
	 * @param password
	 *            -Database password
	 * @param dbName
	 *            - Name of database
	 * @param dbType
	 *            - Database type like MySQL, Oracle
	 */
	public DatabaseConnection(String dbType, String dbName, String userName,
			String password, String dbIP) {
		this.userName = userName;
		this.password = password;
		this.dbName = dbName;
		this.dbType = dbType;
		this.dbServerIP=dbIP;
	}


	/**
	 * This method closes the Database connection
	 * 
	 * @throws SQLException
	 */
	public void closeDatabaseConnectivity() throws SQLException {

		if(!connection.isClosed()) {
			connection.close();
		}
		logger.info(dbType + " database connection is closed");
	}

	/**
	 * This method will get data from database table corresponding to a
	 * particular row no and column heading
	 * 
	 * @param tableName
	 * @param fromRowNo
	 * @param columnHeading
	 * @return
	 * @throws SQLException
	 * @throws InvalidBrowserException 
	 * @throws DatabaseConnectivityException 
	 */
	public Object[][] getDataFromColumnRow(String tableName, String testCaseName,int fromRowNo,
			String columnHeading) throws SQLException, InvalidBrowserException, DatabaseConnectivityException {

		int index = 0;
		int columnCount = getColumnHeadings(tableName).size();
		Object[][] dbData = null;
		String[] browserArray=BaseTest.getBrowserName();
		dbData = new Object[getDBTableRowCount(tableName,testCaseName,fromRowNo)*(browserArray.length)][columnCount];

		String query = "select " + columnHeading + " from " + tableName
				+ " where id=? and test_case_name=?";
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, fromRowNo);
			preparedStatement.setString(2, testCaseName);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to retreive data from row no: "+fromRowNo+" in "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name/row no in the database");
			throw new DataSheetException("Unable to retreive data from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name/row no in the database");
		}
		if (!resultSet.isBeforeFirst() ){
			System.out.println("no value");
			logger.error("No data available in table: " + tableName
					+ " with column heading: " + columnHeading
					+ " and row number: " + fromRowNo+" for test case: "+testCaseName);
			throw new DatabaseConnectivityException("No data available in table: " + tableName
					+ " with column heading: " + columnHeading
					+ " and row number: " + fromRowNo+" for test case: "+testCaseName);
		} else {
			System.out.println("in else bloack");
			while (resultSet.next()) {

				for (String browserValue : browserArray) {
					LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
					System.out.println(browserValue+" *****");
					dataMap.put("Browser", browserValue);

					dataMap.put(columnHeading, resultSet.getString(1));

					dbData[index] = new Object[] { dataMap };
					index++;
				}
			}
			closeDatabaseConnectivity();
			return dbData;
		}

	}

	/**
	 * @param tableName
	 * @param columnName
	 * @return
	 * @throws SQLException
	 * @throws InvalidBrowserException 
	 */
	public Object[][] getColumnDataFromDatabase(String tableName, String testCaseName,
			String columnName) throws SQLException, InvalidBrowserException {

		int index = 0;
		int columnCount = getColumnHeadings(tableName).size();
		Object[][] dbData = null;
		String[] browserArray=BaseTest.getBrowserName();
		dbData = new Object[getDBTableRowCount(tableName,testCaseName)*(browserArray.length)][columnCount];

		String query = "select " + columnName + " from " + tableName+" where test_case_name=?";
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, testCaseName);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to retreive data from "+columnName+":"+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case/column name in the database");
			throw new DataSheetException("Unable to retreive data from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case/column name in the database");
		}
		if (!resultSet.isBeforeFirst() ) {
			logger.error("No data available under column heading: "
					+ columnName + " in table: " + tableName+" for test case: "+testCaseName);
			throw new SQLException("No data available under column heading: "
					+ columnName + " in table: " + tableName+" for test case: "+testCaseName);
		} else {
			while (resultSet.next()) {

				for (String browserValue : browserArray) {
					LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
					dataMap.put("Browser", browserValue);
					/*
					 * get the column names; column indexes start from 1 after
					 * excluding id field of the table
					 */
					for (int i = 1; i <= columnCount - 1; i++) {
						dataMap.put(columnName, resultSet.getString(columnName));
					}
					dbData[index] = new Object[] { dataMap };
					index++;
				}
			}
			closeDatabaseConnectivity();
			return dbData;
		}
	}

	/**
	 * This method will get column headings of a database table and will be
	 * returned as an ArrayList of String. First element of the ArrayList will
	 * be the table id(index)
	 * 
	 * @param tableName
	 *            - name of the table for which column headings are retrieved
	 * @return columnList - returns an ArrayList of column headings.
	 * @throws SQLException
	 */
	public ArrayList<String> getColumnHeadings(String tableName)
			throws SQLException {

		String query = "show columns from " + tableName;
		ResultSet resultSetTemp = null;
		ArrayList<String> columnList = new ArrayList<String>();

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSetTemp = preparedStatement.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to retreive column headings from "+tableName+"\n Possible reason is invalid table name in the database");
			throw new DataSheetException("Unable to retreive column headings from "+tableName+"\n Possible reason is invalid table name in the database");
		}
		if (!resultSetTemp.isBeforeFirst() ) {

			logger.error("Unable to retrieve column headings from " + tableName);
			throw new SQLException("Unable to retrieve column headings from "
					+ tableName);
		} else {

			while (resultSetTemp.next()) {
				columnList.add(resultSetTemp.getString(1));
			}
			return columnList;
		}
	}

	/**
	 * This method creates a connection with database
	 * 
	 * @return Connection- connection to database
	 */
	public Connection getConnection() throws DatabaseConnectivityException {
		try {
			if(dbName.equalsIgnoreCase("")){
				logger.error("No database name specified.");
				throw new DatabaseConnectivityException("No database name specified.");
			}
			else{
				if (this.dbType.equalsIgnoreCase("mysql")) {

					Class.forName(MYSQL_DRIVER);
					connection = DriverManager.getConnection(MYSQL_URL +dbServerIP+"/"+ dbName,
							userName, password);
				} else if (dbType.equalsIgnoreCase("oracle")) {
					Class.forName(ORACLE_DRIVER);
					connection = DriverManager.getConnection(ORACLE_URL + dbName,
							userName, password);
				} else {
					logger.error("Unable to connect to Database. Invalid DataBase type: "
							+ dbType);
					throw new DatabaseConnectivityException(
							"Unable to connect to Database. Invalid DataBase type: "
									+ dbType);
				}
			}
		} catch (ClassNotFoundException e) {
			logger.error("Unable to find " + dbType + " class driver");
			throw new DatabaseConnectivityException("Unable to find " + dbType
					+ " class driver");
		} catch (SQLException e) {
			logger.error("Connection to " + dbType
					+ " database failed due to SQLException");
			throw new DatabaseConnectivityException("Connection to " + dbType
					+ " database failed due to SQLException");
		}

		if (connection.equals(null)) {
			logger.error("Unable to establish " + dbType
					+ " database connection in " + dbName + " using username: "
					+ userName + " and password:  " + password);
			throw new DatabaseConnectivityException("Unable to establish "
					+ dbType + " database connection in " + dbName
					+ " using username: " + userName + " and password:  "
					+ password);
		} else {
			logger.info("Connected to " + dbType + " database" + " : " + dbName);
		}

		return connection;
	}

	/**
	 * This method will retrieve data from database table specified for the test
	 * case name specified
	 * 
	 * @param tableName
	 * @param testCaseName
	 * @return
	 * @throws SQLException
	 * @throws InvalidBrowserException 
	 */
	public Object[][] getDataFromDatabase(String tableName, String testCaseName)
			throws SQLException, InvalidBrowserException {

		int index = 0;
		int columnCount = getColumnHeadings(tableName).size();
		String[] browserArray=BaseTest.getBrowserName();
		Object[][] dbData = null;
		System.out.println(browserArray.length+"size of browsers  "+getDBTableRowCount(tableName, testCaseName)+" row count");
		dbData = new Object[getDBTableRowCount(tableName,testCaseName)*(browserArray.length)][columnCount];

		String query = "select * from " + tableName + " where test_case_name=?";
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, testCaseName);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to retreive data from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name in the database");
			throw new DataSheetException("Unable to retreive data from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name in the database");
		}
		if (!resultSet.isBeforeFirst() ) {
			logger.error("No test data in database matching the test case name: "
					+ testCaseName + " in " + tableName);
			throw new SQLException(
					"No test data in database matching the test case name: "
							+ testCaseName + " in " + tableName);
		} else {

			while (resultSet.next()) {

				for (String browserValue : browserArray) {
					LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
					dataMap.put("Browser", browserValue);
					/*
					 * get the column names; column indexes start from 1 after
					 * excluding id field of the table
					 */
					for (int i = 1; i <= columnCount - 1; i++) {
						String columnName = getColumnHeadings(tableName).get(i);
						dataMap.put(columnName, resultSet.getString(columnName));
					}	

					dbData[index] = new Object[] { dataMap };
					index++;
				}

			}
		}

		return dbData;
	}

	/**
	 * This method will get data rows from the table between specified row no.
	 * Both 'from' and 'to' row numbers are inclusive
	 * 
	 * @param tableName
	 * @param fromRowNo
	 * @param toRowNo
	 * @return
	 * @throws SQLException
	 * @throws InvalidBrowserException 
	 */
	public Object[][] getDataFromRows(String tableName,String testCaseName, int fromRowNo,
			int toRowNo) throws SQLException, InvalidBrowserException {

		int index = 0;
		int columnCount = getColumnHeadings(tableName).size();
		Object[][] dbData = null;
		String[] browserArray=BaseTest.getBrowserName();
		dbData = new Object[getDBTableRowCount(tableName,testCaseName,fromRowNo,toRowNo)*(browserArray.length)][columnCount];

		String query = "select * from " + tableName + " where test_case_name=? and id between "
				+ fromRowNo + " and " + toRowNo;
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, testCaseName);
			resultSet = preparedStatement.executeQuery();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to retreive data from "+tableName+" for test case "+testCaseName+" for row numbers between "+fromRowNo+" and "+toRowNo+"\n Possible reasons are invalid table/test case name in the database");
			throw new DataSheetException("Unable to retreive data from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name in the database");
		}
		if (!resultSet.isBeforeFirst() ) {
			logger.error("No data available in table: " + tableName
					+ " from row numbers: " + fromRowNo + " to row numbers: "
					+ toRowNo+" for test case: "+testCaseName);
			throw new SQLException("No data available in table: " + tableName
					+ " from row number: " + fromRowNo + " to row number: "
					+ toRowNo+" for test case: "+testCaseName);
		} else {
			while (resultSet.next()) {

				for (String browserValue : browserArray) {
					LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
					dataMap.put("Browser", browserValue);

					/*
					 * get the column names; column indexes start from 1 after
					 * excluding id field of the table
					 */
					for (int i = 1; i <= columnCount - 1; i++) {
						String columnName = getColumnHeadings(tableName).get(i);
						dataMap.put(columnName, resultSet.getString(columnName));
					}

					dbData[index] = new Object[] { dataMap };
					index++;
				}
			}
			closeDatabaseConnectivity();
			return dbData;
		}
	}

	/**
	 * This method will return the no of rows in a database table
	 * 
	 * @param tableName
	 *            - name of the database table whose row no is returned
	 * @return rowCount- no of rows in a database table
	 * @throws SQLException
	 */
	public int getDBTableRowCount(String tableName,String testCaseName) throws SQLException {
		int rowCount = 0;
		String query = "select count(*) from " + tableName+ " where test_case_name=?";
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, testCaseName);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to retreive row count from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name in the database");
			throw new DataSheetException("Unable to retreive row count from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name in the database");
		}
		if (!resultSet.isBeforeFirst() ) {
			logger.error("No data available in table: " + tableName);
			throw new SQLException("No data available in table: " + tableName);
		} else {
			while (resultSet.next()) {
				rowCount = resultSet.getInt(1);
			}
			return rowCount;
		}
	}


	public int getDBTableRowCount(String tableName,String testCaseName,int rowNo) throws SQLException {
		int rowCount = 0;
		String query = "select count(*) from " + tableName+ " where test_case_name=? and id=?";
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, testCaseName);
			preparedStatement.setInt(2, rowNo);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to retreive row count from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name in the database");
			throw new DataSheetException("Unable to retreive row count from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name in the database");
		}
		if (!resultSet.isBeforeFirst() ) {
			logger.error("No data available in table: " + tableName);
			throw new SQLException("No data available in table: " + tableName);
		} else {
			while (resultSet.next()) {
				rowCount = resultSet.getInt(1);
			}
			return rowCount;
		}
	}


	public int getDBTableRowCount(String tableName,String testCaseName,int fromRowNo,int toRowNo) throws SQLException {
		int rowCount = 0;
		String query = "select count(*) from " + tableName+ " where test_case_name=? and id between "+ fromRowNo + " and " + toRowNo;
		try{
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, testCaseName);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to retreive row count from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name in the database");
			throw new DataSheetException("Unable to retreive row count from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name in the database");
		}
		if (!resultSet.isBeforeFirst() ) {
			logger.error("No data available in table: " + tableName);
			throw new SQLException("No data available in table: " + tableName);
		} else {
			while (resultSet.next()) {
				rowCount = resultSet.getInt(1);
			}
			return rowCount;
		}
	}

	/**
	 * This method returns a row of data from database table
	 * 
	 * @param tableName
	 * @param rowNo
	 * @return
	 * @throws SQLException
	 * @throws InvalidBrowserException 
	 */
	public Object[][] getDataFromSingleRow(String tableName,String testCaseName,int rowNo)
			throws SQLException, InvalidBrowserException {

		int index = 0;
		int columnCount = getColumnHeadings(tableName).size();
		Object[][] dbData = null;
		String[] browserArray=BaseTest.getBrowserName();
		dbData = new Object[getDBTableRowCount(tableName,testCaseName,rowNo)*(browserArray.length)][columnCount];

		String query = "select * from " + tableName + " where test_case_name=? and  id=?";

		try{

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, testCaseName);
			preparedStatement.setInt(2, rowNo);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to retreive data from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name/row no in the database");
			throw new DataSheetException("Unable to retreive data from "+tableName+" for test case "+testCaseName+"\n Possible reasons are invalid table/test case name/row no in the database");
		}
		if (!resultSet.isBeforeFirst() ) {
			logger.error("Unable to retrieve data from row no: " + rowNo
					+ " from table: " + tableName+" for test case: "+testCaseName);
			throw new SQLException("Unable to retrieve column headings from "
					+ tableName+" for test case: "+testCaseName);
		} else {
			while (resultSet.next()) {

				for (String browserValue : browserArray) {
					LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
					dataMap.put("Browser", browserValue);
					/*
					 * get the column names; column indexes start from 1 after
					 * excluding id field of the table
					 */
					for (int i = 1; i <= columnCount - 1; i++) {
						String columnName = getColumnHeadings(tableName).get(i);
						dataMap.put(columnName, resultSet.getString(columnName));
					}
					dbData[index] = new Object[] { dataMap };
					index++;
				}
			}
			closeDatabaseConnectivity();
			return dbData;
		}
	}
}
