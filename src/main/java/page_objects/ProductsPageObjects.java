package page_objects;

import generic_keywords.WebElementsInteractions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPageObjects extends WebElementsInteractions {


    private final By getTitleOfProductsPage = By.xpath("//span[contains(text(),'Products')]");
    private final By getTextofFirstItem = By.xpath("//a[@id='item_4_title_link']");

    public ProductsPageObjects(WebDriver driver) {
        super(driver);
    }

    public String getTitleofPage() {
        return retrieveTextData(getTitleOfProductsPage);
    }

    public String getItemName(){
        return retrieveTextData(getTextofFirstItem);
    }
}