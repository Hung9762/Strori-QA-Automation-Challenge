package test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utils.BasePage;

import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TC_UHP1_No30daysGuaranteeTextIsVisible extends BasePage {

    @Test(description = "TC1-UHP1-No30daysGuaranteeTextIsVisible")
    public void unHappyPath() throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions a = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement countryInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='text' and @placeholder='Type to Select Countries']")));
        countryInput.sendKeys("Me");
        Reporter.log("----> Writing Me on input\n");
        /*---- Step No.02 ---- Select Mexico from the list of countries ----*/
        List<WebElement> countryList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li[@class='ui-menu-item']/div")));
        for (WebElement country : countryList) {
            if (country.getText().equals("Mexico")) {
                country.click();
                Reporter.log("----> Select Mexico value\n");
                break;
            }
        }

        /*---- Step No.03 ---- Select Option2 and Option3 from dropdown ----*/
        Select dropDownOption = new Select(driver.findElement(By.xpath("//select[@name='dropdown-class-example']")));
        dropDownOption.selectByVisibleText("Option2");
        Reporter.log("----> Select Option2\n");
        Thread.sleep(2500);
        dropDownOption.selectByVisibleText("Option3");
        Reporter.log("----> Select Option3\n");
        Thread.sleep(2500);

        /*---- Step No.04 Open ---- Search for text to be visible ---- */
        WebElement openWindow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Open Window')]")));
        openWindow.click();
        String homepage = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        Iterator<String> windowHandler = windows.iterator();

        while (windowHandler.hasNext()) {
            String childWindow = windowHandler.next();
            if (!homepage.equalsIgnoreCase(childWindow)) {
                driver.switchTo().window(childWindow);
                Thread.sleep(10000);
                Reporter.log("Search 30 days Money Back Guarantee text\n");
                WebElement guaranteeText = driver.findElement(By.xpath("//h3[contains(text(),'30 day Money Back Guarantee')]"));
                if ( guaranteeText.isSelected() == false){
                    Reporter.log("----> 30 days Money Back Guarantee text is not visible to the user\n");
                    Assert.fail("----> 30 days Money Back Guarantee text is not visible to the user");
                }
                driver.close();
                driver.switchTo().window(homepage);
            }
        }
    }
}
