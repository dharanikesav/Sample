package com.tcs.saf.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.tcs.saf.base.BasePage;
/*import com.tcs.saf.base.HandleAuthWindow;*/
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.Reporter;


public class LoginPage extends BasePage{
    
  
   public final String lik_Login = "//*[@id='header-profile-toggle']";
   public final String txb_UserName = "//*[@id='lluserid']";
   public final String txb_Password = "//*[@id='llpassword']";
   public final String btn_Login = "//*[@id='ll-btn']";
   public final String lik_MarketSelection = "//*[@id='ls-selection-btn']";
   public final String ddn_CountrySelection = "//*[@id='cl-country']";
   public final String ddn_LanguageSelection = "//*[@id='cl-language']";
   public final String btn_Continue = "//*[@id='cl-form']/button";
   

    public LoginPage(RemoteWebDriver driver) 
    {
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
    
         /*
     * The Login function helps user to login with User id or MAM Card No
     * @param UserName - customer user id or MAM Card No*
     * @param Password - customer password or MAM pin no
     * @return  - none
     */
        public void Login(String UserName,String Password)
        {
       
            By login = getLocator(lik_Login,BasePage.BY_TYPE.BY_XPATH);
            By username = getLocator(txb_UserName, BasePage.BY_TYPE.BY_XPATH);
            By password = getLocator(txb_Password, BasePage.BY_TYPE.BY_XPATH);
            By loginbtn = getLocator(btn_Login, BasePage.BY_TYPE.BY_XPATH);
            
            click(login,"login link");
            type(username,UserName,"Userid");
            type(password,Password,"Password");
            click(loginbtn,"login button");
            Reporter.log("User is able to login successfully");      
                      
            
        }
        
      /*
     * The MarketingSelection function helps to select Makret Country and Language 
     * @param Country - Marketing Country
     * @param Language - To select Language
     * @return  - none
     */
        
        public void MarketingSelection(String Country, String Langauge)
        {
       try {
           By marketSelection = getLocator(lik_MarketSelection,BasePage.BY_TYPE.BY_XPATH);
           By countrySelection = getLocator(ddn_CountrySelection,BasePage.BY_TYPE.BY_XPATH);
           By languageSelection = getLocator(ddn_LanguageSelection,BasePage.BY_TYPE.BY_XPATH);
           By continuebtn = getLocator(btn_Continue,BasePage.BY_TYPE.BY_XPATH);
           
           click(marketSelection ,"Marketing selection link");
           selectDropDownByVisibleText(countrySelection ,Country);
           selectDropDownByVisibleText(languageSelection ,Langauge);
           click(continuebtn ,"Continue button");
       } catch (Exception ex) {
           Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
       }
         
        }
                
        
        
    
    
}
