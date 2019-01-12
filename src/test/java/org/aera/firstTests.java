package org.aera;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class firstTests {

    public WebDriver driver;


    @Parameters("browser")
    @BeforeClass
    public void beforeTest(String browser) {
        // If the browser is Firefox, then do this
        if(browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "../aerahotwire/library/geckodriver");
            driver = new FirefoxDriver();
            // If browser is Chrome, then do this
        }else if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "../aerahotwire/library/chromedriver");
            driver = new ChromeDriver();
        }
        driver.get("http://www.hotwire.com/");
        driver.manage().window().maximize();
    }


    @Test
    public void test() throws Exception{

        Assert.assertEquals(driver.getTitle(),"Cheap Hotels, Cars, Airfare | Last Minute Travel Deals | Hotwire");

        driver.findElement(By.xpath("//li[@class='hidden-xs']//a[@target='_self'][contains(text(),'Vacations')]")).click();

        driver.findElement(By.xpath("//button[contains(text(),'Flight')]")).click();

        driver.findElement(By.xpath("//button[contains(text(),'Hotel')]")).click();

        driver.findElement(By.xpath("//button[contains(text(),'Car')]")).click();

        driver.findElement(By.xpath("//input[@id='farefinder-package-origin-location-input']")).sendKeys("SFO");

        Thread.sleep(10);

        WebDriverWait wait = new WebDriverWait(driver,60);

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//input[@id='farefinder-package-origin-location-input']/../ul/li/a[contains(@title,'SFO')]")));

        driver.findElement(By.xpath("//input[@id='farefinder-package-origin-location-input']/../ul/li/a[contains(@title,'SFO')]")).click();

        driver.findElement(By.xpath("//input[@id='farefinder-package-destination-location-input']")).sendKeys("LAX");

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//input[@id='farefinder-package-destination-location-input']/../ul/li/a[contains(@title,'LAX')]")));

        driver.findElement(By.xpath("//input[@id='farefinder-package-destination-location-input']/../ul/li/a[contains(@title,'LAX')]")).click();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.now();

        System.out.println("Todays Date : " + dtf.format(localDate));

        System.out.println("Departure Date :" + dtf.format(localDate.plusDays(1)));

        System.out.println("Returning Date :" + dtf.format(localDate.plusWeeks(3)));

        WebElement departure = driver.findElement(By.xpath("//input[@id='farefinder-package-startdate-input']"));

        departure.click();

        departure.clear();

        departure.sendKeys(dtf.format(localDate.plusDays(1)));

        departure.sendKeys(Keys.TAB);

        //departure.sendKeys(Keys.ESCAPE);

        Select departTime = new Select(driver.findElement(By.xpath("//select[@id='farefinder-package-pickuptime-input']")));
        departTime.selectByVisibleText("Evening");

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//input[@id='farefinder-package-enddate-input']")));

        WebElement returnDatePicker = driver.findElement(By.xpath("//input[@id='farefinder-package-enddate-input']"));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].click();", returnDatePicker);

        //returnDatePicker.click();

        returnDatePicker.clear();

        returnDatePicker.sendKeys(dtf.format(localDate.plusWeeks(3)));

        departure.sendKeys(Keys.TAB);

        Select dropTime = new Select(driver.findElement(By.xpath("//select[@id='farefinder-package-dropofftime-input']")));
        dropTime.selectByVisibleText("Morning");

        WebElement findVacation = driver.findElement(By.xpath("//button[@id='farefinder-package-search-button']"));

        js.executeScript("arguments[0].click();",findVacation);
        //findVacation.click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[contains(@class,'sort-options nobullet')]//li[2]//button[1]/span[contains(text,Recommended)]")));

        List<WebElement> resultArticle = driver.findElements(By.xpath("//div[@id='resultsContainer']//article"));

        System.out.println("Recommended number of results/hotels : "+resultArticle.size());

        Assert.assertTrue(resultArticle.size()>0);


    }

    @AfterClass
    public void tearDown(){
        driver.close();
        //driver.quit();
    }


}
