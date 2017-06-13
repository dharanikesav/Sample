package com.tcs.saf.test;
//TEst here
//AGain
import java.io.IOException;
import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.tcs.saf.base.BaseTest;
import com.tcs.saf.exceptions.DataSheetException;
import com.tcs.saf.exceptions.InvalidBrowserException;
import com.tcs.saf.pages.FlightBookingSearchPage;
import com.tcs.saf.pages.FlightSelectionPage;
import com.tcs.saf.pages.ItemConfirmationPage;
import com.tcs.saf.pages.LoginPage;
import com.tcs.saf.pages.PassengerDetailsPage;
import com.tcs.saf.pages.PaymentDetailsPage;
import jxl.read.biff.BiffException;
import org.testng.Reporter;

public class FlightSearchTest extends BaseTest {

    public FlightSearchTest() {

        super();

    }

    public FlightSearchTest(String testName, String browser, LinkedHashMap<String, String> mapDataSheet) {

        super(testName, browser, mapDataSheet);

    }

    @Factory(dataProvider = "dataSheet")
    public Object[] testCreator(LinkedHashMap<String, String> mapDataSheet) {

        return new Object[]{new FlightSearchTest(this.getClass().getSimpleName(), mapDataSheet.get("Browser"), mapDataSheet)};

    }

    @DataProvider(name = "dataSheet")
    public Object[][] getTestData() throws IOException, InvalidBrowserException, DataSheetException, BiffException {

        return testDataProvider.getTestDataFromExcel(externalSheetPath, this.getClass().getSimpleName());

    }

    @Test
    public void FlightSearchTest() {
        LoginPage lpage = new LoginPage(getDriver());
        FlightBookingSearchPage FligtSearchPage = new FlightBookingSearchPage(getDriver());
        FlightSelectionPage FlightSelection = new FlightSelectionPage(getDriver());
        ItemConfirmationPage ITCOPage = new ItemConfirmationPage(getDriver());
        PassengerDetailsPage Pdetails = new PassengerDetailsPage(getDriver());
        PaymentDetailsPage paymentDetails = new PaymentDetailsPage(getDriver());
        lpage.launchBaseURL();

        lpage.MarketingSelection(getValue("Market"), getValue("Language"));

        FligtSearchPage.BookingDetails(getValue("Browser"), getValue("From_Country"), getValue("From_Airport"), getValue("From"), getValue("To_Country"), getValue("To_Airport"), getValue("To"), getValue("Departing"), getValue("Returning"), getValue("Adults"), getValue("Class"));

        FlightSelection.outboundFlightSelection(getValue("OutboundFlightNo"), getValue("OutboundEconomyClassSelection"));
        FlightSelection.inboundFlightSelection(getValue("InboundFlightNo"), getValue("InboundEconomyClassSelection"));
        FlightSelection.clickFlightSelectionContinueButton();
        ITCOPage.iTCOClickSeatSelectionButton();
        ITCOPage.outboundSeatSelection();
        ITCOPage.inboundSeatSelection();
        String normalSeatPrice = ITCOPage.SeatPrice();
        ITCOPage.clickAddShoppingCart();
       
       // ITCOPage.backtopassengerDetails();
        ITCOPage.toPassengersDetails();
        Pdetails.PassengerDetails(getValue("Title"), getValue("Fname"), getValue("Lname"), getValue("areaCode"), getValue("ContactNumber"), getValue("Email"));
        paymentDetails.selectCard();
        paymentDetails.EnterCardNoAndCVV(getValue("CNo"), getValue("CVV"));
        paymentDetails.AddressLineCity(getValue("Address"), getValue("city"), getValue("pCode"));
        String BCode = paymentDetails.AggryandContinue();
        Reporter.log("Booking Code is :"+BCode);

    }

}
