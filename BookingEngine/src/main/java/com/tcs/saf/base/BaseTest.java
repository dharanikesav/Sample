package com.tcs.saf.base;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.ScreenshotException;
import org.openqa.selenium.remote.UnreachableBrowserException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.tcs.saf.exceptions.DataSheetException;
import com.tcs.saf.exceptions.InvalidBrowserException;
import com.tcs.saf.utilities.DatabaseConnection;
import com.tcs.saf.utilities.Log;
import com.tcs.saf.utilities.TestDataProvider;

/**
 * @author TCS
 * 
 */
public abstract class BaseTest {

	public static String databaseName;
	public static String databasePassword;
	public static String databaseServerIP;
	public static String databaseType;
	public static String databaseUserName;
	public static String testDataSource;
	public static DatabaseConnection databaseConnection;
	public static String externalSheetPath;
	public static String webdriverServerHostName;
	public static String webdriverServerPortName;
	public static String webdriverUrl;
	public int timeOut;

	public RemoteWebDriver driver;

	public RemoteWebDriver getDriver() {
		return driver;
	}

	public LinkedHashMap<String, String> mapDataSheet;
	public TestDataProvider testDataProvider;
	public String testName;
	public String testBrowser;
	public static ResourceBundle globalProperties;
	public static ResourceBundle gridProperties;

	private static final Logger logger = Log.getInstance(Thread.currentThread()
			.getStackTrace()[1].getClassName());
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	public BaseTest()  {
		this.testDataProvider = new TestDataProvider();
		getGridProperties();
		getGlobalProperties();
	}

	/**
	 * @param browser
	 * @param mapDataSheet
	 */
	public BaseTest(String testName, String browser,
			LinkedHashMap<String, String> mapDataSheet) {
		this.testName = testName;
		this.testDataProvider = new TestDataProvider();
		this.testBrowser = browser;
		this.mapDataSheet = mapDataSheet;

	}

	/**
	 * This function initiate a web driver client
	 * @throws MalformedURLException
	 */
	@BeforeMethod
	public void beforemethod() throws MalformedURLException{



		if(!(this.testBrowser==null))
		{

                        @SuppressWarnings("RedundantStringToString")
			String formattedTime = timeFormat.format(new Date()).toString(); 
			logger.info("\n" +
					"\nTest Case Name            :  "+testName+
					"\nExecution using Data Row  :"+
					mapDataSheet.toString()+
					"\nStart time                :  "+formattedTime+
					"\n-------------------------------------------------------------------------------------------------------------------------------------");

			if (this.testBrowser.equalsIgnoreCase("Firefox")
					|| this.testBrowser
					.equalsIgnoreCase("InternetExplorer")
					|| this.testBrowser
					.equalsIgnoreCase("Chrome"))
			{


				startWebDriverClient(this.testBrowser);
				logger.info("Test initialization");
				logger.info("Calling startWebDriverClient for browser: "
						+ this.testBrowser);



			}
			else
			{
				logger.error("Browser name"
						+ " '"
						+ this.testBrowser
						+ "' "
						+ "is invalid:Please give InternetExplorer,Chrome or Firefox");



			}

		}


	}

	@AfterMethod
	public void afterMethod(ITestResult testResult) throws SQLException {
		if(!(this.testBrowser==null))
		{


			if (this.testBrowser.equalsIgnoreCase("Firefox")
					|| this.testBrowser
					.equalsIgnoreCase("InternetExplorer")
					|| this.testBrowser
					.equalsIgnoreCase("Chrome"))
			{
				if (testDataSource.equalsIgnoreCase("database")) 
				{
					while (databaseConnection != null)
						databaseConnection.closeDatabaseConnectivity();
				}
				if (testResult.getStatus() == 2)
				{
					try 
					{
						takeScreenshot();
					} catch (Exception e)
					{
						logger.error("Unable to take screenshot for test: " + testResult.getName(),e.fillInStackTrace());
					}
				}

			}
			else
			{
				logger.error("Invalid browser name");
			}

			Alert alertBox=isAlertPresent();
			if(alertBox!=null)
			{
				alertBox.accept();
			}
			driver.close();
			driver.quit();
		}


                @SuppressWarnings("RedundantStringToString")
		String formattedTime = timeFormat.format(new Date()).toString(); 
		logger.info("In afterMethod - Performing test cleanup , Closing webdriver instance\n"+
				"End time         :       "+formattedTime+
				"\n---------------------------------------------------------------------------------------------------\n" +

				"-----------------------------------------------------------------------------------------------------\n");
	}

	/**
	 * This function reads Global properties
	 * @throws DataSheetException 
	 */
	protected void getGlobalProperties() {

		globalProperties = ResourceBundle.getBundle("global");

		externalSheetPath = globalProperties.getString("external_sheet_path");

		timeOut = Integer.parseInt(globalProperties.getString("time_out"));
		if (globalProperties.containsKey("test_data_source")) {
			testDataSource = globalProperties.getString("test_data_source");
		}
		if(testDataSource.equalsIgnoreCase("excel")){
			externalSheetPath = globalProperties.getString("external_sheet_path");
			if(externalSheetPath.equals(""))
			{
				logger.error("Please provide a valid sheet path");
				Assert.fail();
			}
		}
		else if(testDataSource.equalsIgnoreCase("database")){

			databaseName = globalProperties.getString("database_name");
			databaseType = globalProperties.getString("database_type");
			databaseUserName = globalProperties.getString("database_username");
			databasePassword = globalProperties.getString("database_password");
			databaseServerIP = globalProperties.getString("database_ip");
		}
		else{
			logger.error("Please provide a valid test data source value");
			Assert.fail();
		}
	}


