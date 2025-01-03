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

        localWait.until(driver -> driver.findElement(By.xpath("//h4[contains(text(),'Izvinite zbog neprijatnosti.')]")));  // Use localWait for waiting
        String pageTitle = webDriver.findElement(By.xpath("//h4[contains(text(),'Izvinite zbog neprijatnosti.')]")).getText();
        assertTrue(pageTitle.contains("Izvinite zbog neprijatnosti."), "Stranica koju trazižite nije pronađena");

        WebElement goHomeIcon = webDriver.findElement(By.xpath("//img[@alt='Imtec web shop']"));
        assertTrue(goHomeIcon.isDisplayed(), "Go Home icon is not displayed!");
        goHomeIcon.click();
    }

    @Test
    @Order(2)
    public void testSearchFunctionality() {
        webDriver.get(baseUrl + "nonexistent-page");

        localWait.until(driver -> driver.findElement(By.cssSelector(".search-widget:nth-child(3) .tvcmssearch-words")));  // Use localWait for waiting

        WebElement searchBar = webDriver.findElement(By.cssSelector(".search-widget:nth-child(3) .tvcmssearch-words"));
        WebElement searchIcon =webDriver.findElement(By.cssSelector(".search-widget:nth-child(3) .tvsearch-header-display-full .material-icons"));

        searchBar.sendKeys("Tastature");
        searchIcon.click();

        String thisUrl = webDriver.getCurrentUrl();
        assertTrue(thisUrl.contains("search"), "Search doesn't work properly");

        WebElement goHomeIcon = webDriver.findElement(By.xpath("//img[@alt='Imtec web shop']"));
        assertTrue(goHomeIcon.isDisplayed(), "Go Home icon is not displayed!");
        goHomeIcon.click();

        String currentUrl = webDriver.getCurrentUrl();
        assertTrue(currentUrl.equals(baseUrl), "Go home button doesn't work properly");
    }
}
