package com.tcs.saf.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.tcs.saf.base.BasePage;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.Reporter;

public class PaymentDetailsPage extends BasePage {

    public final String rbt_CreditCard = "//*[@id='ITEM_PAYMENT_FLIGHTS_FLIGHT_MOP_CATEGORIES_0_CC']";
    public final String img_visaCard = "//*[@id='ITEM_PAYMENT_FLIGHTS_img-CC-VI']";
    public final String inp_cardNo = "//*[@id='ITEM_PAYMENT_FLIGHTS_FLIGHT_CC_NUMBER_0Lastnumber']";
    public String inp_cvvNo = "//*[@id='ITEM_PAYMENT_FLIGHTS_FLIGHT_CC_SECURITY_CODE_00']";
    public final String chk_TC = "//*[@id='TERMS_CONDITIONS_TERMS_AGREE_ALL_0']";
    public final String btn_CPaymnet = "//*[@id='TOOLBAR_FINISH_0']";
    public final String inp_AddressLineOne = "//*[@id='BILLING_DETAILS_BILL_ADDRESS_LINE_1_0']";
    public final String inp_City = "//*[@id='BILLING_DETAILS_BILL_CITY_0']";
    public final String inp_PostatlCode = "//*[@id='BILLING_DETAILS_BILL_POSTAL_CODE_0']";
    public final String txt_BookingCode = "//*[@id='reclocs']";

    public PaymentDetailsPage(RemoteWebDriver driver) {
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

    public void selectCard() {
        By CreditCard = getLocator(rbt_CreditCard, BasePage.BY_TYPE.BY_XPATH);
        click(CreditCard, "Slect Card");
        By visaCard = getLocator(img_visaCard, BasePage.BY_TYPE.BY_XPATH);
        click(visaCard, "Visa Card img");
    }

    public void EnterCardNoAndCVV(String CNo, String Cvv) {

        String[] Cnum = CNo.split(" ");
        for (int i = 0; i < 4; i++) {
            String str = inp_cardNo;
            str = str.replace("Lastnumber", Integer.toString(i));
            By CreditCard = getLocator(str, BY_TYPE.BY_XPATH);
            type(CreditCard, Cnum[i], "Card No");
        }

        By cvv = getLocator(inp_cvvNo, BY_TYPE.BY_XPATH);
        type(cvv, Cvv, "CVV");
    }

    public void AddressLineCity(String Address, String city, String postalCode) {

        By AddressLineOne = getLocator(inp_AddressLineOne, BY_TYPE.BY_XPATH);
        type(AddressLineOne, Address, "Address");
        By City = getLocator(inp_City, BY_TYPE.BY_XPATH);
        type(City, city, "city");
        By PostatlCode = getLocator(inp_PostatlCode, BY_TYPE.BY_XPATH);
        type(PostatlCode, postalCode, "city");
    }

    public String AggryandContinue() {
        By TC = getLocator(chk_TC, BasePage.BY_TYPE.BY_XPATH);
        click(TC, "T & C");

        By CPaymnet = getLocator(btn_CPaymnet, BasePage.BY_TYPE.BY_XPATH);
        click(CPaymnet, "CPaymnet");
        By BookingCode = getLocator(txt_BookingCode, BasePage.BY_TYPE.BY_XPATH);
       return getText(BookingCode);
    }
}
