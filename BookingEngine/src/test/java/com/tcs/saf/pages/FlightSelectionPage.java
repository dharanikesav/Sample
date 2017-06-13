package com.tcs.saf.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.tcs.saf.base.BasePage;
import java.util.List;
/*import com.tcs.saf.base.HandleAuthWindow;*/
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FlightSelectionPage extends BasePage {

    public final String lik_Login = "//*[@id='header-profile-toggle']";
    public final String txb_UserName = "//*[@id='lluserid']";
    public final String txb_Password = "//*[@id='llpassword']";
    public final String btn_Login = "//*[@id='ll-btn']";
    public final String lik_MarketSelection = "//*[@id='ls-selection-btn']";
    public final String ddn_CountrySelection = "//*[@id='cl-country']";
    public final String ddn_LanguageSelection = "//*[@id='cl-language']";
    public final String btn_Continue = "//*[@id='cl-form']/button";

    public FlightSelectionPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public boolean hasPageLoaded() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPageUrl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void launchBasePage() {
        launchBaseURL();
        delay(500);
    }

    public String rbt_outboundflightselectionbutton = "//input[contains(@id,'input_economy')][@type='radio'][@name='outbound']";
    public String rbt_inboundflightselectionbutton = "//input[contains(@id,'input_economy')][@type='radio'][@name='inbound']";
    public String lik_outboundShowMoreFlights = "//*[@id='branded-fares-more-flights-0']/div";
    public String lik_inboundShowMoreFlights = "//*[@id='branded-fares-more-flights-1']/div";
    public String btn_outboundeconamyClassSelction = "//*[@id='outbound-increment-EconomyClassSelection-footer']/button";
    public String btn_inboundeconamyClassSelction = "//*[@id='inbound-increment-EconomyClassSelection-footer']/button";
    public String btn_flightselectioncontinue = "//*[@id='TOOLBAR_CONTINUE_0']";

    @SuppressWarnings("UnusedAssignment")
    public void outboundFlightSelection(String OutboundFlightNumber, String EconomyClassSelection) {
        By outboundflightselectionbutton = getLocator(rbt_outboundflightselectionbutton, BasePage.BY_TYPE.BY_XPATH);
        By outboundshowMoreFlights = getLocator(lik_outboundShowMoreFlights, BasePage.BY_TYPE.BY_XPATH);

        if (isElementVisible(outboundshowMoreFlights, "Show More Flights")) {
            click(outboundshowMoreFlights, "Show More Flights");
        }

        List<WebElement> inputButtons = webElements(outboundflightselectionbutton, "outboundflightselectionbutton");

        for (int i = 0; i < inputButtons.size(); i++) {

            String rbt_outboundbutton = "(//input[contains(@id,'input_economy')][@type='radio'][@name='outbound'])[flightNo]";

            rbt_outboundbutton = rbt_outboundbutton.replace("flightNo", Integer.toString(i + 1));

            By outboundbutton = getLocator(rbt_outboundbutton, BasePage.BY_TYPE.BY_XPATH);

            click(outboundbutton, "outboundbutton");

            String txt_FlgithNo = "//*[@id='branded-fares-flight-availability-0']/table/tbody/tr[increment]/td/div/div[2]/div/div[2]/div[2]/ul/li[2]";
            txt_FlgithNo = txt_FlgithNo.replace("increment", Integer.toString(i * 2 + 3));
            By FlifghtNO = getLocator(txt_FlgithNo, BY_TYPE.BY_XPATH);
            if (getText(FlifghtNO).contains(OutboundFlightNumber)) {

                if (EconomyClassSelection.equalsIgnoreCase("Economy Light")) {
                    btn_outboundeconamyClassSelction = btn_outboundeconamyClassSelction.replace("EconomyClassSelection", "0");
                    btn_outboundeconamyClassSelction = btn_outboundeconamyClassSelction.replace("increment", Integer.toString(i));

                } else if (EconomyClassSelection.equalsIgnoreCase("Economy Classic")) {
                    btn_outboundeconamyClassSelction = btn_outboundeconamyClassSelction.replace("EconomyClassSelection", "1");
                    btn_outboundeconamyClassSelction = btn_outboundeconamyClassSelction.replace("increment", Integer.toString(i));
                } else if (EconomyClassSelection.equalsIgnoreCase("Economy Flex")) {
                    btn_outboundeconamyClassSelction = btn_outboundeconamyClassSelction.replace("EconomyClassSelection", "2");
                    btn_outboundeconamyClassSelction = btn_outboundeconamyClassSelction.replace("increment", Integer.toString(i));
                }

            }

            By econamyClassSelction = getLocator(btn_outboundeconamyClassSelction, BasePage.BY_TYPE.BY_XPATH);
            click(econamyClassSelction, "Economy Class Selection");

            break;
        }
    }

    @SuppressWarnings("UnusedAssignment")
    public void inboundFlightSelection(String InboundFlightNumber, String EconomyClassSelection) {
        By inboundflightselectionbutton = getLocator(rbt_inboundflightselectionbutton, BasePage.BY_TYPE.BY_XPATH);
        By inboundshowMoreFlights = getLocator(lik_inboundShowMoreFlights, BasePage.BY_TYPE.BY_XPATH);

        if (isElementVisible(inboundshowMoreFlights, "Show More Flights")) {
            click(inboundshowMoreFlights, "Show More Flights");
        }

        List<WebElement> inputButtons = webElements(inboundflightselectionbutton, "inboundflightselectionbutton");

        for (int i = 0; i < inputButtons.size(); i++) {

            String rbt_inboundbutton = "(//input[contains(@id,'input_economy')][@type='radio'][@name='inbound'])[flightNo]";

            rbt_inboundbutton = rbt_inboundbutton.replace("flightNo", Integer.toString(i + 1));

            By inboundbutton = getLocator(rbt_inboundbutton, BasePage.BY_TYPE.BY_XPATH);

            click(inboundbutton, "inboundbutton");

            String inboundbuttoncounter = getAttribute(inboundbutton, "id", "Attrubute value");
            inboundbuttoncounter = inboundbuttoncounter.replaceAll("[^\\d.]", "").replaceAll("[-+.^:,]", "");

            String txt_FlgithNo = "//*[@id='branded-fares-flight-availability-1']/table/tbody/tr[increment]/td/div/div[2]/div/div[2]/div[2]/ul/li[2]";

            txt_FlgithNo = txt_FlgithNo.replace("increment", Integer.toString(i * 2 + 3));
            By FlifghtNO = getLocator(txt_FlgithNo, BY_TYPE.BY_XPATH);
            if (getText(FlifghtNO).contains(InboundFlightNumber)) {

                if (EconomyClassSelection.equalsIgnoreCase("Economy Light")) {
                    btn_inboundeconamyClassSelction = btn_inboundeconamyClassSelction.replace("EconomyClassSelection", "0");
                    btn_inboundeconamyClassSelction = btn_inboundeconamyClassSelction.replace("increment", inboundbuttoncounter);

                } else if (EconomyClassSelection.equalsIgnoreCase("Economy Classic")) {
                    btn_inboundeconamyClassSelction = btn_inboundeconamyClassSelction.replace("EconomyClassSelection", "1");
                    btn_inboundeconamyClassSelction = btn_inboundeconamyClassSelction.replace("increment", inboundbuttoncounter);
                } else if (EconomyClassSelection.equalsIgnoreCase("Economy Flex")) {
                    btn_inboundeconamyClassSelction = btn_inboundeconamyClassSelction.replace("EconomyClassSelection", "2");
                    btn_inboundeconamyClassSelction = btn_inboundeconamyClassSelction.replace("increment", inboundbuttoncounter);
                }

                By econamyClassSelction = getLocator(btn_inboundeconamyClassSelction, BasePage.BY_TYPE.BY_XPATH);
                click(econamyClassSelction, "Economy Class Selection");
                break;
            }
        }
    }
    
    public void clickFlightSelectionContinueButton(){
        
        By flightselectioncontinue = getLocator(btn_flightselectioncontinue, BY_TYPE.BY_XPATH);
        click(flightselectioncontinue,"Flight Selection Continue");
        waitForPageLoad();
        waitSeconds(10);
    }

}
