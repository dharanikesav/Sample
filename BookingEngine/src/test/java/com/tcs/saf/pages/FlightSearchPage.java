package com.tcs.saf.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.tcs.saf.base.BasePage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlightSearchPage extends BasePage {

    public FlightSearchPage(RemoteWebDriver driver) {
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
   
}
