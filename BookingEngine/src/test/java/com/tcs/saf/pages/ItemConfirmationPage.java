package com.tcs.saf.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.tcs.saf.base.BasePage;
import java.util.List;
/*import com.tcs.saf.base.HandleAuthWindow;*/
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ItemConfirmationPage extends BasePage {
    
    public String btn_iTCOSeatSeelction = "//*[@id='TEASERS_TEASER_SEAT_CUSTOMIZE_0']";
    
    public ItemConfirmationPage(RemoteWebDriver driver) {
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
    
    public final String btn_outboundMLegSeat = "//*[contains(@id,'SEGMENT_1_seat')]/span[contains(@class,'legroom ')]";
    public final String btn_inboundMLegSeat = "//*[contains(@id,'SEGMENT_2_seat')]/span[contains(@class,'legroom ')]";
    
    public final String btn_outboundSeat = "//*[contains(@id,'SEGMENT_1_seat')]/span[contains(@class,'standard')]";
    public final String btn_inboundSeat = "//*[contains(@id,'SEGMENT_2_seat')]/span[contains(@class,'standard')]";
    public final String btn_addshoppingcart = "//*[@id='TOOLBAR_BUTTON_ADD_SHOPPING_CART_0']";
    public final String btn_outboundSaveSeat = "//*[@id='SEAT_SAVE_SEATS_0']";
    public final String btn_inboundSaveSeat = "//*[@id='SEAT_SAVE_SEATS_1']";
    public final String btn_confirmaddshoppingcart = "//*[@id='EMERGENCY_EXIT_EMX_OK_0']";
    public final String txt_normalSeatPrice = "//*[@id='total_price_to_pay']";
    public final String btn_editSeat = "//*[@id='IRC_FLIGHTS_IRC_FLIGHTS_EDIT_SEATS_0']";
    public final String btn_expandOutBoundSeatSelection = "//*[@id='SEC_1']/h3/aside/span[2]";
    public final String btn_expandInBoundSeatSelection = "//*[@id='SEC_2']/h3/aside/span[2]";
    public final String btn_backbuttonforconfigureformSeatPage = "//*[@id='bottom']/button";
    public final String btn_updateSeats = "//*[@id='TOOLBAR_BUTTON_ADD_SHOPPING_CART_0']";
    public final String btn_toPassangersDetailsPage = "//*[@id='TOOLBAR_2_CHECKOUT_0']";
    
    public void iTCOClickSeatSelectionButton() {
        By iTCOSeatSeelction = getLocator(btn_iTCOSeatSeelction, BY_TYPE.BY_XPATH);
        click(iTCOSeatSeelction, "Seat Selection Button");
        delay(5000);
    }
    
    public void outboundSeatSelection() {
        waitSeconds(15);
        By outboundMLegSeat = getLocator(btn_outboundMLegSeat, BasePage.BY_TYPE.BY_XPATH);
        if (isElementPresent(outboundMLegSeat, "outboundMLegSeat")) {
            click(getLocator("(" + btn_outboundMLegSeat + ")[1]", BY_TYPE.BY_XPATH), "outboundMLegSeat");
        } else {
            click(getLocator("(" + btn_outboundSeat + ")[1]", BY_TYPE.BY_XPATH), "outboundSeat");
        }
        By outboundSaveSeat = getLocator(btn_outboundSaveSeat, BY_TYPE.BY_XPATH);
        click(outboundSaveSeat, "outboundSaveSeat");
        
    }
    
    public void inboundSeatSelection() {
        
        By inboundMLegSeat = getLocator(btn_inboundMLegSeat, BasePage.BY_TYPE.BY_XPATH);
        if (isElementPresent(inboundMLegSeat, "outboundMLegSeat")) {
            click(getLocator("(" + btn_inboundMLegSeat + ")[1]", BY_TYPE.BY_XPATH), "outboundMLegSeat");
        } else {
            click(getLocator("(" + btn_inboundSeat + ")[1]", BY_TYPE.BY_XPATH), "outboundSeat");
        }
        By inboundSaveSeat = getLocator(btn_inboundSaveSeat, BY_TYPE.BY_XPATH);
        click(inboundSaveSeat, "inboundSaveSeat");
        
    }
    
    public String SeatPrice() {
        By normalSeatPrice = getLocator(txt_normalSeatPrice, BY_TYPE.BY_XPATH);
        return getText(normalSeatPrice);
    }
    
    public void clickAddShoppingCart() {
        By addshoppingcart = getLocator(btn_addshoppingcart, BasePage.BY_TYPE.BY_XPATH);
        By confirmaddshoppingcart = getLocator(btn_confirmaddshoppingcart, BasePage.BY_TYPE.BY_XPATH);
        click(confirmaddshoppingcart, "Add Shopping Cart Button");
        click(addshoppingcart, "Add Shopping Cart Button");
        waitForPageLoad();
    }
    
    public void editSeat() {
        By editSeat = getLocator(btn_editSeat, BasePage.BY_TYPE.BY_XPATH);
        click(editSeat, "editSeat");
    }
    
    public void updateoutboundSeatSelection() {
        delay(5000);
        By expandOutBoundSeatSelection = getLocator(btn_expandOutBoundSeatSelection, BY_TYPE.BY_XPATH);
        click(expandOutBoundSeatSelection, "outboundSaveSeat");
        
        By outboundMLegSeat = getLocator(btn_outboundMLegSeat, BasePage.BY_TYPE.BY_XPATH);
        if (isElementPresent(outboundMLegSeat, "outboundMLegSeat")) {
            click(getLocator("(" + btn_outboundMLegSeat + ")[1]", BY_TYPE.BY_XPATH), "outboundMLegSeat");
        } else {
            click(getLocator("(" + btn_outboundSeat + ")[1]", BY_TYPE.BY_XPATH), "outboundSeat");
        }
        
        By outboundSaveSeat = getLocator(btn_outboundSaveSeat, BY_TYPE.BY_XPATH);
        click(outboundSaveSeat, "outboundSaveSeat");
        
    }
    
    public void updateinboundSeatSelection() {
        
        By inboundMLegSeat = getLocator(btn_inboundMLegSeat, BasePage.BY_TYPE.BY_XPATH);
        if (isElementPresent(inboundMLegSeat, "outboundMLegSeat")) {
            click(getLocator("(" + btn_inboundMLegSeat + ")[1]", BY_TYPE.BY_XPATH), "outboundMLegSeat");
        } else {
            click(getLocator("(" + btn_inboundSeat + ")[1]", BY_TYPE.BY_XPATH), "outboundSeat");
        }
        By inboundSaveSeat = getLocator(btn_inboundSaveSeat, BY_TYPE.BY_XPATH);
        click(inboundSaveSeat, "inboundSaveSeat");
        By confirmaddshoppingcart = getLocator(btn_confirmaddshoppingcart, BasePage.BY_TYPE.BY_XPATH);
        if (isElementEnabled(inboundSaveSeat, btn_editSeat)) {
            click(confirmaddshoppingcart, "Add Shopping Cart Button");
        }
    }
    
    public void backtopassengerDetails() {
        By backbuttonforconfigureformSeatPage = getLocator(btn_backbuttonforconfigureformSeatPage, BasePage.BY_TYPE.BY_XPATH);
        By updateSeats = getLocator(btn_updateSeats, BasePage.BY_TYPE.BY_XPATH);
        if (isElementPresent(backbuttonforconfigureformSeatPage, "Back button")) {
            click(backbuttonforconfigureformSeatPage, "back to button");
        } else {
            click(updateSeats, "updateSeats button");
        }
        
    }
    
    public void toPassengersDetails() {
        By backbuttonforPdetailsformSeatPage = getLocator(btn_toPassangersDetailsPage, BasePage.BY_TYPE.BY_XPATH);
        click(backbuttonforPdetailsformSeatPage, "continue button");
    }
}
