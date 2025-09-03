package base;

import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.utils.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class BasePage {

    public static String getScreenshot(String imageName, WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File f = ts.getScreenshotAs(OutputType.FILE);
        String filePath = "./screenshot/" + imageName;
        try {
            FileUtils.copyFile(f, new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath;
    }

    public static String converting_Base64(String screenshotPath) {
        byte[] file;
        try {
            file = FileUtils.readFileToByteArray(new File(screenshotPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Base64.encodeBase64String(file);
    }
}
