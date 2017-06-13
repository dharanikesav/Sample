package com.tcs.saf.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.tcs.saf.utilities.Log;

/**
 * @author TCS
 *
 */
public class WebDriverFactory {

    /**
     * This function initialize a WebDriver object using on the Browser type and
     * remoteServerUrl
     *
     * @param browserType
     * @param remoteServerUrl
     * @return RemoteWebdriver
     * @throws MalformedURLException
     */
    public static RemoteWebDriver getWebdriver(String browserType,
            URL remoteServerUrl) throws MalformedURLException {

        Logger logger = Log
                .getInstance(Thread.currentThread().getStackTrace()[1]
                        .getClassName());

        logger.info("Browser type is:" + browserType);
        RemoteWebDriver webdriver = null;
        DesiredCapabilities driverCapability = null;
        ResourceBundle browserProperties = getBrowserProperties();

        if ("InternetExplorer".equalsIgnoreCase(browserType)) {
            try {

                driverCapability = DesiredCapabilities.internetExplorer();
                driverCapability.setCapability("browserName", "internet explorer");
                driverCapability.setCapability("acceptSslCerts", "true");
                driverCapability.setCapability("javascriptEnabled", "true");
                driverCapability
                        .setCapability(
                                InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                                true);
                System.setProperty("webdriver.ie.driver",
                        browserProperties.getString("webdriver_ie_driver"));
                logger.info("getWebDriver - Setting webdriver.ie.driver system property as: "
                        + System.getProperty("webdriver.ie.driver"));
            } catch (IllegalStateException e) {
                logger.error("The path to the driver executable must be set by the webdriver.ie.driver system property, Check IE driver path in Global.properties file", e.fillInStackTrace());
                throw new IllegalStateException("The path to the driver executable must be set by the webdriver.ie.driver system property, Check IE driver path in Global.properties file");

            }
        } else if ("Firefox".equalsIgnoreCase(browserType)) {

            driverCapability = DesiredCapabilities.firefox();
            driverCapability.setCapability("browserName", "firefox");

        } else if ("Chrome".equalsIgnoreCase(browserType)) {
            try {
                driverCapability = DesiredCapabilities.chrome();
                driverCapability.setCapability("browserName", "chrome");
                System.setProperty("webdriver.chrome.driver",
                        browserProperties.getString("webdriver_chrome_driver"));

                driverCapability.setCapability("acceptSslCerts", "true");
                driverCapability.setCapability("javascriptEnabled", "true");

                ChromeOptions options = new ChromeOptions();/* added to ignore certificate errors*/

                options.addArguments("--test-type");
                driverCapability.setCapability("chrome.binary", browserProperties.getString("webdriver_chrome_driver"));
                driverCapability.setCapability(ChromeOptions.CAPABILITY, options);

            } catch (IllegalStateException e) {
                logger.error("The path to the driver executable must be set by the webdriver.ie.driver system property, Check IE driver path in Global.properties file", e.fillInStackTrace());
                throw new IllegalStateException("The path to the driver executable must be set by the webdriver.ie.driver system property, Check IE driver path in Global.properties file");
            }
            driverCapability.setCapability("acceptSslCerts", true);
            driverCapability.setJavascriptEnabled(true);
        } else if (browserType == null) {
            logger.error("DesiredCapabilities is null , Unable to instantiate new webDriver. Unrecognised browser identifier. "
                    + browserType + "/n possible causes, \n\t1.'EXECUTION STATUS' column in data sheet is empty for all fields\n\t2.'EXECUTION STATUS' column in data sheet is 'N' for all fields");

        } else {
            logger.error("DesiredCapabilities is null , Unable to instantiate new webDriver. Unrecognised browser identifier. "
                    + browserType);

        }

        webdriver = new RemoteWebDriver(remoteServerUrl, driverCapability);
        return webdriver;

    }

    /**
     * This function reads Browser path from Global properties
     *
     * @return Browser Properties
     */
    public static ResourceBundle getBrowserProperties() {

        ResourceBundle browserProperties = null;
        browserProperties = ResourceBundle.getBundle("global");
        return browserProperties;

    }
}
