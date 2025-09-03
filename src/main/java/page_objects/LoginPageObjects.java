package page_objects;

import generic_keywords.WebElementsInteractions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPageObjects extends WebElementsInteractions {

//    WebDriver driver;
    private final By userNameTextField = By.id("user-name");
    private final By passwordTextField = By.id("password");

    private final By loginBtn = By.id("login-button");

    public LoginPageObjects(WebDriver driver) {
        super(driver); // Fix: call superclass constructor to initialize driver
//        this.driver = driver;
    }

    public ProductsPageObjects userLogin(String username, String password){
        goToApplication("https://www.saucedemo.com");
        sendText(userNameTextField,username);
        sendText(passwordTextField,password);
        clickElement(loginBtn);
        return new ProductsPageObjects(driver);
    }
}
