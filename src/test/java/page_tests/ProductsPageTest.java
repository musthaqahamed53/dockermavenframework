package page_tests;

import com.aventstack.extentreports.Status;
import org.testng.annotations.Test;
import page_objects.LoginPageObjects;
import page_objects.ProductsPageObjects;

public class ProductsPageTest extends BaseTest{

    LoginPageObjects loginPageObjects;
    ProductsPageObjects productsPageObjects;

    @Test
    public void testItemName(){
        loginPageObjects = new LoginPageObjects(driver);
        productsPageObjects = loginPageObjects.userLogin("performance_glitch_user","secret_sauce");
        System.out.println(productsPageObjects.getTitleofPage());
        System.out.println(productsPageObjects.getItemName());
        testLogger.get().log(Status.PASS,"Test Test Sheik");
    }

}
