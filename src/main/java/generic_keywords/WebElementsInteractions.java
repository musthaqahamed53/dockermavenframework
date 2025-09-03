package generic_keywords;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebElementsInteractions {
    protected WebDriver driver; //to use in current class and inherited class

    public WebElementsInteractions(WebDriver driver) {
        this.driver = driver;
    }

    public void clickElement(By locator){
        driver.findElement(locator).click();
    }

    public void sendText(By locator,String text){
        driver.findElement(locator).sendKeys(text);
    }

    public void goToApplication(String url){
        driver.get(url);
    }

    public String retrieveTextData(By locator){
        return driver.findElement(locator).getText();
    }
}
