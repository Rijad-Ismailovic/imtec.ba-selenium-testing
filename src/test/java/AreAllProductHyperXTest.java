import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS) ovo je bio error.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AreAllProductHyperXTest extends BaseTest {

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
    public void testProducts() {

        WebElement HyperxProducts = webDriver.findElement(By.cssSelector(".col-xl-12:nth-child(1) .tvimage-lazy"));
        HyperxProducts.click();


        scrollMultipleTimesAndCloseCookie(2);


        String[] xpaths = {
                "//div[@id='js-product-list']/div/div/article/div/div/div/a/img",
                "//img[@alt='HyperX Cloud III slušalice bežične crno-crvene']",
                "//*[@id=\"js-product-list\"]/div/div/article[3]",
                "//img[@alt='HyperX podloga za miš Pulsefire Mat XL']",
                "//img[@alt='HyperX QuadCast S USB Mikrofon']"
        };

        for (String xpath : xpaths) {

            scrollUntilVisibleAndCloseCookie(By.xpath(xpath));
            WebElement productImage = webDriver.findElement(By.xpath(xpath));
            productImage.click();


            localWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".h1")));
            WebElement productTitle = webDriver.findElement(By.cssSelector(".h1"));
            String titleText = productTitle.getText();


            assertTrue(titleText.contains("HYPERX"), "Product title does not contain 'HyperX': " + titleText);


            webDriver.navigate().back();

            scrollMultipleTimesAndCloseCookie(2);
        }
    }


    private void scrollMultipleTimesAndCloseCookie(int times) {
        for (int i = 0; i < times; i++) {
            localJs.executeScript("window.scrollBy(0, 100);");
            closeCookieConsent();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void scrollUntilVisibleAndCloseCookie(By by) {
        while (true) {
            try {
                WebElement element = webDriver.findElement(by);
                if (element.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {

            }
            localJs.executeScript("window.scrollBy(0, 100);");
            closeCookieConsent();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long scrollHeight = (long) localJs.executeScript("return document.body.scrollHeight;");
            long clientHeight = (long) localJs.executeScript("return window.innerHeight;");
            long scrollTop = (long) localJs.executeScript("return window.pageYOffset;");
            if (scrollTop + clientHeight >= scrollHeight) {
                break;
            }
        }
    }

    private void closeCookieConsent() {
        try {
            WebElement cookieConsentCloseButton = webDriver.findElement(By.linkText("Prihvatam"));
            if (cookieConsentCloseButton.isDisplayed()) {
                cookieConsentCloseButton.click();
            }
        } catch (Exception e) {

        }
    }


}