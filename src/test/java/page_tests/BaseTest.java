package page_tests;

import base.AppConstants;
import base.BasePage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import static utils.ExtentReportHelper.getReportObject;

public class BaseTest {
    protected String browser;

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    protected static ThreadLocal<ExtentTest> testLogger = new ThreadLocal<>();

    private static final ExtentReports reports = getReportObject();

    private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static void removeDriver() {
        driverThreadLocal.remove();
    }

    @Parameters({"browserName"})
    @BeforeMethod
    public void setupTest(@Optional String browserName, ITestResult iTestResult) {
        ChromeOptions co = new ChromeOptions();
        FirefoxOptions fo = new FirefoxOptions();
        if (browserName != null) {
            browser = browserName;
            System.out.println("browser from common 1 "+browser);
            System.out.println("browser from AppConstants 1"+AppConstants.browserName);
        } else {
            browser = AppConstants.browserName;
            System.out.println("browser from common 2"+browser);
            System.out.println("browser from AppConstants 2"+AppConstants.browserName);
        }

        if (browser.equalsIgnoreCase("chrome")) {
            if (AppConstants.platform.equalsIgnoreCase("local")) {
                WebDriverManager.chromedriver().setup();
                setDriver(new ChromeDriver());
//                co.addArguments("--remote-allow-origins=*"); //help us remove any remote origins error
//                setDriver(new ChromeDriver(co));
            }
            else if(AppConstants.platform.equalsIgnoreCase("remote")){

                co.setPlatformName("linux");
                co.setPageLoadStrategy(PageLoadStrategy.EAGER);
                try {
                    setDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), co));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(AppConstants.platform.equalsIgnoreCase("remote_git")){
                co.addArguments("--headless"); //for GitHub actions
                co.addArguments("--disable-gpu");
                co.addArguments("--no-sandbox" );
                WebDriverManager.chromedriver().setup();
                co.addArguments("--remote-allow-origins=*");
                setDriver(new ChromeDriver(co));
            }
            else{
                LOGGER.error("Platform Not Supported");
            }
        }
        if (browser.equalsIgnoreCase("firefox")) {
            if (AppConstants.platform.equalsIgnoreCase("local")) {
//                fo = new FirefoxOptions();
//                fo.addArguments("--remote-allow-origins=*"); //help us remove any remote origins error
                WebDriverManager.firefoxdriver().setup();
//                setDriver(new FirefoxDriver(fo));
                setDriver(new FirefoxDriver());
            }
            else if (AppConstants.platform.equalsIgnoreCase("remote")) {
                fo = new FirefoxOptions();
                fo.setPlatformName("linux");
                fo.setPageLoadStrategy(PageLoadStrategy.EAGER);
                try {
                    setDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), fo));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(AppConstants.platform.equalsIgnoreCase("remote_git")){
                fo.addArguments("--headless"); //for GitHub actions
                fo.addArguments("--disable-gpu");
                fo.addArguments("--no-sandbox");
                WebDriverManager.firefoxdriver().setup();
                setDriver(new FirefoxDriver(fo));
            }
            else{
                LOGGER.error("Platform Not Supported");
            }
        }
        else {
            LOGGER.info("Browser name is: "+browser);
        }

        ExtentTest test = reports.createTest(iTestResult.getMethod().getMethodName());
        testLogger.set(test);
        testLogger.get().log(Status.INFO,"Driver Start Time: "+ LocalDateTime.now());
    }

    @AfterMethod
    public void tearDownTest(ITestResult iTestResult) {

        if(iTestResult.isSuccess()){
            testLogger.get().log(Status.PASS,
                    MarkupHelper.createLabel(iTestResult.getMethod().getMethodName()
                            +" is successfull", ExtentColor.GREEN));
        }
        else{
            testLogger.get().log(Status.FAIL,"Test Failed due to: "+ iTestResult.getThrowable());
            String screenshot = BasePage.getScreenshot(iTestResult.getMethod().getMethodName()+".jpg",getDriver());
            testLogger.get().fail(MediaEntityBuilder.createScreenCaptureFromBase64String(BasePage.converting_Base64(screenshot)).build());
        }

        testLogger.get().log(Status.INFO,"Driver End Time: "+ LocalDateTime.now());


        if (getDriver() != null) { // Fix: avoid NullPointerException
            getDriver().quit();
            removeDriver();
        }
    }

    @AfterClass
    public void flushTestReport(){
        reports.flush();
    }
}
