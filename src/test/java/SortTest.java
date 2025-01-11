import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortTest extends BaseTest {
    private WebDriverWait localWait;
    private Actions localAction;
    private JavascriptExecutor localJs;

    @BeforeEach
    public void setUpHelpers() {
        localWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        localAction = new Actions(webDriver);
        localJs = (JavascriptExecutor) webDriver;
        webDriver.get(baseUrl);
    }

    @Test
    @Order(1)
    public void ByLowest() throws InterruptedException {
        WebElement search = webDriver.findElement(By.className("tvcmssearch-words"));
        search.sendKeys("tastatura");

        WebElement search1 = webDriver.findElement(By.className("tvheader-search-btn"));
        search1.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".select-title")));
        WebElement click1 = webDriver.findElement(By.cssSelector(".select-title"));
        click1.click();

        localJs.executeScript("window.scrollBy(0,400)");
        Thread.sleep(500);

        WebElement click2 = webDriver.findElement(By.linkText("Prema cijeni (najjeftiniji - najskuplji)"));
        click2.click();

        Thread.sleep(4000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".item:nth-child(1) .tvproduct-wrapper:nth-child(1) .tvproduct-defult-img")));
        WebElement cl = webDriver.findElement(By.cssSelector(".item:nth-child(1) .tvproduct-wrapper:nth-child(1) .tvproduct-defult-img"));
        cl.click();


        WebElement price1 = webDriver.findElement(By.cssSelector(".current-price > span"));
        String pricee1 = price1.getText();
        Double priceee1 = Double.parseDouble(pricee1.replace(" KM","").replace(",","."));

        webDriver.navigate().back();

        WebElement cl2 = webDriver.findElement(By.cssSelector(".item:nth-child(2) .tvproduct-wrapper:nth-child(1) .tvproduct-defult-img"));
        cl2.click();

        WebElement price2 = webDriver.findElement(By.cssSelector(".current-price > span"));
        String pricee2 =price2.getText();
        Double priceee2 = Double.parseDouble(pricee2.replace(" KM","").replace(",","."));

        assertTrue(priceee1<priceee2, "Fail");
    }

    @Test
    @Order(2)
    public void byHighestTest() throws InterruptedException {
        WebElement search = webDriver.findElement(By.className("tvcmssearch-words"));
        search.sendKeys("tastatura");

        WebElement search1 = webDriver.findElement(By.className("tvheader-search-btn"));
        search1.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".select-title")));
        WebElement click1 = webDriver.findElement(By.cssSelector(".select-title"));
        click1.click();

        localJs.executeScript("window.scrollBy(0,500)");
        Thread.sleep(500);

        WebElement click2 = webDriver.findElement(By.linkText("Prema cijeni (najskuplji - najjeftiniji )"));
        click2.click();

        Thread.sleep(4000);
        String className = "product-miniature";

        double[] price = new double[2];

        for (int i = 0; i < 2; i++){
            List<WebElement> elements = webDriver.findElements(By.className(className));
            WebElement cl = elements.get(i);
            cl.click();

            Thread.sleep(1000);
            WebElement price1 = webDriver.findElement(By.cssSelector(".current-price > span"));
            String price1Text = price1.getText();
            String normalizedPriceText = price1Text.replace(".", "").replace(",", ".");
            price[i] = Double.parseDouble(normalizedPriceText.replace(" KM","").trim());
            webDriver.navigate().back();

            Thread.sleep(4000);
        }

        assertTrue(price[0]>price[1], "Fail");
    }
    }


