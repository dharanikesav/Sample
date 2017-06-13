package com.tcs.saf.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.InvalidCookieDomainException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Function;

import com.tcs.saf.utilities.Log;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.logging.Level;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.Reporter;

public abstract class BasePage {

    protected enum BY_TYPE {

        BY_XPATH, BY_LINKTEXT, BY_ID, BY_CLASSNAME,
        BY_NAME, BY_CSSSELECTOR, BY_PARTIALLINKTEXT, BY_TAGNAME
    };

    public final Logger logger = Log.getInstance(Thread.currentThread()
            .getStackTrace()[1].getClassName());
    public RemoteWebDriver driver;

    public RemoteWebDriver getDriver() {
        return driver;
    }

    public abstract boolean hasPageLoaded();

    public abstract String getPageUrl();

    public BasePage(RemoteWebDriver driver) {
        this.driver = driver;

    }

    protected By getLocator(String locator, BY_TYPE type) {

        switch (type) {
            case BY_XPATH:

                return By.xpath(locator);

            case BY_LINKTEXT:
                return By.linkText(locator);
            case BY_ID:

                return By.id(locator);

            case BY_CSSSELECTOR:
                return By.cssSelector(locator);
            case BY_CLASSNAME:

                return By.className(locator);

            case BY_NAME:
                return By.name(locator);

            case BY_PARTIALLINKTEXT:
                return By.partialLinkText(locator);

            case BY_TAGNAME:
                return By.tagName(locator);

        }
        throw new IllegalArgumentException("Invalid By Type, Please provide correct locator type");

    }

    /**
     * This function launches the Application URL from grid.properties
     */
    public void launchBaseURL() {
        String URL = BaseTest.getBaseURL();
        if (URL.length() != 0) {
            try {
                driver.get(URL);
                Reporter.log("The Base URL:" + URL + "is loaded successfully");
            } catch (UnreachableBrowserException e) {
                logger.error("Unable to load the Base URL: ", e.fillInStackTrace());
                throw new UnreachableBrowserException("Unable to load the Base URL: " + URL);
            }
        } else {
            logger.error("Unable to load the Base URL: " + URL + " Please provide a valid Base URL");
            throw new UnreachableBrowserException("Unable to load the Base URL: "
                    + URL + " Please provide a valid Base URL.");
        }
    }

    /**
     * This function is to navigate the browser to a URL
     *
     * @param URL - URL to which browser has to be navigated
     */
    public void navigateToURL(String URL) {
        try {
            driver.navigate().to(URL);
        } catch (UnreachableBrowserException e) {
            logger.error("Unable to launch the URL: " + URL);
            throw new UnreachableBrowserException("Unable to launch the URL: " + URL);
        }
    }

