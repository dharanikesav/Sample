package com.tcs.saf.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.tcs.saf.base.BasePage;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.Reporter;

/**
 * @Author: 206247 Country Selection Home Page
 */
public class FlightBookingSearchPage extends BasePage {

    //variables for Round Trip booking details
    private final String btn_flightManager = "//div[@id='flightmanager']/div/ul/li/a/span";
    private final String btn_roundTrip = "//input[@id='lhfa-return']";
    private final String btn_oneWay = "//*[@id='flightmanagerFlightsForm']/div[2]/div/fieldset/div/label[2]";
    private final String btn_origin = "//button[contains(@id,'-origin')]";
    private final String inp_originCountry = "//div[@class='form-group hidden-md hidden-lg']/select[@id='flmOriginAirportAtlasCountry']";//*[@id='flmOriginAirportAtlasCountry']";
    private final String inp_originCity = "//*[@id='flmOriginAirportAtlasCity']";
    private final String inp_originAirport = "//*[@id='flmOriginAirportAtlasAirport']";
    private final String btn_destination = "//button[contains(@id,'-destination']";
    private final String inp_destinationCountry = "//*[@id='flmDestinationAirportAtlasCountry']";
    private final String inp_destinationCity = "//*[@id='flmDestinationAirportAtlasCity']";
    private final String inp_destinationAirport = "//*[@id='flmDestinationAirportAtlasAirport']";
    private final String ddn_adults = "//select[contains(@id,'Adults')]";
    private final String lik_childLink = "//a[contains(text(),'Travel with children')]";
    private final String ddn_childCount = "//select[contains(@id,'Children')]";
    private final String ddn_infantCount = "//select[contains(@id,'Infants')]";
    private final String lik_infantError = "//*[@id='flightmanagerFlightsForm']/div[1]/div/div/ul/li/a[contains(.,'More infants than adults')]";
    private final String ddn_class = "//select[contains(@id,'Cabin')]";
    private final String btn_Search = "//button[@type='submit'][contains(.,'Search flights')]";

    //variables for SelectFlightsForRoundTrip
    private final String inp_departRadio = "//*[contains(@id,'flightRadio_0_0_')]";
    private final String inp_returnRadio = "//*[contains(@id,'flightRadio_1_1_')]";
    private final String btn_continue = "//*[@id='TOOLBAR_CONTINUE_0']/span";
    private final String btn_continue2 = "//*[@id='TOOLBAR_2_CHECKOUT_0']/span";
    private final String inp_noLogin = "//input[@id='LOGIN_LOGIN_MODES_0_NO_LOGIN']";
    private final String ddn_titleDD = "//span[@id='DATA_PASSENGERS_ADT_TITLE_0']/div/div/div[@class='arrow_btn']/span";
    private final String ddn_titleDD2 = "//span[@id='DATA_PASSENGERS_ADT_TITLE_1']/div/div/div";
    private final String ddn_genderDD = "//span[@id='DATA_PASSENGERS_CHD_GENDER_0']/div/div/div";
    private final String lik_titleOption = "//ul[contains(@id,'sizcache')]/li[2]/div";
    private final String inp_firstName = "//input[@id='PASSENGERS_ADT_FIRST_NAME_0']";
    private final String inp_firstName2 = "//input[@id='PASSENGERS_ADT_FIRST_NAME_1']";
    private final String inp_lastName = "//input[@id='PASSENGERS_ADT_LAST_NAME_0']";
    private final String inp_lastName2 = "//input[@id='PASSENGERS_ADT_LAST_NAME_1']";
    private final String inp_firstNameC = "//input[@id='PASSENGERS_CHD_FIRST_NAME_0']";
    private final String inp_lastNameC = "//input[@id='PASSENGERS_CHD_LAST_NAME_0']";
    private final String inp_contactNumber = "//input[@id='CONTACT_DETAILS_PHONE_PHONE_NUMBER_0']";
    private final String inp_contactMail = "//input[@id='CONTACT_DETAILS_EMAIL_0']";
    private final String btn_continue3 = "//*[@id='TOOLBAR_CONTINUE_0']/span";
    private final String ddn_adultDay = "//select[contains(@id='PASSENGERS_ADT_DATE_OF_BIRTH_ADT_DOB_DATE_0')]";
    private final String ddn_adultMonth = "//select[contains(@id='PASSENGERS_ADT_DATE_OF_BIRTH_ADT_DOB_MONTH_0')]";
    private final String ddn_adultYear = "//select[contains(@id='PASSENGERS_ADT_DATE_OF_BIRTH_ADT_DOB_YEAR_0')]";
    private final String ddn_childDay = "//span[@id='DATA_PASSENGERS_CHD_DATE_OF_BIRTH_0']/div/div/div";
    private final String ddn_childMonth = "//span[@id='DATA_PASSENGERS_CHD_DATE_OF_BIRTH_0']/div[2]/div/div";
    private final String ddn_childYear = "//span[@id='DATA_PASSENGERS_CHD_DATE_OF_BIRTH_0']/div[3]/div/div";

