import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS) ovo je bio error.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test404Page extends BaseTest {

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
    public void test404PageDisplay() {
        webDriver.get(baseUrl + "nonexistent-page");

        localWait.until(driver -> driver.findElement(By.tagName("h1")));  // Use localWait for waiting
        String pageTitle = webDriver.findElement(By.tagName("h1")).getText();
        assertTrue(pageTitle.contains("404") || pageTitle.contains("Not Found"), "Custom 404 message is not displayed!");

        List<WebElement> goHomeLinks = webDriver.findElements(By.linkText("Go Home"));
        assertTrue(goHomeLinks.size() > 0, "'Go Home' link is not displayed!");
    }

    @Test
    @Order(2)
    public void testGoHomeLinkFunctionality() {
        webDriver.get(baseUrl + "nonexistent-page");

        localWait.until(driver -> driver.findElement(By.linkText("Go Home")));  // Use localWait for waiting
        webDriver.findElement(By.linkText("Go Home")).click();
        String currentUrl = webDriver.getCurrentUrl();

        assertEquals(baseUrl, currentUrl, "Home page URL is incorrect!");
    }
}