    /**
     * This function returns the Current Window URL
     *
     * @return String - returns the Current Window URL
     */
    public String getCurrentURL() {
        Reporter.log("The current Browser URL returned is" + driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    /**
     * This function returns the Current Window Title
     *
     * @return String - returns the Current Window Title
     */
    public String getPageTitle() {
        try {
            delay(10000);
            Reporter.log("The Current Window title is " + driver.getTitle());

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BasePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return driver.getTitle();
    }

    /**
     * This function checks whether the Current Window URL is same as the
     * Expected
     *
     * @param expectedURL - URL expected in the Current Window
     * @return boolean - returns true if the CurrentWindow URL matches the
     * expectedURL, else returns false
     */
    public boolean isURLAsExpected(String expectedURL) {
        Reporter.log("The Current URL:" + getCurrentURL() + "; Expected URL:"
                + expectedURL);
        return expectedURL.equals(getCurrentURL());
    }

    /**
     * This function is to load the previous URL in the browser history.
     *
     */
    public void navigateBack() {
        try {
            driver.navigate().back();
            Reporter.log("User is able to do backward page navigation successfully");
        } catch (WebDriverException e) {
            logger.error("The page cannot be navigated backward", e.fillInStackTrace());
            throw new WebDriverException("The page cannot be navigated backward");
        }
    }

    /**
     * This function loads the URL which is forward in the browser's history.
     * Does nothing if we are on the latest page viewed.
     *
     */
    public void navigateForward() {
        try {
            driver.navigate().forward();
            Reporter.log("User is able to do forward page navigation successfully");
        } catch (UnreachableBrowserException e) {
            logger.error("The page cannot be navigated forward", e.fillInStackTrace());
            throw new UnreachableBrowserException("The page cannot be navigated forward");
        }
    }

    /**
     * This function refresh the current page
     *
     */
    public void refreshPage() {
        try {
            driver.navigate().refresh();
            Reporter.log("User is able to do page refresh successfully");
        } catch (UnreachableBrowserException e) {
            logger.error("The page cannot be refreshed", e.fillInStackTrace());
            throw new UnreachableBrowserException("The page cannot be refreshed");
        }
    }

    /**
     * This function is to make the driver wait explicitly for a condition to be
     * satisfied
     *
     * @param locator - By object of the element whose
     * visibility/presence/clickability has to be checked
     */
    public void addExplicitWait(By locator, String condition, int inttimeoutinseconds) {

        WebDriverWait webDriverWait = new WebDriverWait(driver, inttimeoutinseconds, 250L);
        try {
            if (condition.equalsIgnoreCase("visibility")) {
                webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
                logger.info("Driver waits explicitly until the element with" + locator.
                        toString().replace("By.", " ") + " is visible");
            } else if (condition.equalsIgnoreCase("clickable")) {
                webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
                logger.info("Driver waits explicitly until the element with" + locator.
                        toString().replace("By.", " ") + " is visible");
            } else if (condition.equalsIgnoreCase("presence")) {
                webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
                logger.info("Driver waits explicitly until the element with" + locator.
                        toString().replace("By.", " ") + " is visible");
            } else {
                logger.error("Condition String should be visibility or clickable or presence");
            }
        } catch (NoSuchElementException e) {
            logger.error("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        } catch (UnsupportedCommandException e) {
            logger.error("The condition given to check the elemnt with"
                    + locator.toString().replace("By.", " ")
                    + " is invalid", e.fillInStackTrace());
            throw new NoSuchElementException("The condition given to check the elemnt with"
                    + locator.toString().replace("By.", " ")
                    + " is invalid", e.fillInStackTrace());
        }
    }

    /**
     * This function clicks on the element which can be located by the By Object
     *
     * @param locator - By object to locate the element to be clicked
     * @param locatorName - Name of the locator to declared. i.e., Name of the
     * locator_Button,Name of the locator_Link,etc
     */
    public void click(By locator, String locatorName) {
        try {
            // addExplicitWait Added by Lufthansa project - Author: 206247                   
            addExplicitWait(locator, "clickable", 1000);//clickable,presence,visibility
            driver.findElement(locator).click();
            waitForPageLoad();
            Reporter.log("User is able to click on " + locatorName
                    + " successfully");
            logger.info("Click is performed on element with"
                    + locator.toString().replace("By.", " "));
        } catch (NoSuchElementException e) {
            logger.error("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        }
    }

    /**
     * This function is to pass a text into an input field within UI
     *
     * @param locator - By object to locate the input field to which text has to
     * be send
     * @param value - Text value which is to be send to the input field
     * @param locatorName - Name of the locator to declared. i.e., Name of the
     * locator_Button,Name of the locator_Link,etc
     */
    public void type(By locator, String value, String locatorName) {
        try {
            // addExplicitWait Added by Lufthansa project - Author: 206247                   
            addExplicitWait(locator, "visibility", 5000);//clickable,presence,visibility
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(value);
            Reporter.log("User is able to enter " + value + " into " + locatorName + " successfully");
            logger.info("String " + value + " is send to element with"
                    + locator.toString().replace("By.", " "));
        } catch (NoSuchElementException e) {
            logger.error("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        }
    }

    /**
     * This function is to get the visible text of an element within UI
     *
     * @param locator - By object to locate the element from which the text has
     * to be taken
     * @return String - returns the innertext of the specified element
     */
    public String getText(By locator) {
        String text = null;
        try {
            logger.info("The value on the field with"
                    + locator.toString().replace("By.", " ")
                    + " is obtained");
            text = driver.findElement(locator).getText();
            Reporter.log("Text retreived from page is " + locator + "");
        } catch (NoSuchElementException e) {
            logger.error("Unable to get the text. The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found", e.fillInStackTrace());
            throw new NoSuchElementException("Unable to get the text. The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        }
        return text;
    }

    /**
     * This function is to select a dropdown option using its index
     *
     * @param locator - By object to locate the dropdown which is to be selected
     * @param index - index of the dropdown option to be selected
     */
    public void selectDropDownByIndex(By locator, int index) {
        try {
            Select dropDown = new Select(driver.findElement(locator));
            dropDown.selectByIndex(index);
            Reporter.log("User is able to select the dropdown option with index: " + index
                    + " successfully");
            logger.info("The dropdown option with index: " + index
                    + " is selected");
        } catch (NoSuchElementException e) {
            logger.error("Unable to select the dropdown; The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        }
    }

    /**
     * This function is to select the dropdown options that have a value
     * matching the argument
     *
     * @param locator - By object to locate the dropdown which is to be selected
     * @param value - value to match against the dropdown option to be selected
     */
    public void selectDropDownByValue(By locator, String value) {
        try {
            Select dropDown = new Select(driver.findElement(locator));
            dropDown.selectByValue(value);
            Reporter.log("User is able to select the dropdown option with value: " + value
                    + " successfully");
            logger.info("The dropdown option with value: " + value
                    + " is selected");
        } catch (NoSuchElementException e) {
            logger.error("Unable to select the dropdown; The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        }
    }

    /**
     * This function is to select the dropdown options that displays text
     * matching the argument
     *
     * @param locator - By object to locate the dropdown which is to be selected
     * @param visibleText - visible text to match against the dropdown option to
     * be selected
     */
    public void selectDropDownByVisibleText(By locator, String visibleText) {
        try {
            Select dropDown = new Select(driver.findElement(locator));
            dropDown.selectByVisibleText(visibleText);
            Reporter.log("User is able to select the dropdown option with text: " + visibleText
                    + " successfully");
            logger.info("The dropdown option with text: " + visibleText
                    + " is selected");
        } catch (NoSuchElementException e) {
            logger.error("Unable to select the dropdown; The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        }
    }

    /**
     * This function is to move the mouse pointer to the specified location
     *
     * @param locator - By object to locate the element to which mouse pointer
     * has to be moved
     * @param locatorName - Name of the locator to declared. i.e., Name of the
     * locator_Button,Name of the locator_Link,etc
     */
    public void mouseOver(By locator, String locatorName) {
        try {
            new Actions(driver).moveToElement(driver.findElement(locator))
                    .build().perform();
            Reporter.log("User is able to perform Mouse hover on " + locatorName
                    + " Tab successfully");
            logger.info("Mouse hover is performed on element with"
                    + locator.toString().replace("By.", " "));
        } catch (NoSuchElementException e) {
            logger.error("Unable to perform MouseOver; The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        }
    }

    /**
     * This function is to click on an element by moving the mouse pointer to
     * the specified location or to read the tip of a mouse
     *
     * @param locator - By object to locate the element to which mouse pointer
     * has to be moved
     * @param locatorName - Name of the locator to declared. i.e., Name of the
     * locator_Button,Name of the locator_Link,etc
     */
    public void moveMouseTipAndClick(By locator, String locatorName) {
        try {
            WebElement element = driver.findElement(locator);
            Locatable hoverItem = (Locatable) element;
            Mouse mouse = ((HasInputDevices) driver).getMouse();
            mouse.mouseMove(hoverItem.getCoordinates());
            mouse.click(hoverItem.getCoordinates());
            Reporter.log("User is able to perform Mouse hover on " + locatorName
                    + " Tab successfully");
        } catch (NoSuchElementException e) {
            logger.error("Unable to perform click; The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        }
    }

    /**
     * This function performs a click-and-hold at the location of the source
     * element; moves to the location of the target element, then releases the
     * mouse.
     *
     * @param initialElementLocator - By object of the initial location of the
     * source webelement
     * @param finalElementLocator - By object of the final location where the
     * webelement has to be moved
     * @param locatorName - Name of the locator to declared. i.e., Name of the
     * locator_Button,Name of the locator_Link,etc *
     */
    public void dragAndDrop(By initialElementLocator, By finalElementLocator, String locatorName) {
        try {
            Actions builder = new Actions(driver);
            Action dragAndDrop = builder
                    .clickAndHold(driver.findElement(initialElementLocator))
                    .moveToElement(driver.findElement(finalElementLocator))
                    .release(driver.findElement(finalElementLocator))
                    .build();
            dragAndDrop.perform();
            Reporter.log("User is able to perform Drag And Drop on " + locatorName + " successfuly");
            logger.info("The element is draged from"
                    + initialElementLocator.toString().replace("By.", " ")
                    + " to"
                    + finalElementLocator.toString().replace("By.", " "));
        } catch (NoSuchElementException e) {
            logger.error("Unable to perform dragAndDrop;The element is not draged from"
                    + initialElementLocator.toString().replace("By.", " ")
                    + " to"
                    + finalElementLocator.toString().replace("By.", " "), e.fillInStackTrace());
            throw new NoSuchElementException("Unable to perform dragAndDrop;The element is not draged from"
                    + initialElementLocator.toString().replace("By.", " ")
                    + " to"
                    + finalElementLocator.toString().replace("By.", " "));
        }
    }

    /**
     * This function is to perform double click on a webelement
     *
     * @param locator - By object of the webelement on which double click has to
     * be performed
     * @param locatorName - Name of the locator to declared. i.e., Name of the
     * locator_Button,Name of the locator_Link,etc *
     */
    public void doubleClick(By locator, String locatorName) {
        try {
            Actions builder = new Actions(driver);
            builder.doubleClick(driver.findElement(locator));
            Reporter.log("User is able to perform Double Click is performed on " + locatorName + " successfuly");
            logger.info("The element with" + locator.toString().replace("By.", " ")
                    + " is right clicked");
        } catch (NoSuchElementException e) {
            logger.error("Unable to perform doubleClick; The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        }
    }

    /**
     * This funtion is to maximize the Current Browser Window
     *
     */
    public void maximizeWindow() {
        try {
            driver.manage().window().maximize();
            Reporter.log("Window is maximized");
            logger.info("Window is maximized");
        } catch (UnreachableBrowserException e) {
            logger.error("Unable to maximize the window: ", e.fillInStackTrace());
            throw new NoSuchElementException("Unable to maximize the window");
        }
    }

    /**
     * This function is to add a time delay
     *
     * @param time - time duration in MilliSeconds
     */
    @SuppressWarnings("InfiniteRecursion")
    public void delay(long time) {
        try {
            Thread.sleep(time);
            logger.info("Delay for " + time + " MilliSeconds is added");
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(BasePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This function is to get the current window handle
     *
     * @return windowHandle - returns the handle of current browser window
     */
    public String getWindowHandle() {
        String windowHandle = driver.getWindowHandle();
        logger.info("The current window handle " + windowHandle
                + " is returned");
        try {
            windowHandle = driver.getWindowHandle();
            logger.info("The current window handle " + windowHandle + " is returned");
        } catch (WebDriverException e) {
            logger.error("Unable to returm the window handle: ", e.fillInStackTrace());
            throw new WebDriverException("Unable to returm the window handle");
        }
        return windowHandle;
    }

    /**
     * This function is to switch the driver from Current Window to newly opened
     * Window
     */
    public void switchToWindow() {
        try {
            for (String windowHandle : driver.getWindowHandles()) {
                driver.switchTo().window(windowHandle);
            }
            Reporter.log("User is able to switch between windows successfuly");
            logger.info("The window is switched");
        } catch (NoSuchWindowException e) {
            logger.error("Unable to switch the window: ", e.fillInStackTrace());
            throw new NoSuchWindowException("Unable to switch the window");
        }
    }

    /**
     * This function is to switch into a frame using frame index
     *
     * @param index - index of the frame to which driver has to be switched into
     * @param frameName - name of the frame
     */
    public void switchToFrameByIndex(int index, String frameName) {
        try {
            driver.switchTo().frame(index);
            Reporter.log("User is able to switch into frame:" + frameName + " successfully");
            logger.info("The driver is switched into frame");
        } catch (NoSuchFrameException e) {
            logger.error("Unable to switch into frame: ", e.fillInStackTrace());
            throw new NoSuchFrameException("Unable to switch into frame");
        }
    }

    /**
     * This function is to switch into a frame using the frame name
     *
     * @param frameName - name of the frame to which driver has to be switched
     * into
     */
    public void switchToFrameByName(String frameName) {
        if (!frameName.equalsIgnoreCase(null)) {
            try {
                driver.switchTo().frame(frameName);
                Reporter.log("User is able to switch into frame:" + frameName + " successfully");
                logger.info("The driver is switched to frame: " + frameName);
            } catch (NoSuchFrameException e) {
                logger.error("Unable to switch into frame:", e.fillInStackTrace());
                throw new NoSuchFrameException("Unable to switch into frame");
            }
        }
        logger.info("Unable to switch into frame as framename is null");
    }

    /**
     * This function is to switch into a frame; frame is located as a webelemet
     *
     * @param locator - By object of the webelemet using which frame can be
     * located
     * @param frameName - name of the frame *
     */
    public void switchToFrameByWebElement(By locator, String frameName) {
        try {
            driver.switchTo().frame(driver.findElement(locator));
            Reporter.log("User is able to switch into frame:" + frameName + " successfully");
            logger.info("The driver is switched to frame with"
                    + locator.toString().replace("By.", " "));
        } catch (NoSuchFrameException e) {
            logger.error("Unable to switch into frame: ", e.fillInStackTrace());
            throw new NoSuchFrameException("Unable to switch into frame");
        }
    }

    /**
     * This function is to click on a webelemet using JavascriptExecutor
     *
     * @param locator - By object of the webelement which is to be clicked
     * @param locatorName - Name of the locator to declared. *
     */
    public void clickUsingJavascriptExecutor(By locator, String locatorName) {
        try {
            JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;;
            WebElement webElement = driver.findElement(locator);
            javaScriptExecutor.executeScript("arguments[0].click();", webElement);
            Reporter.log("User is able to click " + locatorName + " successfully");
            logger.info("The element with"
                    + locator.toString().replace("By.", " ")
                    + " is clicked");
            waitForPageLoad();
        } catch (NoSuchElementException e) {
            logger.error("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        }

    }

    /**
     * This function is to scroll the browser window to a webelement using
     * JavascriptExecutor
     *
     * @param locator - By object of the webelement to which the window has to
     * be scrolled
     * @param locatorName - Name of the locator to declared.
     */
    public void scrollToElementUsingJavascriptExecutor(By locator, String locatorName) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement webElement = driver.findElement(locator);
            js.executeScript("arguments[0].scrollIntoView(true);", webElement);
            Reporter.log("User is able to scroll into " + locatorName + " on browser successfully");
            logger.info("Browser window is scrolled to element with"
                    + locator.toString().replace("By.", " "));
        } catch (NoSuchElementException e) {
            logger.error("Unable to scroll: ", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        } catch (MoveTargetOutOfBoundsException e) {
            logger.error("Unable to scroll: ", e.fillInStackTrace());
            throw new MoveTargetOutOfBoundsException("Target element provided with"
                    + locator.toString().replace("By.", " ") + "is invalid");
        }

    }

    /**
     * This function is to check whether a webelement is visible or not
     *
     * @param locator - By object of the webelement which is to be checked
     * @return boolean - returns true if the specified webelement is visible,
     * else it will return false
     */
    public boolean isElementVisible(By locator, String locatorName) {
        Reporter.log(" " + locatorName + " is visible");
        logger.info("The element with "
                + locator.toString().replace("By.", " ") + " is visible");
        return driver.findElement(locator).isDisplayed();
    }

    /**
     * This function is to check whether a webelement is enabled or not
     *
     * @param locator - By object of the webelement which is to be checked
     * @param locatorName
     * @return boolean - returns true if the specified webelement is enabled,
     * else it will return false
     */
    public boolean isElementEnabled(By locator, String locatorName) {

        Reporter.log(" " + locatorName + " is enabled");
        logger.info("The element with"
                + locator.toString().replace("By.", " ") + " is enabled");
        return driver.findElement(locator).isEnabled();
    }

    /**
     * This function is to check whether the Current Window Title is as expected
     *
     * @param expectedTitle - Title expected in the Current Window
     * @return boolean - returns true if the CurrentTitle matches the
     * expectedTitle, else it will return false
     */
    public boolean isTitleAsExpected(String expectedTitle) {
        Reporter.log("The current window title is " + getPageTitle()
                + " whereas the expected is " + expectedTitle);
        logger.info("The current window title is " + getPageTitle()
                + " whereas the expected is " + expectedTitle);
        return expectedTitle.equals(getPageTitle());
    }

    /**
     * This function is to check whether there is any alert present.
     *
     *
     * @return boolean - returns true if alert is present, else it will return
     * false
     */
    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            Reporter.log("User is able to switch to alert box successfuly");
            logger.info("An alert box is present");
            return true;
        } catch (Exception e) {
            logger.error("There is no alert present ", e.fillInStackTrace());
            return false;
        }
    }

    /**
     * This function is to get a cookie with a specific name
     *
     * @param cookieName - Name of the cookie which is to be returned
     * @return Cookie - Returns the cookie value for the name specified, or null
     * if no cookie found with the given name
     */
    public Cookie getCookie(String cookieName) {
        Reporter.log("User is able to obtain the cookie:" + cookieName + " successfuly");
        logger.info("The cookie:" + cookieName + " is obtained");
        return driver.manage().getCookieNamed(cookieName);
    }

    /**
     * This function is to delete a cookie from the browser's "cookie jar" The
     * domain of the cookie will be ignored.
     *
     * @param cookieName - name of the cookie which is to be deleted.
     */
    public void deleteCookieNamed(String cookieName) {
        if (!cookieName.equalsIgnoreCase(null)) {
            try {
                Reporter.log("User is able to delete the cookie:" + cookieName + " successfuly");
                logger.info("The cookie:" + cookieName + " is deleted");
                driver.manage().deleteCookieNamed(cookieName);
            } catch (InvalidCookieDomainException e) {
                logger.error("Unable to delete the cookie: ", e.fillInStackTrace());
                throw new InvalidCookieDomainException("The cookie with name "
                        + cookieName + " cannot be deleted");
            }
        } else {
            logger.info("Cookie Name is null; Unable to delete");
        }
    }

    /**
     * This function is to delete all the cookies for the current domain
     *
     */
    public void deleteAllCookie() {
        try {
            driver.manage().deleteAllCookies();
            Reporter.log("User is able to delete all cookies successfully");
            logger.info("All cookies are deleted");
        } catch (InvalidCookieDomainException e) {
            logger.error("Unable to delete cookies: ", e.fillInStackTrace());
            throw new InvalidCookieDomainException("Unable to delete cookies");
        }
    }

    /**
     * This function is to perform a right click on a particular webelement
     *
     * @param locator - By object of the Webelement on which right click
     * operation has to be performed
     * @param locatorName - Name of the locator to declared.
     */
    public void rightClick(By locator, String locatorName) {
        try {
            WebElement webElement = driver.findElement(locator);
            Actions action = new Actions(driver);
            action.contextClick(webElement).build().perform();
            Reporter.log("User is able to perform Right Click on " + locatorName + " successfuly");
            logger.info("The element with "
                    + locator.toString().replace("By.", " ") + " is right clicked");
        } catch (NoSuchElementException e) {
            logger.error("Unable to scroll: ", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        } catch (UnsupportedCommandException e) {
            logger.error("Unable to scroll: ", e.fillInStackTrace());
            throw new UnsupportedCommandException("Command used by the webdriver is unsupported");
        }
    }

    /**
     * This function is to move the Webelement to an offset from the top-left
     * corner of the Webelement
     *
     * @param locator - By object to locate the Webelement which is to be moved
     * @param locatorName - Name of the locator to declared.
     * @param xOffset - xOffset by which the Webelement will be moved from the
     * current position towards x-axis
     * @param yOffset - yOffset by which the Webelement will be moved from the
     * current position towards y-axis
     */
    public void moveToElement(By locator, String locatorName, int xOffset, int yOffset) {
        try {
            Actions builder = new Actions(driver);
            builder.moveToElement(driver.findElement(locator), xOffset, yOffset);
            Reporter.log("User is able to perform Move to element on " + locatorName + " successfuly");
            logger.info("Element with " + locator.toString().replace("By.", " ")
                    + " " + "is moved " + xOffset + " along x-axis and"
                    + yOffset + " along y-axis");
        } catch (MoveTargetOutOfBoundsException e) {
            logger.error("Unable to move the element from current position", e.fillInStackTrace());
            throw new MoveTargetOutOfBoundsException("Target provided x:" + xOffset
                    + "and y:" + yOffset + "is invalid");
        } catch (NoSuchElementException e) {
            logger.error("Unable to move the element from current position", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locator.toString().replace("By.", " ")
                    + " not found");
        }

    }

    /**
     * This function is to handle the alert; Will Click on OK button First get a
     * handle to the open alert, prompt or confirmation and then accept the
     * alert.
     *
     */
    public void acceptAlert() {
        try {
            Alert alertBox = driver.switchTo().alert();
            alertBox.accept();
            Reporter.log("User is able to able to accept the alert box successfully");
            logger.info("The alert is accepted");
        } catch (NoAlertPresentException e) {
            logger.error("Unable to access the alert: ", e.fillInStackTrace());
            throw new NoAlertPresentException("Alert not present");
        }
    }

    /**
     * This function is to handle the alert; Will Click on Cancel button First
     * get a handle to the open alert, prompt or confirmation and then dismiss
     * the alert.
     *
     */
    public void dismissAlert() {
        try {
            Alert alertBox = driver.switchTo().alert();
            alertBox.dismiss();
            Reporter.log("User is able to able to dismiss the alert box successfully ");
            logger.info("The alert is dismissed");
        } catch (NoAlertPresentException e) {
            logger.error("Unable to access the alert: ", e.fillInStackTrace());
            throw new NoAlertPresentException("Alert not present");
        }
    }

    /**
     * This function is to get the text which is present on the Alert.
     *
     * @return String - returns the text message which is present on the Alert.
     */
    public String getAlertMessage() {
        String alertMessage = null;
        try {
            Alert alertBox = driver.switchTo().alert();
            Reporter.log("User is able to retrieve the text " + alertBox.getText() + " within popup successfully");
            logger.info("The text " + alertBox.getText() + " within popup is returned");
            alertMessage = alertBox.getText();
        } catch (NoAlertPresentException e) {
            logger.error("Unable to access the alert:", e.fillInStackTrace());
            throw new NoAlertPresentException("Alert not present");
        }
        return alertMessage;
    }

    /**
     * This function closes the Current Browser Window
     *
     */
    public void closeCurrentWindow() {
        driver.close();
        logger.info("Driver window is closed");
    }

    /**
     * This function for select box in the form of table Added by Lufthansa -
     * Author: 206247
     */
    public void selectFromTable(By table, By td, String text, String locatorName) {
        try {
            delay(500);
            addExplicitWait(table, "visibility", 1000);//clickable,presence,visibility
            WebElement Table = driver.findElement(table);
            Table.click();
            WebElement TableTd = driver.findElement(td);
            List<WebElement> allOptions = TableTd.findElements(By.tagName("td"));
            for (WebElement we : allOptions) {
                if (we.getText().equals(text)) {
                    we.click();
                    Reporter.log("User is able to select the dropdown option with text: " + text
                            + " successfully");
                    return;
                }
            }
        } catch (NoSuchElementException e) {
            logger.error("selectFromTable: Unable to find the element: ", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locatorName + " not found");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BasePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This function is used handled page load Added by Lufthansa - Author:
     * 206247
     */
    public void waitForPageLoad() {

        Wait<WebDriver> wait = new WebDriverWait(driver, 1000);
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return String
                        .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                        .equals("complete");
            }
        });
    }

    /**
     * This function is used to assert the expected text in UI Added by
     * Lufthansa - Author: 206247
     */
    public void AssertText(String text) {
        try {
            delay(50);
            Assert.assertTrue(driver.getPageSource().contains(text));
            Reporter.log("" + text + " has been verified successfully in current page");

        } catch (NoSuchElementException e) {
            logger.info("" + text + " not displayed");
            java.util.logging.Logger.getLogger(BasePage.class.getName()).log(Level.SEVERE, "" + text + " not asserted", e);

        }
    }

    public void selectDate(By div, By by, String date) {
        try {
            // times out after 5 seconds
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(div));
            WebElement dateWidget = element.findElement(by);
            List<WebElement> rows = dateWidget.findElements(By.tagName("tr"));
            List<WebElement> columns = dateWidget.findElements(By.tagName("td"));

            for (WebElement cell : columns) {
                System.out.println(cell.getText());
                if (cell.getText().equals(date)) {
                    cell.click();
                    break;
                }
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void selectFromList(By list, String text) throws Exception {
        try {
            List<WebElement> allElements = driver.findElements(list);
            for (WebElement element : allElements) {
                System.out.println(element.getText());
                if (element.getText().equals(text)) {
                    element.click();
                }
            }

        } catch (NoSuchElementException e) {
        }
    }

    public List<WebElement> webElements(By locator, String locatorName) {
        List<WebElement> allElements;
        try {
            allElements = driver.findElements(locator);
        } catch (NoSuchElementException e) {
            logger.error("selectFromTable: Unable to find the element: ", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locatorName + " not found");
        }

        return allElements;
    }

    public String getAttribute(By locator, String attribute, String locatorName) {
        String attributeValue;
        try {
            attributeValue = driver.findElement(locator).getAttribute(attribute);
        } catch (NoSuchElementException e) {
            logger.error("selectFromTable: Unable to find the element: ", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locatorName + " not found");
        }

        return attributeValue;
    }

    public boolean isElementPresent(By locator, String locatorName) {

        boolean elementExist;
        try {
            elementExist = driver.findElements(locator).size() > 0;
        } catch (NoSuchElementException e) {
            logger.error("selectFromTable: Unable to find the element: ", e.fillInStackTrace());
            throw new NoSuchElementException("The element with"
                    + locatorName + " not found");
        }

        return elementExist;
    }

    public static void waitMinutes(int minutes) {
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        calendar.add(Calendar.MINUTE, minutes);
        Date aTime = calendar.getTime();
        String reportDate = df.format(aTime);
        while (!reportDate.equals(df.format(Calendar.getInstance().getTime()))) {
        }
    }

    public static void waitSeconds(int seconds)  {
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        calendar.add(Calendar.SECOND, seconds);
        Date aTime = calendar.getTime();
        String reportDate = df.format(aTime);
        while (!reportDate.equals(df.format(Calendar.getInstance().getTime()))) {
        }
    }
}
