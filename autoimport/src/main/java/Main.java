import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Main {

    public static void main(String[] args) {

        if (args.length >= 4) {
            final String adminUrl = args[0];
            final String categoriesFilePath = args[1];
            final String productsFilePath = args[2];
            final String combinationsFilePath = args[3];

            Main main = new Main(adminUrl, categoriesFilePath, productsFilePath, combinationsFilePath);
            main.run();
        }  else {
            System.out.println("Please pass base url");
        }
    }

    private final String baseUrl;
    private final String categoriesPath;
    private final String productsPath;
    private final String combinationsPath;

    private final WebDriver driver;
    private final String adminMail = "admin@mail.com";
    private final String adminPassword = "adminadmin";
    private final String attributeName = "Terminy";

    private final List<String> categoriesColumns = Arrays.asList("Nazwa", "Aktywny (0 lub 1)", "Kategoria nadrzędna");

    public Main(String baseUrl, String categoriesPath, String productsPath, String combinationsPath) {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println(baseUrl);

        this.baseUrl = baseUrl;
        this.categoriesPath = categoriesPath;
        this.productsPath = productsPath;
        this.combinationsPath = combinationsPath;
    }

    public void run() {
        login();

        if (!isAttrPresent(attributeName)) {
            addAttribute(attributeName);
        }

        addCategories(categoriesPath);
        addProducts(productsPath);
        addCombinations(combinationsPath);
    }

    private void addCombinations(String path) {

    }

    private void addProducts(String path) {

    }

    private void addCategories(String path) {

        // go to categories page
        WebElement categoriesButton = driver.findElement(By.id("subtab-AdminCategories"));
        final String link = categoriesButton.findElement(By.tagName("a")).getAttribute("href");
        driver.get(link);

        // click settings button
        driver.findElement(By.xpath("//*[@id=\"category-grid-actions-button\"]")).click();

        // click import button
        WebElement dropdownMenu = driver.findElement(By.cssSelector(".dropdown-menu.dropdown-menu-right.show"));
        final String importLink = dropdownMenu.findElement(By.id("category-grid-action-import")).getAttribute("href");
        driver.get(importLink);

        // select file
        driver.findElement(By.xpath("//*[@id=\"file\"]")).sendKeys(path);

        // select all options to true
        final WebElement truncateButton = driver.findElement(By.xpath("//*[@id=\"truncate_1\"]"));
        if (truncateButton.getAttribute("value").equals("1")) {
            truncateButton.click();
        }

        final WebElement regenerateButton = driver.findElement(By.xpath("//*[@id=\"regenerate_1\"]"));
        if (regenerateButton.getAttribute("value").equals("1")) {
            regenerateButton.click();
        }

        final WebElement forceIDsButton = driver.findElement(By.xpath("//*[@id=\"forceIDs_1\"]"));
        if (forceIDsButton.getAttribute("value").equals("1")) {
            forceIDsButton.click();
        }

        final WebElement sendEmailButton = driver.findElement(By.xpath("//*[@id=\"sendemail_0\"]"));
        if (sendEmailButton.getAttribute("value").equals("0")) {
            sendEmailButton.click();
        }

        // click next step button
        driver.findElement(By.xpath("//*[@id=\"main-div\"]/div[2]/div[2]/div/div[2]/div/div/div[1]/form/div/div[2]/div/button")).click();
        driver.switchTo().alert().accept();

        // select proper columns
        selectColumns(categoriesColumns);

        // click import button
        driver.findElement(By.xpath("//*[@id=\"import\"]")).click();

        // wait for successful import
        final int minutesLimit = 5;
        WebDriverWait wait = new WebDriverWait(driver, 60 * minutesLimit);
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div[6]/div/button[3]"))
        );
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div[6]/div/button[3]")).click();

    }

    private void selectColumns(List<String> columnNames) {
        List<WebElement> selects = driver.findElements(By.className("type_value"));

        for (int i = 0; i < selects.size(); i++) {
            final Select select = new Select(selects.get(i));
            final String selectValue = columnNames.get(i);

            List<WebElement> options = select.getOptions();
            WebElement option = options.stream().filter(opt -> opt.getText().equals(selectValue)).findFirst().get();
            select.selectByValue(option.getAttribute("value"));
        }
    }

    private void login() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.get(baseUrl);
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(adminMail);
        driver.findElement(By.xpath("//*[@id=\"passwd\"]")).sendKeys(adminPassword);
        driver.findElement(By.xpath("//*[@id=\"submit_login\"]")).click();

        WebElement element = wait.until(presenceOfElementLocated(By.xpath("//*[@id=\"content\"]/div[1]/div/div/h1\n")));
    }

    private void addAttribute(String attributeName) {

        // click "add attributes group" button
        driver.findElement(By.id("page-header-desc-attribute_group-new_attribute_group")).click();

        // set name of attribute
        driver.findElement(By.xpath("//*[@id=\"name_1\"]")).sendKeys(attributeName);

        // set public name of attribute
        driver.findElement(By.xpath("//*[@id=\"public_name_1\"]")).sendKeys(attributeName);

        // click save button
        driver.findElement(By.xpath("//*[@id=\"attribute_group_form_submit_btn\"]")).click();
    }

    private boolean isAttrPresent(String attributeName) {
        driver.findElement(By.xpath("//*[@id=\"subtab-AdminCatalog\"]/a\n")).click();

        WebElement attributesAndFeature = driver.findElement(By.id("subtab-AdminParentAttributesGroups"));
        final String link = attributesAndFeature.findElement(By.tagName("a")).getAttribute("href");
        driver.get(link);

        List<WebElement> attributes = driver.findElements(By.cssSelector(".pointer.column-name.left"));
        return attributes.stream()
                .anyMatch(a -> a.getText().contains(attributeName));
    }

}
