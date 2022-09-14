package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.Reporter;



public class BasePage {

    public static WebDriver driver;

    @BeforeClass
    public void  tearUp() {
        String browserName = System.getProperty("browser", "chrome");
        System.out.println(browserName);
        //Web Driver Instance Creation
        Reporter.log("Starting Driver: "+browserName);
        if (browserName.equals("chrome")){
            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if (browserName.equals("firefox")){
            FirefoxOptions options = new FirefoxOptions();
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }else if (browserName.equals("edge")){
            EdgeOptions options = new EdgeOptions();
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");

    }

    @AfterClass
    public void tearDown(){
        Reporter.log("Closing Driver");
        driver.quit();
    }

    public static WebDriver getDriver() { return driver; }
}
