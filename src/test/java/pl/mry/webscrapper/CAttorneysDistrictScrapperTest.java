package pl.mry.webscrapper;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import pl.mry.webscrapper.enums.Browser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.mry.webscrapper.enums.Browser.CHROME;
import static pl.mry.webscrapper.enums.Browser.FIREFOX;

public class CAttorneysDistrictScrapperTest {

    private String BASE_URL = "https://rejestradwokatow.pl/adwokat";
    private WebDriver driver;
    private Browser browser = CHROME;

    private List<String> attorneysDetailsUrl = new ArrayList<>();

    private String FILE_URL = "C:\\Users\\MRy\\Desktop\\filename.txt";
    private String HEADER = "Name | License \n";

    @Test
    public void scrapeAttorneys() {
        setupDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();

        Select citySelect = new Select(driver.findElement(By.xpath("//*[@id=\"id_j\"]")));
        citySelect.selectByVisibleText("Siedlce");
        driver.findElement(By.xpath("/html/body/main/div/article/div/form/nav/input")).click();

        findLinksToAttorneysDetails();

        saveAttorneysDetailsToFile();
        driver.quit();
    }

    private void saveAttorneysDetailsToFile() {
        try {
            FileWriter myWriter = new FileWriter(FILE_URL);
            myWriter.write(HEADER);
            for (String attorneyDetailsUrl : attorneysDetailsUrl) {
                driver.get(attorneyDetailsUrl);
                StringBuilder sb = new StringBuilder();
                sb.append(driver.findElement(By.xpath("/html/body/main/div/section/h2")).getText());
                sb.append(" | ");
                sb.append(driver.findElement(By.xpath("/html/body/main/div/section/h3")).getText());
                sb.append("\n");

                myWriter.write(sb.toString());
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void findLinksToAttorneysDetails() {
        addLinksToAttorneysDetails();
        Integer actualPage = 1;
        do {
            actualPage++;
            clickPageButton(actualPage);
            addLinksToAttorneysDetails();
        } while (isNextPage(actualPage));
    }

    private void clickPageButton(Integer actualPage) {
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        for (WebElement link : allLinks) {
            if (link.getText().equals(actualPage.toString())) {
                link.click();
                return;
            }
        }
    }

    private boolean isNextPage(Integer actualPage) {
        Integer nextPage = actualPage + 1;
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        for (WebElement link : allLinks) {
            if (link.getText().equals(nextPage.toString())) {
                return true;
            }
        }
        return false;
    }

    private void addLinksToAttorneysDetails() {
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        for (WebElement link : allLinks) {
            if (link.getText().equals("")) {
                if (!link.getAttribute("href").equals("https://rejestradwokatow.pl/adwokat")) {
                    attorneysDetailsUrl.add(link.getAttribute("href"));
                    System.out.println(link.getAttribute("href"));
                }
            }
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