    public FlightBookingSearchPage(RemoteWebDriver driver) {
        super(driver);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean hasPageLoaded() {
        return false;
    }

    @Override
    public String getPageUrl() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * This function used launch the Base Page
     */
    public void launchBasePage() {
        launchBaseURL();
        waitForPageLoad();
    }

    public final String inp_From = "//input[@placeholder='From']";
    public final String inp_To = "//input[@placeholder='To']";

    public void BookingDetails(String Browser, String OriginCountry, String OriginCity,
            String OriginAirport, String DestinationCountry, String DestinationCity,
            String DestinationAirport, String StartingDate, String ReturnDate, String Adults, String Class) {

        By RoundTripBtn = getLocator(btn_roundTrip, BasePage.BY_TYPE.BY_XPATH);
        delay(2000);
        By From = getLocator(inp_From, BY_TYPE.BY_XPATH);
        type(From, OriginAirport, "Airport Name");
        delay(5000);
        click(getLocator("html/body", BY_TYPE.BY_XPATH), "");
        By To = getLocator(inp_To, BY_TYPE.BY_XPATH);
        type(To, DestinationAirport, "Airport Name");
        delay(5000);
        click(getLocator("html/body", BY_TYPE.BY_XPATH), "");
        click(By.xpath("//input[@placeholder='Departing']"), "Departure Date");
        for (int i = 0; i < 3; i++) {
            
            click(By.xpath("//button[@class='next']"), "Departure Date");
        }
        click(By.xpath("(//table/tbody/tr[3]/td[4])[2]"), StartingDate);
        delay(5000);
        click(By.xpath("(//table/tbody/tr[4]/td[4])[2]"), ReturnDate);
        delay(1000);
        By AdultsCount = getLocator(ddn_adults, BasePage.BY_TYPE.BY_XPATH);
        selectDropDownByValue(AdultsCount, Adults);
        delay(2000);
        By ClassType = getLocator(ddn_class, BasePage.BY_TYPE.BY_XPATH);
        selectDropDownByVisibleText(ClassType, Class);
        delay(2000);
        By SearchFlights = getLocator(btn_Search, BasePage.BY_TYPE.BY_XPATH);
        click(SearchFlights, "Search Flights");
        waitForPageLoad();
    }

    public void FromCCA(String Country, String City, String Airport) {
        By originCountry = getLocator(inp_originCountry, BasePage.BY_TYPE.BY_XPATH);
        By originCity = getLocator(inp_originCity, BasePage.BY_TYPE.BY_XPATH);
        By originAirport = getLocator(inp_originAirport, BasePage.BY_TYPE.BY_XPATH);
        By destinationCountry = getLocator(inp_destinationCountry, BasePage.BY_TYPE.BY_XPATH);
        By destinationCity = getLocator(inp_destinationCity, BasePage.BY_TYPE.BY_XPATH);
        By destinationAirport = getLocator(inp_destinationAirport, BasePage.BY_TYPE.BY_XPATH);
        click(By.xpath("//*[@id='flightmanagerFlightsForm']/div[3]/div/button"), "Origin Button");
        delay(8000);
        selectDropDownByVisibleText(originCountry, Country);
        delay(5000);
        clickUsingJavascriptExecutor(By.xpath("html/body/div[10]/div/div/form/div[2]/div/div[2]/div[2]/label[contains(.,'" + Country + "')]"), "Origin Country");
        delay(5000);
        clickUsingJavascriptExecutor(By.xpath("html/body/div[10]/div/div/form/div[2]/div/div[4]/div[2]/label[contains(.,'" + City + "')]"), "Origin City");
        delay(5000);
        clickUsingJavascriptExecutor(By.xpath("html/body/div[10]/div/div/form/div[2]/div/div[6]/div[2]/label[contains(.,'" + Airport + "')]"), "Origin Airport");
        delay(500);
        clickUsingJavascriptExecutor(By.xpath("html/body/div[10]/div/div/form/div[3]/input"), "Select Button");
        delay(1000);

    }

    public void ToCCA(String Country, String City, String Airport) {
        click(By.xpath("//*[@id='flightmanagerFlightsForm']/div[4]/div/button"), "Destination Button");
        delay(5000);
        click(By.xpath("//div[@data-name='country']/label[contains(.,'" + Country + "')]"), "Destination Country");
        clickUsingJavascriptExecutor(By.xpath("html/body/div[10]/div/div/form/div[2]/div/div[2]/div[2]/label[contains(.,'" + Country + "')]"), "Destination Country");
        delay(8000);
        clickUsingJavascriptExecutor(By.xpath("html/body/div[10]/div/div/form/div[2]/div/div[4]/div[2]/label[contains(.,'" + City + "')]"), "Destination City");
        delay(5000);
        clickUsingJavascriptExecutor(By.xpath("html/body/div[10]/div/div/form/div[2]/div/div[6]/div[2]/label[contains(.,'" + Airport + "')]"), "Destination Airport");
        delay(500);
        clickUsingJavascriptExecutor(By.xpath("html/body/div[10]/div/div/form/div[3]/input"), "Select Button");
        delay(1000);

    }

    public void SelectFlightsForRoundTrip(String Title, String Fname, String Lname, String DOB, String ContactNumber, String Email) {
        By DepartingFlight = getLocator(inp_departRadio, BasePage.BY_TYPE.BY_XPATH);
        click(DepartingFlight, "Departure Flight Radio Button");
        delay(1000);
        By ReturnFlight = getLocator(inp_returnRadio, BasePage.BY_TYPE.BY_XPATH);
        click(ReturnFlight, "Return Flight Radio Button");
        delay(2000);
        By Continue = getLocator(btn_continue, BasePage.BY_TYPE.BY_XPATH);
        click(Continue, "Continue Button");
        waitForPageLoad();
        By Continue2 = getLocator(btn_continue2, BasePage.BY_TYPE.BY_XPATH);
        click(Continue2, "Continue Button");
        waitForPageLoad();
        delay(5000);
        By TitleDDList = getLocator("//span[@id='DATA_PASSENGERS_ADT_TITLE_0']/div/div/div[2]/span", BasePage.BY_TYPE.BY_XPATH);
        click(TitleDDList, Title);
        click(getLocator("html/body/ul/li[2]/div", BY_TYPE.BY_XPATH), "Title");
        By FirstName = getLocator(inp_firstName, BasePage.BY_TYPE.BY_XPATH);
        type(FirstName, Fname, "FirstName");
        delay(2000);
        By LastName = getLocator(inp_lastName, BasePage.BY_TYPE.BY_XPATH);
        type(LastName, Lname, "LastName");
        delay(2000);
        By ContactNum = getLocator(inp_contactNumber, BasePage.BY_TYPE.BY_XPATH);
        type(ContactNum, ContactNumber, "ContactNumber");
        delay(2000);
        By ContactMail = getLocator(inp_contactMail, BasePage.BY_TYPE.BY_XPATH);
        type(ContactMail, Email, "Contact Email");
        delay(2000);
        By Continue3 = getLocator(btn_continue3, BasePage.BY_TYPE.BY_XPATH);
        click(Continue3, "Continue Button");
        waitForPageLoad();
        getText(getLocator("//h1[contains(.,'Your Payment Details')]", BY_TYPE.BY_XPATH));
    }

    public void BookingDetailsOneWay(String OriginCountry, String OriginCity,
            String OriginAirport, String DestinationCountry, String DestinationCity,
            String DestinationAirport, String StartingDate, String Adults, String Class) {
        try {
            By FlightManager = getLocator(btn_flightManager, BasePage.BY_TYPE.BY_XPATH);
            click(FlightManager, "Flight Manager");

            By OneWayBtn = getLocator(btn_oneWay, BasePage.BY_TYPE.BY_XPATH);
            click(OneWayBtn, "One Way");

            By From = getLocator(inp_From, BY_TYPE.BY_XPATH);
            type(From, OriginAirport, "Airport Name");
            delay(1000);
            //To close the dropdown in flight manager tab
            click(getLocator("html/body", BY_TYPE.BY_XPATH), "");

            By To = getLocator(inp_To, BY_TYPE.BY_XPATH);
            type(To, DestinationAirport, "Airport Name");
            delay(1000);
            //To close the dropdown in flight manager tab
            click(getLocator("html/body", BY_TYPE.BY_XPATH), "");

            click(By.xpath("//input[@placeholder='Departing']"), "Departure Date");
            click(By.xpath("//table/tbody/tr[3]/td[4]/button"), StartingDate);

            By AdultsCount = getLocator(ddn_adults, BasePage.BY_TYPE.BY_XPATH);
            selectDropDownByValue(AdultsCount, Adults);

            By ClassType = getLocator(ddn_class, BasePage.BY_TYPE.BY_XPATH);
            selectDropDownByVisibleText(ClassType, Class);

            By SearchFlights = getLocator(btn_Search, BasePage.BY_TYPE.BY_XPATH);
            click(SearchFlights, "Search Flights");
            waitForPageLoad();
        } catch (Exception ex) {
            Logger.getLogger(FlightBookingSearchPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SelectFlightsForOneWayTrip(String Title, String Title2, String Fname, String Fname2, String Lname, String Lname2, String ContactNumber, String Email) {
        By DepartingFlight = getLocator(inp_departRadio, BasePage.BY_TYPE.BY_XPATH);
        click(DepartingFlight, "Departure Flight Radio Button");
        delay(1000);
        By Continue = getLocator(btn_continue, BasePage.BY_TYPE.BY_XPATH);
        click(Continue, "Continue Button");
        waitForPageLoad();
        By Continue2 = getLocator(btn_continue2, BasePage.BY_TYPE.BY_XPATH);
        click(Continue2, "Continue Button");
        waitForPageLoad();
        delay(5000);
        By TitleDD = getLocator(ddn_titleDD, BasePage.BY_TYPE.BY_XPATH);
        click(TitleDD, "Title DropDown");
        delay(500);
        click(By.xpath("//*[@id='page-alpi']/ul/li[2]/div[contains(.,'" + Title + "')]"), "Passenger Title");
        By FirstName = getLocator(inp_firstName, BasePage.BY_TYPE.BY_XPATH);
        type(FirstName, Fname, "FirstName");
        By LastName = getLocator(inp_lastName, BasePage.BY_TYPE.BY_XPATH);
        type(LastName, Lname, "LastName");
        delay(2000);
        By TitleDD2 = getLocator(ddn_titleDD2, BasePage.BY_TYPE.BY_XPATH);
        click(TitleDD2, "Title DropDown2");
        delay(1000);
        click(By.xpath("//*[@id='page-alpi']/ul[2]/li[2]/div[contains(.,'" + Title + "')]"), "Passenger Title");
        By FirstName2 = getLocator(inp_firstName2, BasePage.BY_TYPE.BY_XPATH);
        type(FirstName2, Fname2, "FirstName2");
        By LastName2 = getLocator(inp_lastName2, BasePage.BY_TYPE.BY_XPATH);
        type(LastName2, Lname2, "LastName");
        By ContactNum = getLocator(inp_contactNumber, BasePage.BY_TYPE.BY_XPATH);
        type(ContactNum, ContactNumber, "ContactNumber");
        By ContactMail = getLocator(inp_contactMail, BasePage.BY_TYPE.BY_XPATH);
        type(ContactMail, Email, "Contact Email");
        By Continue3 = getLocator(btn_continue3, BasePage.BY_TYPE.BY_XPATH);
        click(Continue3, "Continue Button");
        waitForPageLoad();
        AssertText("Your Payment Details");
    }

    public void BookingOneWayWithChild(String OriginCountry, String OriginCity,
            String OriginAirport, String DestinationCountry, String DestinationCity,
            String DestinationAirport, String StartingDate, String Adults, String Children, String Class) {
        By FlightManager = getLocator(btn_flightManager, BasePage.BY_TYPE.BY_XPATH);
        click(FlightManager, "Flight Manager");
        By OneWayBtn = getLocator(btn_oneWay, BasePage.BY_TYPE.BY_XPATH);
        click(OneWayBtn, "One Way");
        By From = getLocator(inp_From, BY_TYPE.BY_XPATH);
        type(From, OriginAirport, "Airport Name");
        click(getLocator("html/body", BY_TYPE.BY_XPATH), "");
        By To = getLocator(inp_To, BY_TYPE.BY_XPATH);
        type(To, DestinationAirport, "Airport Name");
        click(getLocator("html/body", BY_TYPE.BY_XPATH), "");
        click(By.xpath("//input[@placeholder='Departing']"), "Departure Date");
        click(By.xpath("//table/tbody/tr[3]/td[4]/button"), StartingDate);
        delay(5000);
        By AdultsCount = getLocator(ddn_adults, BasePage.BY_TYPE.BY_XPATH);
        selectDropDownByValue(AdultsCount, Adults);
        By WithChildren = getLocator(lik_childLink, BasePage.BY_TYPE.BY_XPATH);
        click(WithChildren, "Travel With Children");
        delay(2000);
        By ChildCount = getLocator(ddn_childCount, BasePage.BY_TYPE.BY_XPATH);
        selectDropDownByValue(ChildCount, Children);
        By ClassType = getLocator(ddn_class, BasePage.BY_TYPE.BY_XPATH);
        selectDropDownByVisibleText(ClassType, Class);
        By SearchFlights = getLocator(btn_Search, BasePage.BY_TYPE.BY_XPATH);
        click(SearchFlights, "Search Flights");
        waitForPageLoad();
    }

    public void FlightsForOneWayTripWithChild(String Title, String Fname, String Lname, String AdultDOB, String Gender, String CFname, String CLname, String ChildDOB, String ContactNumber, String Email) throws Exception {
        By DepartingFlight = getLocator(inp_departRadio, BasePage.BY_TYPE.BY_XPATH);
        click(DepartingFlight, "Departure Flight Radio Button");
        delay(1000);
        By Continue = getLocator(btn_continue, BasePage.BY_TYPE.BY_XPATH);
        click(Continue, "Continue Button");
        waitForPageLoad();
        By Continue2 = getLocator(btn_continue2, BasePage.BY_TYPE.BY_XPATH);
        click(Continue2, "Continue Button");
        waitForPageLoad();
        delay(20000);
        By TitleDD = getLocator(ddn_titleDD, BasePage.BY_TYPE.BY_XPATH);
        click(TitleDD, "Title DropDown");
        delay(500);
        click(By.xpath("//*[@id='page-alpi']/ul/li/div[contains(.,'" + Title + "')]"), "Passenger Title");
        By FirstName = getLocator(inp_firstName, BasePage.BY_TYPE.BY_XPATH);
        type(FirstName, Fname, "FirstName");
        By LastName = getLocator(inp_lastName, BasePage.BY_TYPE.BY_XPATH);
        type(LastName, Lname, "LastName");
        delay(2000);
        String[] AdultBirth = AdultDOB.split("/");
        By AdultDay = getLocator(ddn_adultDay, BasePage.BY_TYPE.BY_XPATH);
        click(By.xpath("//*[@id='page-alpi']/ul/li/div[contains(.,'" + Gender + "')]"), "Gender");
        By ChildFirstName = getLocator(inp_firstNameC, BasePage.BY_TYPE.BY_XPATH);
        type(ChildFirstName, CFname, "ChildFirstName");
        By ChildLastName = getLocator(inp_lastNameC, BasePage.BY_TYPE.BY_XPATH);
        type(ChildLastName, CLname, "ChildLastName");
        String[] ChildBirth = ChildDOB.split("/");
        By ChildMonth = getLocator(ddn_childMonth, BasePage.BY_TYPE.BY_XPATH);
        click(ChildMonth, "Child Birth Month");
        delay(1000);
        click(By.xpath("//*[@id='page-alpi']/ul[4]/li/div[contains(.,'" + ChildBirth[1] + "')]"), "Birth Month");
        By ChildDay = getLocator(ddn_childDay, BasePage.BY_TYPE.BY_XPATH);
        click(ChildDay, "Child Birth Day");
        delay(1000);
        click(By.xpath("//*[@id='page-alpi']/ul[6]/li/div[contains(.,'" + ChildBirth[0] + "')]"), "Birth Day");
        By ChildYear = getLocator(ddn_childYear, BasePage.BY_TYPE.BY_XPATH);
        click(ChildYear, "Child Birth Year");
        delay(1000);
        click(By.xpath("//*[@id='page-alpi']/ul[5]/li/div[contains(.,'" + ChildBirth[2] + "')]"), "Birth Year");
        By ContactNum = getLocator(inp_contactNumber, BasePage.BY_TYPE.BY_XPATH);
        type(ContactNum, ContactNumber, "ContactNumber");
        By ContactMail = getLocator(inp_contactMail, BasePage.BY_TYPE.BY_XPATH);
        type(ContactMail, Email, "Contact Email");
        By Continue3 = getLocator(btn_continue3, BasePage.BY_TYPE.BY_XPATH);
        click(Continue3, "Continue Button");
        waitForPageLoad();
        AssertText("Your Payment Details");
    }

    public void AllTillInfant(String OriginCountry, String OriginCity, String OriginAirport, String DestinationCountry, String DestinationCity, String DestinationAirport, String StartingDate, String ReturnDate, String Adults, String Children, String Infants, String Class) {
        By FlightManager = getLocator(btn_flightManager, BasePage.BY_TYPE.BY_XPATH);
        click(FlightManager, "Flight Manager");
        By RoundTripBtn = getLocator(btn_roundTrip, BasePage.BY_TYPE.BY_XPATH);
        click(RoundTripBtn, "Round Trip");
        By From = getLocator(inp_From, BY_TYPE.BY_XPATH);
        type(From, OriginAirport, "Airport Name");
        click(getLocator("html/body", BY_TYPE.BY_XPATH), "");
        By To = getLocator(inp_To, BY_TYPE.BY_XPATH);
        type(To, DestinationAirport, "Airport Name");
        click(getLocator("html/body", BY_TYPE.BY_XPATH), "");
        click(By.xpath("//input[@placeholder='Departing']"), "Departure Date");
        click(By.xpath("//table/tbody/tr[3]/td[4]/button"), StartingDate);
        By AdultsCount = getLocator(ddn_adults, BasePage.BY_TYPE.BY_XPATH);
        selectDropDownByValue(AdultsCount, Adults);
        By WithChildren = getLocator(lik_childLink, BasePage.BY_TYPE.BY_XPATH);
        click(WithChildren, "Travel With Children");
        delay(2000);
        By ChildCount = getLocator(ddn_childCount, BasePage.BY_TYPE.BY_XPATH);
        selectDropDownByValue(ChildCount, Children);
        By InfantsCount = getLocator(ddn_infantCount, BasePage.BY_TYPE.BY_XPATH);
        selectDropDownByValue(InfantsCount, Infants);
        By ClassType = getLocator(ddn_class, BasePage.BY_TYPE.BY_XPATH);
        selectDropDownByVisibleText(ClassType, Class);
        By SearchFlights = getLocator(btn_Search, BasePage.BY_TYPE.BY_XPATH);
        click(SearchFlights, "Search Flights");
        waitForPageLoad();
        delay(5000);
    }

    public void InfantError() {
        By InfantCountError = getLocator(lik_infantError, BasePage.BY_TYPE.BY_XPATH);
        addExplicitWait(InfantCountError, "visibility", 100);

        Reporter.log("Error 'More infants than adults' has displayed in red colour as expected.");
    }
}
