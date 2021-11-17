import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShopAutomation {
    WebDriver driver;

    @Before
    public void setUp() {
        // Telling the system where to find Chrome Driver
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");

        // Instantiate the driver for Chrome
        driver = new ChromeDriver();

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
    public void install() throws InterruptedException {
        // Navigate to the URL
        driver.get("http://localhost:8000/install/");

        // Next button - choosing the language
        driver.findElement(By.id("btNext")).click();

        // Accept the license
        driver.findElement(By.id("set_license")).click();

        // Next button
        driver.findElement(By.id("btNext")).click();

        // Change the name of the shop
        WebElement shop_name = driver.findElement(By.id("infosShop"));
        shop_name.sendKeys("Helios");

        // Disable SSL
        WebElement rbutton = driver.findElement(By.cssSelector("input[value='0']"));
        rbutton.click();

        // Fill in the name
        WebElement name = driver.findElement(By.id("infosFirstname"));
        name.sendKeys("admin");

        // Fill in the surname
        WebElement surname = driver.findElement(By.id("infosName"));
        surname.sendKeys("admin");

        // Fill in the email
        WebElement email = driver.findElement(By.id("infosEmail"));
        email.sendKeys("admin@mail.com");

        // Fill in the password
        WebElement password = driver.findElement(By.id("infosPassword"));
        password.sendKeys("adminadmin");

        // Repeat the password
        WebElement repeat_password = driver.findElement(By.id("infosPasswordRepeat"));
        repeat_password.sendKeys("adminadmin");

        // Next button
        driver.findElement(By.id("btNext")).click();

        // Change the address of the database server
        WebElement db_server = driver.findElement(By.id("dbServer"));
        db_server.clear();
        db_server.sendKeys("mysql");

        // Fill in the password for database
        WebElement db_password = driver.findElement(By.id("dbPassword"));
        db_password.clear();
        db_password.sendKeys("root");

        // Next button
        driver.findElement(By.id("btNext")).click();

        // Wait until the shop is installed
        WebDriverWait wait = new WebDriverWait(driver,120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultInstall")));
    }

    @Test
    public void changesInSSL() throws InterruptedException {
        // Navigate to the URL
        // USUN FOLDER INSTALL
        // TUTAJ TRZEBA ZMIENIC LINK NA POPRAWNY
        driver.get("http://localhost:8000/admin822bklzeb/index.php?controller=AdminLogin&token=92df1e35d4a377384acdf82135094d85");

        //Maximize current window
        driver.manage().window().maximize();

        // Fill in the email
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("admin@mail.com");

        // Fill in the password
        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys("adminadmin");

        // Proceed button
        driver.findElement(By.cssSelector(".ladda-label")).click();

        // Wait until the shop is installed
        WebDriverWait wait = new WebDriverWait(driver,120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("details-button")));

        // TUTAJ CHROME MI WYWALA KOMUNIKAT O BEZPIECZENSTWIE
        // Advanced button
        driver.findElement(By.id("details-button")).click();

        // "Proceed to localhost (unsafe)" button
        driver.findElement(By.id("proceed-link")).click();

        // Welcome pop-up tutorial that needs to be skipped
        boolean staleElement = true;
        while(staleElement){
            try{
                driver.findElement(By.cssSelector("button.btn")).click();

                staleElement = false;
            } catch(StaleElementReferenceException e){

                staleElement = true;
            }
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // "Preferencje" button
        WebElement element = driver.findElement(By.cssSelector("i.material-icons.mi-settings"));
        js.executeScript("arguments[0].click();", element);

        // "Ruch" button
        WebElement ruch_element = driver.findElement(By.cssSelector("#subtab-AdminParentMeta > a"));
        js.executeScript("arguments[0].click();", ruch_element);

        // Change the SSL domain
        WebElement domena_SSL = driver.findElement(By.id("meta_settings_shop_urls_form_domain_ssl"));
        domena_SSL.clear();
        domena_SSL.sendKeys("localhost:8001");

        // Save button
        WebElement zapisz_element = driver.findElement(By.cssSelector("form.form:nth-child(2) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > button"));
        js.executeScript("arguments[0].click();", zapisz_element);

        // "Ogólny" in the left bottom section
        WebElement ogolny_element = driver.findElement(By.cssSelector("#subtab-AdminParentPreferences > a"));
        js.executeScript("arguments[0].click();", ogolny_element);

        // Button to check HTTPS handling
        driver.findElement(By.cssSelector(".d-block")).click();

        // "Tak, rozumiem ryzyko" button
        WebElement ryzyko_element = driver.findElement(By.cssSelector(".btn-outline-danger"));
        js.executeScript("arguments[0].click();", ryzyko_element);

        // Button to enable SSL
        driver.findElement(By.cssSelector("#form_enable_ssl_1")).click();

        // Save button
        driver.findElement(By.cssSelector("#form-preferences-save-button")).click();

        // Button to enable SSL everywhere
        driver.findElement(By.cssSelector("#form_enable_ssl_everywhere_1")).click();

        // Save button
        driver.findElement(By.cssSelector("#form-preferences-save-button")).click();
    }

    @Test
    public void buyProducts() throws InterruptedException {
        // Navigate to the URL
        driver.get("http://localhost:8001");

        //Maximize current window
        driver.manage().window().maximize();

        // Click "Repertuar"
        driver.findElement(By.xpath("//*[@id=\"category-7\"]/a")).click();

        // Click "Bełchatów"
        driver.findElement(By.xpath("//*[@id=\"left-column\"]/div[1]/ul/li[2]/ul/li[1]/a")).click();

        // Click the element to buy
        driver.findElement(By.xpath("//*[@id=\"js-product-list\"]/div[1]/div/article/div/div[1]/h2/a")).click();

        // Click to select more elements
        for(int i = 0 ; i < 3; i++)
            driver.findElement(By.xpath("//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div/div[1]/div/span[3]/button[1]")).click();

        // Click the button to add the element to cart
        driver.findElement(By.xpath("//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div/div[2]/button")).click();

        // Click the button to continue shopping
        driver.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/button")).click();

        // Click the button to continue shopping
        driver.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/button")).click();

        // Click "Repertuar"
        driver.findElement(By.xpath("//*[@id=\"category-7\"]/a")).click();

        // Click "Białystok"
        driver.findElement(By.xpath("//*[@id=\"left-column\"]/div[1]/ul/li[2]/ul/li[2]/a")).click();

        // Click the element to buy
        driver.findElement(By.xpath("//*[@id=\"js-product-list\"]/div[1]/div[1]/article/div/div[1]/h2/a")).click();

        // Click to select more elements
        for(int i = 0 ; i < 7; i++)
            driver.findElement(By.xpath("//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div/div[1]/div/span[3]/button[1]")).click();

        // Click the button to add the element to cart
        driver.findElement(By.xpath("//*[@id=\"add-to-cart-or-refresh\"]/div[2]/div/div[2]/button")).click();

        // Click to process the order
        driver.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/a")).click();

        // Click to remove the item from the order
        driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[1]/div/div[2]/ul/li[1]/div/div[3]/div/div[3]")).click();

        // Click to move to finish processing the order
        driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div/div[2]/div/a")).click();

        // Click to select the gender of the user
        driver.findElement(By.id("field-id_gender-1")).click();

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
        WebElement birthday = driver.findElement(By.id("birthday"));
        birthday.clear();
        birthday.sendKeys("2002-07-19");

        // Click to select the first permission
        driver.findElement(By.xpath("//*[@id=\"customer-form\"]/div/div[8]/div[1]/span/label")).click();

        // Click to select the second permission
        driver.findElement(By.xpath("//*[@id=\"customer-form\"]/div/div[9]/div[1]/span/label")).click();

        // Click to proceed
        driver.findElement(By.xpath("//*[@id=\"customer-form\"]/footer/button")).click();

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

        // Click to proceed
        driver.findElement(By.xpath("//*[@id=\"delivery-address\"]/div/footer/button")).click();

        // Click to select the delivery type
        driver.findElement(By.xpath("//*[@id=\"delivery_option_5\"]")).click();

        // Click to proceed
        driver.findElement(By.xpath("//*[@id=\"js-delivery\"]/button")).click();

        // Click to select the type of payment
        driver.findElement(By.xpath("//*[@id=\"payment-option-2\"]")).click();

        // Click to agree to terms and conditions
        driver.findElement(By.xpath("//*[@id=\"conditions_to_approve[terms-and-conditions]\"]")).click();

        // Click to place the order
        driver.findElement(By.xpath("//*[@id=\"payment-confirmation\"]/div[1]/button")).click();

        // Click to go to the user's account
        driver.findElement(By.xpath("//*[@id=\"_desktop_user_info\"]/div/a[2]/span")).click();

        // Click to go to the history of orders
        driver.findElement(By.xpath("//*[@id=\"history-link\"]/span")).click();

        // Assert that the order was placed
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"content\"]/table/tbody/tr/td[4]/span")).isDisplayed());

    }

}