	/**
	 * This function reads Grid properties
	 */
	protected void getGridProperties() {

		gridProperties = ResourceBundle.getBundle("grid");
		webdriverServerHostName = gridProperties
				.getString("webdriver_hostname");
		webdriverServerPortName = gridProperties.getString("webdriver_port");
		webdriverUrl = gridProperties.getString("webdriver_defaultURL");

	}


	/**
	 * This function Starts WebDriver client based on the given Browser
	 * 
	 * @param browser
	 * @throws MalformedURLException 
	 * @throws InvalidBrowserException 
	 * @throws Exception
	 */
	protected void startWebDriverClient(String browser) throws MalformedURLException
	{

		try
		{
			if (browser.equalsIgnoreCase("Firefox")
					|| browser
					.equalsIgnoreCase("InternetExplorer")
					|| browser
					.equalsIgnoreCase("Chrome"))
			{



				URL remoteServerUrl = new URL("http://" + webdriverServerHostName
						+ ":" + webdriverServerPortName + "/wd/hub");
				logger.info("startWebDriverClient - Starting remoteWebDriver for URL " + remoteServerUrl);
				driver = WebDriverFactory.getWebdriver(browser, remoteServerUrl);
				driver.manage().window().maximize();
				driver.manage().timeouts()
				.implicitlyWait(timeOut, TimeUnit.MILLISECONDS);
				logger.info("startWebDriverClient: Started remoteWebDriver for "
						+ driver);
			}

			else
			{

				logger.error("Browser name"
						+ " '"
						+ browser
						+ "' "
						+ "is invalid:Please give InternetExplorer,Chrome or Firefox");

			}
		}


		catch (UnreachableBrowserException e) {
			logger.error("Could not start a new session. Possible causes are: \n\t 1. Selenium Grid is not proprly set up" +
					"\n\t 2. Drivers are not properly added to the path " +
					"\n\t 3. Port mentioned in the Grid properties file is different from port in which Remote server has started",e.fillInStackTrace());
			throw new UnreachableBrowserException("Could not start a new session. Possible causes are: \n\t 1. Selenium Grid is not proprly set up" +
					"\n\t 2. Drivers are not properly added to the path " +
					"\n\t 3. Port mentioned in the Grid properties file is different from port in which Remote server has started");

		}
		catch (WebDriverException e){
			logger.error("Could not start a new session. Possible causes are : \n\t 1. Selenium Grid is not proprly set up" +
					"\n\t 2. Drivers are not properly added to the path " +
					"\n\t 3. Port mentioned in the Grid properties file is different from port in which Remote server has started",e.fillInStackTrace());
			throw new WebDriverException("Could not start a new session. Possible causes are: \n\t 1. Selenium Grid is not proprly set up" +
					"\n\t 2. Drivers are not properly added to the path " +
					"\n\t 3. Port mentioned in the Grid properties file is different from port in which Remote server has started");
		}

		catch(Exception e)
		{


			logger.error("Browser name"
					+ " '"
					+ browser
					+ "' "
					+ "is invalid:Please give InternetExplorer,Chrome or Firefox");




		}


	}

	/**
	 * This function retrieve the Base URL from Grid properties
	 * 
	 * @return - Returns the Application's Base URL
	 */
	public static String getBaseURL() {
		return webdriverUrl;
	}

	/**
	 * This method will return the browser names specified in global properties file
	 * 
	 * @return array of browser names
	 */
	public static String[] getBrowserName()
	{
		String[] browserArray = null;

		if (globalProperties.containsKey("browser_name")) {
			
			String browsers=globalProperties.getString("browser_name");
			if(browsers.contains(",")){
				browserArray = browsers.trim().split(",");
			}
			else{
				browserArray=browsers.trim().split(" ");
			}
			for (String browser : browserArray) {
				if(browser.equalsIgnoreCase("Firefox")
						|| browser
						.equalsIgnoreCase("InternetExplorer")
						|| browser
						.equalsIgnoreCase("Chrome")){
					return browserArray;
				}
				else
				{
					logger.error("Browser name specified in global properties file is invalid. Please Please give InternetExplorer,Chrome or Firefox");

				}
			}

		} else {
			logger.error("No browser name specified for the test execution");

		}
		return browserArray;
	}

	public static String getGlobalBrowserFlag()
	{
		return globalProperties.getString("use_global_browser_forExcel");

	}

	public void takeScreenshot() throws ScreenshotException{
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE); 
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("screenshot.directory")+"\\"+testName+"_"+System.nanoTime()+".png"));
			logger.info("Screen shot has been taken");

		} catch (IOException e)
		{
			// TODO Auto-generated catch block

			logger.error("The filename, directory name, or volume label syntax of screenshot is incorrect");
			e.printStackTrace();
		}
		catch(ScreenshotException e1){

			throw new ScreenshotException("Unable to take screenshot"); 
		}

	}

	public String getValue(String key)
	{

		if(mapDataSheet.containsKey(key)){
			return mapDataSheet.get(key);
		}

		else
		{
			logger.info("Column heading '"+key+"' is not present in your data source \n Please Check the Column Headings of your TestCase's data in your data source (Excel / Database)");
			throw new NullPointerException("Column heading "+key+"is not present in your data source \n Please Check the Column Headings of your TestCase's data in your data source (Excel / Database)");
		}


	}

	public Alert isAlertPresent()
	{
		try
		{
			Alert alert = driver.switchTo().alert();

			return alert;
		} catch (NoAlertPresentException e) {

			return null;
		}

	}
}