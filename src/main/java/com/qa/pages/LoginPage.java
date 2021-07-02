package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest {

    @AndroidFindBy(accessibility ="test-Username" ) private MobileElement usernameTxtFld;
    @AndroidFindBy(accessibility = "test-Password") private MobileElement passwordTxtFld;
    @AndroidFindBy(accessibility = "test-LOGIN") private MobileElement loginBtn;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView") private MobileElement errTxt;

    /**
     *
     * @param username
     * @return
     * This method is used in the pageFactory as we are on same page after entering the user name and
     * Hence returning the same page for the method.
     */
    public LoginPage enterUserName(String username){
        sendKeysWhileVisible(usernameTxtFld, username);
        return this;
    }

    public LoginPage enterPassword(String password){
        sendKeysWhileVisible(passwordTxtFld, password);
        return this;
    }

    public ProductsPage clickLoginButton(){
        clickWhileVisible(loginBtn);
        return new ProductsPage();
    }

    public String getErrTxt(){
        return getAttributeWhileVisible(errTxt,"text");
    }
}
