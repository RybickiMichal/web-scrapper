package pl.mry.webscrapper;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pl.mry.webscrapper.enums.Browser;

import static pl.mry.webscrapper.enums.Browser.CHROME;
import static pl.mry.webscrapper.enums.Browser.FIREFOX;

public class BMessageSender {

    private String BASE_URL = "https://profil.wp.pl/login.html?zaloguj=poczta";
    private WebDriver driver;
    private Browser browser = CHROME;

    private String LOGIN = "test8493240";
    private String PASSWORD = "uaT53N6wyQ7xriC";

    private String SEND_TO = "krasnopolsky27@gmail.com";
    private String SUBJECT = "Huje muje dzikie weze";
    private String MESSAGE_BODY = "TAG";

    @Test
    public void sendMail() {
        setupDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//*[@id=\"login\"]")).sendKeys(LOGIN);
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//*[@id=\"btnSubmit\"]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("/html/body/div[2]/nh-app-view/div/div/div/div[1]/div/div/div/div/nh-top-action-bar/div[2]/div[1]/button[2]")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("/html/body/div[2]/nh-app-view/div/div/div/div[1]/div/div/div[1]/div/div[1]/div[2]/div[2]/div/div/input")).sendKeys(SEND_TO);
        driver.findElement(By.xpath("/html/body/div[2]/nh-app-view/div/div/div/div[1]/div/div/div[1]/div/div[2]/div[2]")).sendKeys(MESSAGE_BODY);
        driver.findElement(By.xpath("/html/body/div[2]/nh-app-view/div/div/div/div[1]/div/div/div[1]/div/div[1]/div[3]/div[2]/input")).sendKeys(SUBJECT);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("/html/body/div[2]/nh-app-view/div/div/div/div[1]/div/div/div[1]/div/div[2]/div[4]/div/div[1]/button/div")).click();


    }

    private void setupDriver() {
        if (browser.equals(FIREFOX)) {
            System.setProperty("webdriver.gecko.driver", "C:\\Users\\MRy\\Desktop\\Repository\\webDrivers\\geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (browser.equals(CHROME)) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\MRy\\Desktop\\Repository\\webDrivers\\chromedriver.exe");
            driver = new ChromeDriver();
        }
    }
}
