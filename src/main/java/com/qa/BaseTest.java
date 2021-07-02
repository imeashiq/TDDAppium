package com.qa;

import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected static AppiumDriver driver;
    protected static Properties props;
    protected static HashMap<String, String> strings = new HashMap<String, String>();
    InputStream inputStream;
    InputStream stringsis;
    TestUtils utils;

    //To initialize the elements from pageFactory we need to create a constructor and pass the AppiumFieldDecorator to load the elements
    public BaseTest(){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    @Parameters({"platformName", "platformVersion", "deviceName"})
    @BeforeTest
    public void beforeTest(String platformName, String platformVersion, String deviceName) throws IOException {

        try{
            props = new Properties();
            String propFileName = "config.properties";
            String xmlFileName = "strings/strings.xml";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);

            stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
            utils = new TestUtils();
            strings = utils.parseStringXML(stringsis);


            DesiredCapabilities dc = new DesiredCapabilities();
            dc.setCapability("platformName", platformName);
            dc.setCapability("platformVersion", platformVersion);
            dc.setCapability("deviceName", deviceName);
            dc.setCapability("automationName", props.getProperty("androidAutomationName"));
            dc.setCapability("appPackage", props.getProperty("androidAppPackage"));
            dc.setCapability("appActivity", props.getProperty("androidAppActivity"));
            //Get resources will get the complete path in the src/test/resources and append the relative path.
            //URL appurl = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
            String appurl = getClass().getResource(props.getProperty("androidAppLocation")).getFile();
            //dc.setCapability("app", appurl);
            //dc.setCapability("avd", "Pixel");
            //dc.setCapability("app", "/Users/devendrankrishnan/Downloads/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");


            URL url = new URL(props.getProperty("appiumURL"));

            driver = new AndroidDriver<WebElement>(dc);
            String sessionID = driver.getSessionId().toString();

            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            if(inputStream != null){
                inputStream.close();
            }
            if(stringsis != null){
                stringsis.close();
            }
        }

    }

    //Creating methods for explicit wait to
    public void waitForVisibility(MobileElement e){
        WebDriverWait wait = new WebDriverWait(driver, TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    //Common Method to perform click action
    public void clickWhileVisible(MobileElement e){
        waitForVisibility(e);
        e.click();
    }

    //Common method to perform sendKeys action
    public void sendKeysWhileVisible(MobileElement e, String txt){
        waitForVisibility(e);
        e.sendKeys(txt);
    }

    //Common method for get attribute action
    public String getAttributeWhileVisible(MobileElement e, String attribute){
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    @AfterTest
    public void afterTest(){
        driver.quit();

    }
}
