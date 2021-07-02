package com.qa.tests;


import org.json.JSONObject;
import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class LoginTests extends BaseTest {

    LoginPage loginPage;
    ProductsPage productsPage;
    InputStream datais;
    JSONObject loginUsers;

    @BeforeClass
    public void BeforeClass() throws IOException {
        try{
            String dataFileName = "data/LoginUsers.json";
            datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(datais);
            loginUsers = new JSONObject(tokener);
        }catch(Exception e){
            e.printStackTrace();
            throw e;

        }finally{
            if(datais != null){
                datais.close();
            }

        }

    }

    @AfterClass
    public void AfterClass(){

    }

    @BeforeMethod
    public void BeforeMethod(Method m){
        loginPage = new LoginPage();
        System.out.println("\n" + "****** Starting Method "+ m.getName() + "**********" + "\n");

    }

    @AfterMethod
    public void AfterMethod(){

    }

    @Test
    public void invalidUserName() {

        loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
        loginPage.clickLoginButton();

        String actualErrText = loginPage.getErrTxt();
        String expectedErrText = strings.get("err_invalid_username_or_password");
        System.out.println("Actual error text is " + actualErrText + "\n" + "Expected Error text is " + expectedErrText);

        Assert.assertEquals(actualErrText, expectedErrText);

    }

    @Test
    public void invalidPassword() {
        loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
        loginPage.clickLoginButton();

        String actualErrText = loginPage.getErrTxt();
        //String expectedErrText = "Username and password do not match any user in this service.";
        String expectedErrText = strings.get("err_invalid_username_or_password");
        System.out.println("Actual error text is " + actualErrText + "\n" + "Expected Error text is " + expectedErrText);

        Assert.assertEquals(actualErrText, expectedErrText);
    }

    @Test
    public void successfulLogin() {
        loginPage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
        productsPage = loginPage.clickLoginButton();

        String actualPrdTitle = productsPage.getTitle();
        //String expectedPrdTitle = "Sauce Labs Backpack";
        String expectedPrdTitle = strings.get("product_title");
        System.out.println("Actual product page first product title is " + actualPrdTitle + "\n" + "Expected product title is " + expectedPrdTitle);

        Assert.assertEquals(actualPrdTitle, expectedPrdTitle);

    }




}
