package page_tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import page_objects.LoginPageObjects;
import page_objects.ProductsPageObjects;

public class LoginPageTests extends BaseTest{
    LoginPageObjects loginPageObjects;
    ProductsPageObjects productsPageObjects;
    private static final Logger LOGGER = LogManager.getLogger(LoginPageTests.class);

    @Test
    public void userLoginTest(){
        loginPageObjects = new LoginPageObjects(driver);
        productsPageObjects = loginPageObjects.userLogin("standard_user","secret_sauce");
        LOGGER.info("Username is: "+"standard_user"+" Password is "+"secret_sauce");
        System.out.println(productsPageObjects.getTitleofPage());

    }
}
