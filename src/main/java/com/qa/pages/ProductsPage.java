package com.qa.pages;

import com.qa.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends BaseTest {

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]") private MobileElement productsTitle;

    /**
     *
     * @param username
     * @return
     * This method is used in the pageFactory as we are on same page after entering the user name and
     * Hence returning the same page for the method.
     */
    public String getTitle(){
        return getAttributeWhileVisible(productsTitle, "text");

    }


}
