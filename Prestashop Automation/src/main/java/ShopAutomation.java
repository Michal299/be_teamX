import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class ShopAutomation {
    WebDriver driver;
    Random rand;
    JavascriptExecutor js;
    WebDriverWait wait;

    @Before
    public void setUp() {
        // Telling the system where to find Chrome Driver
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions()
                .addArguments("--ignore-ssl-errors=yes")
                .addArguments("--ignore-certificate-errors");

        // Instantiate the driver for Chrome
        driver = new ChromeDriver(options);

        rand = new Random();

        js = (JavascriptExecutor) driver;

        wait = new WebDriverWait(driver,25);

        // Telling the system where to find Gecko Driver
        //System.setProperty("webdriver.gecko.driver","resources/geckodriver.exe"); // Setting system properties of FirefoxDriver

        // Instantiate the driver for Firefox
        //driver = new FirefoxDriver(); //Creating an object of FirefoxDriver
    }

    @After
    public void tearDown() {
        // Quit the driver
        driver.quit();
    }

    @Test
    public void buyProducts() throws InterruptedException {
        // Navigate to the URL
        driver.get("http://localhost:8000");

        //Maximize current window
        driver.manage().window().maximize();

        int date;
        int quantity;
        WebElement element;

        for(int i = 1 ; i < 6; i++) {
            // Go to the list of cities
            driver.findElement(By.xpath("/html/body/main/header/div[2]/div/div[1]/div[2]/div[1]/ul/li[1]/a")).click();

            // Choose the first category - "Białystok Helios Biała"
            driver.findElement(By.xpath("/html/body/main/section/div/div[2]/section/div[2]/ul/li[3]/h5/a")).click();

            // Choose the first movie to buy
            driver.findElement(By.xpath("/html/body/main/section/div/div[2]/section/section/div[3]/div/div[1]/div[" + i + "]/article/div/a/img")).click();

            // Choose the date of the movie
            date = rand.nextInt(4) + 1;
            driver.findElement(By.id("group_1")).click();
            driver.findElement(By.xpath("/html/body/main/section/div/div/section/div[1]/div[2]/div[2]/div[2]/form/div[1]/div/select/option[" + date + "]")).click();

            // Choose the quantity
            quantity = rand.nextInt(8) + 1;
            for(int j = 0 ; j < quantity; j++)
                driver.findElement(By.xpath("/html/body/main/section/div/div/section/div[1]/div[2]/div[2]/div[2]/form/div[2]/div/div[1]/div/span[3]/button[1]/i")).click();

            // Add the item to the cart
            driver.findElement(By.xpath("/html/body/main/section/div/div/section/div[1]/div[2]/div[2]/div[2]/form/div[2]/div/div[2]/button")).click();

            // Continue shopping
            // Wait for the button to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div/div[2]/div/div[2]/div/div/button")));
            driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/div/div[2]/div/div/button")).click();
        }

        for(int k = 2 ; k < 7; k++) {
            // Go to the list of cities
            driver.findElement(By.xpath("/html/body/main/header/div[2]/div/div[1]/div[2]/div[1]/ul/li[1]/a")).click();

            // Choose the second category - "Bydgoszcz Helios"
            driver.findElement(By.xpath("/html/body/main/section/div/div[2]/section/div[2]/ul/li[6]/h5/a")).click();

            // Choose the first movie to buy
            driver.findElement(By.xpath("/html/body/main/section/div/div[2]/section/section/div[3]/div/div[1]/div[" + k + "]/article/div/a/img")).click();

            // Choose the date of the movie
            date = rand.nextInt(4) + 1;
            driver.findElement(By.id("group_1")).click();
            driver.findElement(By.xpath("/html/body/main/section/div/div/section/div[1]/div[2]/div[2]/div[2]/form/div[1]/div/select/option[" + date + "]")).click();

            // Choose the quantity
            quantity = rand.nextInt(8) + 1;
            for(int j = 0 ; j < quantity; j++)
                driver.findElement(By.xpath("/html/body/main/section/div/div/section/div[1]/div[2]/div[2]/div[2]/form/div[2]/div/div[1]/div/span[3]/button[1]/i")).click();

            // Add the item to the cart
            driver.findElement(By.xpath("/html/body/main/section/div/div/section/div[1]/div[2]/div[2]/div[2]/form/div[2]/div/div[2]/button")).click();

            // Move to processing the order
            if(k == 6) {
                // Wait for the button to appear
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div/div[2]/div/div[2]/div/div/button")));
                driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/div/div[2]/div/div/a")).click();
            }
            // Continue shopping
            else {
                // Wait for the button to appear
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[5]/div/div/div[2]/div/div[2]/div/div/button")));
                driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/div/div[2]/div/div/button")).click();
            }
        }

        // Remove one item from the order
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/div/div[1]/div/div[2]/ul/li[2]/div/div[3]/div/div[3]/div/a/i")).click();

        // Move to finishing processing the order
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/div/div[2]/div/div[2]/div/a")).click();

        // Click to select the gender of the user
        driver.findElement(By.id("field-id_gender-2")).click();

        // Fill in the first name
        WebElement first_name = driver.findElement(By.id("field-firstname"));
        first_name.clear();
        first_name.sendKeys("User");

        // Fill in the last name
        WebElement last_name = driver.findElement(By.id("field-lastname"));
        last_name.clear();
        last_name.sendKeys("User");

        // Fill in the email
        WebElement email = driver.findElement(By.id("field-email"));
        email.clear();
        email.sendKeys("user@mail.com");

        // Fill in the password
        WebElement password = driver.findElement(By.id("field-password"));
        password.clear();
        password.sendKeys("password123");

        // Fill in the birthday
        WebElement birthday = driver.findElement(By.id("field-birthday"));
        birthday.clear();
        birthday.sendKeys("2002-07-19");

        // Select the first permission
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/div/div[1]/section[1]/div/div/div[1]/form/div/div[8]/div[1]/span/label/input")).click();

        // Select the second permission
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/div/div[1]/section[1]/div/div/div[1]/form/div/div[9]/div[1]/span/label/input")).click();

        // Next
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/div/div[1]/section[1]/div/div/div[1]/form/footer/button")).click();

        // Fill in the address
        WebElement address1 = driver.findElement(By.id("field-address1"));
        address1.clear();
        address1.sendKeys("Good Street");

        // Fill in the post code
        WebElement postcode = driver.findElement(By.id("field-postcode"));
        postcode.clear();
        postcode.sendKeys("80-144");

        // Fill in the city
        WebElement city = driver.findElement(By.id("field-city"));
        city.clear();
        city.sendKeys("Warszawa");

        // Fill in the phone
        WebElement phone = driver.findElement(By.id("field-phone"));
        phone.clear();
        phone.sendKeys("500224517");

        // Next
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/div/div[1]/section[2]/div/div/form/div/div/footer/button")).click();

        // Next
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/div/div[1]/section[3]/div/div[2]/form/button")).click();

        // Choose to pay during delivery
        driver.findElement(By.id("payment-option-2")).click();

        // Agree to terms and conditions
        driver.findElement(By.id("conditions_to_approve[terms-and-conditions]")).click();

        // Process the order
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/div/div[1]/section[4]/div/div[3]/div[1]/button")).click();

        // Go to user account
        driver.findElement(By.xpath("/html/body/main/header/nav/div/div/div[1]/div[2]/div[1]/div/a[2]/span")).click();

        // Go to history and orders details
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/section/div/div/a[3]/span/i")).click();

        // Assert that the order was placed
        String actualString = driver.findElement(By.xpath("/html/body/main/section/div/div/section/section/table/tbody/tr/td[4]/span")).getText();
        assertTrue(actualString.contains("Przygotowanie w toku"));

        // Check the details of the order
        driver.findElement(By.xpath("/html/body/main/section/div/div/section/section/table/tbody/tr/td[6]/a[1]")).click();

    }

}
