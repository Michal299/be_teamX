import org.junit.After;
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

}
