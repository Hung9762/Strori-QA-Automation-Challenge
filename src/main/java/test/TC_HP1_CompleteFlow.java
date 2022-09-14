package test;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utils.BasePage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TC_HP1_CompleteFlow extends BasePage {

    @Test(description = "TC1-HP1-CompleteFlow")
    public void happyPath() throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions a = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement countryInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='text' and @placeholder='Type to Select Countries']")));
        countryInput.sendKeys("Me");
        Reporter.log("----> Writing Me on input\n");
        /*---- Step No.02 ---- Select Mexico from the list of countries ----*/
        List<WebElement> countryList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li[@class='ui-menu-item']/div")));
        for (WebElement country: countryList){
            if (country.getText().equals("Mexico")){
                Reporter.log("----> Select Mexico value\n");
                country.click();
                break;
            }
        }

        /*---- Step No.03 ---- Select Option2 and Option3 from dropdown ----*/
        Select dropDownOption = new Select(driver.findElement(By.xpath("//select[@name='dropdown-class-example']")));;
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

        System.out.println(windowHandler.hasNext());
        while (windowHandler.hasNext()){
            String childWindow = windowHandler.next();
            if (!homepage.equalsIgnoreCase(childWindow)){
                driver.switchTo().window(childWindow);
                Thread.sleep(20000);
                WebElement popUp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'NO THANKS')]")));
                Reporter.log("----> Dismiss PopUp\n");
                popUp.click();
                WebElement guaranteeText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'30 day Money Back Guarantee')]")));
                Reporter.log("----> "+guaranteeText.getText()+"\n");
                driver.close();
                driver.switchTo().window(homepage);
            }
        }
        System.out.println("1 - "+homepage);


        /*---- Step No.05 ---- Open new tab and take screenshot ----*/
        Reporter.log("----> Taking photo of button with text ");
        WebElement openWindowButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Open Tab')]")));
        openWindowButton.click();
        Set<String> windows2 = driver.getWindowHandles();
        Iterator<String> windowHandler2 = windows2.iterator();
        System.out.println(windowHandler2.hasNext());
        while (windowHandler2.hasNext()){
            String childWindow2 = windowHandler2.next();
            if (!homepage.equalsIgnoreCase(childWindow2)){
                driver.switchTo().window(childWindow2);
                System.out.println("2 - " + childWindow2);
                WebElement viewAllCoursesButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'VIEW ALL COURSES')]")));
                Thread.sleep(5000);
                System.out.println(viewAllCoursesButton.getText());
                a.moveToElement(viewAllCoursesButton,0,0).build().perform();
                Thread.sleep(5000);
                Reporter.log("----> Taking photo of button with text ".concat(viewAllCoursesButton.getText()));
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File("./target/surefire-reports/CompleteFlow.png"));
                Reporter.log("<br><img src='CompleteFlow.png' height='400' width='600'");
                Thread.sleep(2500);
                driver.switchTo().window(homepage);
            }
        }
        System.out.println("3 - " + homepage);
        /*---- Step No.06 ---- Open Alert and Confirm and compare text  ----*/
        Reporter.log("----> Input Stori Board text to input\n");
        WebElement alertText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter Your Name']")));
        alertText.sendKeys("Stori Card");
        Reporter.log("----> Click on confirm Alert\n");
        WebElement alertButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='submit' and @value='Alert']")));
        alertButton.click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Reporter.log("----> Alert text: ".concat(alert.getText())+"\n");
        alert.accept();

        alertText.sendKeys("Stori Card");
        Reporter.log("----> Click on confirm button\n");
        WebElement confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='submit' and @value='Confirm']")));
        confirmButton.click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert confirm = driver.switchTo().alert();
        Reporter.log("----> Confirm text: ".concat(confirm.getText())+"\n");
        alert.accept();

        /*---- Step No.07 ---- Get All courses with price $25  ----*/
        //System.out.println("----> Courses that $25: ");
        Reporter.log("----> Names of courses that cost $25: ");
        List<WebElement> courseTable = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//table[@id='product' and @name='courses']/tbody/tr/td[3]")));
        int courseNumber = 1;
        for (WebElement course: courseTable){
            if (course.getText().equals("25")){
                WebElement courseName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='product' and @name='courses']/tbody/tr["+courseNumber+"]/td[2]")));
                Reporter.log(courseName.getText());
            }
            courseNumber++;
        }
        /*---- Step No.08 ---- Get and print engineers names  ----*/
        Reporter.log("----> Names of engineers:");
        List<WebElement> staffTable = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='tableFixHead']/table[@id='product' ]/tbody/tr/td[2]")));
        int staffNumber = 1;
        for (WebElement staff: staffTable){
            if (staff.getText().equals("Engineer")){
                WebElement staffName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tableFixHead']/table[@id='product' ]/tbody/tr["+staffNumber+"]/td[1]")));
                Reporter.log(staffName.getText());
            }
            staffNumber++;
        }

        /*---- Step No.09 ---- Highlight in blue text inside the iframe and print odd characters ----*/
        Reporter.log("----> Search iFrame text and print odd characters: ");
        WebElement testFrame = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@name='iframe-name']")));
        a.moveToElement(testFrame);
        a.perform();
        driver.switchTo().frame(testFrame);
        WebElement iframeText =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='list-column col-md-6 col-sm-6 col-xs-12'][2]/ul/li[2]")));
        a.moveToElement(iframeText);
        a.perform();
        String highlightText = iframeText.getText();
        int letterPosition=0;
        for (char letter : highlightText.toCharArray()){
            if (letterPosition % 2 == 1){
                if (Character.isWhitespace(letter) != true){
                    Reporter.log(""+letter);
                }
            }
            letterPosition++;
        }
        js.executeScript("document.getElementsByTagName('li')[79].style.backgroundColor='lightblue'");
        Reporter.log("----> Taking photo of highlight text");
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("./target/surefire-reports/CompleteFlow-highlight.png"));
        Reporter.log("<br><img src='CompleteFlow-highlight.png' height='400' width='600'");
        Thread.sleep(2500);
    }
}
