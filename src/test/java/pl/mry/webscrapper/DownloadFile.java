package pl.mry.webscrapper;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Sleeper;
import pl.mry.webscrapper.enums.Browser;

import java.awt.*;
import java.awt.event.InputEvent;
import java.time.Duration;

import static pl.mry.webscrapper.enums.Browser.CHROME;
import static pl.mry.webscrapper.enums.Browser.FIREFOX;

public class DownloadFile {
    private String BASE_URL = "https://www.putty.org/";
    private WebDriver driver;
    private Browser browser = FIREFOX;

    @Test
    public void downloadPutty() {
        setupDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();

        driver.findElement(By.partialLinkText("here")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.partialLinkText("putty-64bit-")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickDownload();
    }

    private void clickDownload() {
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofMillis(1000));
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        robot.mouseMove(858, 488);//coordinates of save button
        //focus dialogue window
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofMillis(1000));
        //click save button
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        //wait for file to download

            Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofMillis(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
