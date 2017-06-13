package com.tcs.saf.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.tcs.saf.base.BasePage;
import org.openqa.selenium.By;

public class PassengerDetailsPage extends BasePage {

    private final String ddn_titleDD = "//span[@id='DATA_PASSENGERS_ADT_TITLE_0']/div/div/div[@class='arrow_btn']/span";
    private final String ddn_titleDD1 = "//*[@id='DATA_PASSENGERS_ADT_TITLE_0']/div/div/div[2]/span";
    private final String ddn_genderDD = "//span[@id='DATA_PASSENGERS_CHD_GENDER_0']/div/div/div";
    private final String lik_titleOption = "//*[@id='DATA_PASSENGERS_ADT_TITLE_0']/div/div/div[2]/span";
    private final String inp_firstName = "//input[@id='PASSENGERS_ADT_FIRST_NAME_0']";
    private final String inp_firstName2 = "//input[@id='PASSENGERS_ADT_FIRST_NAME_1']";
    private final String inp_lastName = "//input[@id='PASSENGERS_ADT_LAST_NAME_0']";
    private final String inp_lastName2 = "//input[@id='PASSENGERS_ADT_LAST_NAME_1']";
    private final String inp_firstNameC = "//input[@id='PASSENGERS_CHD_FIRST_NAME_0']";
    private final String inp_lastNameC = "//input[@id='PASSENGERS_CHD_LAST_NAME_0']";
    private final String inp_areaCode = "//*[@id='CONTACT_DETAILS_PHONE_PHONE_AREA_0']";
    private final String inp_contactNumber = "//input[@id='CONTACT_DETAILS_PHONE_PHONE_NUMBER_0']";
    private final String inp_contactMail = "//input[@id='CONTACT_DETAILS_EMAIL_0']";
    private final String btn_continue3 = "//*[@id='TOOLBAR_CONTINUE_0']/span";

    public PassengerDetailsPage(RemoteWebDriver driver) {
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
    }

    public void PassengerDetails(String Title, String Fname, String Lname, String AreaCode,String ContactNumber, String Email) {

        waitSeconds(10);
        By TitleDDList = getLocator(ddn_titleDD1, BasePage.BY_TYPE.BY_XPATH);
        click(TitleDDList, "Title");
        click(getLocator("html/body/ul/li[2]/div", BY_TYPE.BY_XPATH), "Title");
        By FirstName = getLocator(inp_firstName, BasePage.BY_TYPE.BY_XPATH);
        type(FirstName, Fname, "FirstName");
        delay(2000);

        By LastName = getLocator(inp_lastName, BasePage.BY_TYPE.BY_XPATH);
        type(LastName, Lname, "LastName");
        delay(2000);

        By areaCode = getLocator(inp_areaCode, BasePage.BY_TYPE.BY_XPATH);
        type(areaCode, AreaCode, "areaCode");
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

}
