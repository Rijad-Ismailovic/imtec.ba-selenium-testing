import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ResponsiveDesignTest extends BaseTest {
    private WebDriverWait localWait;
    private Actions localAction;
    private JavascriptExecutor localJs;

    public String url ="https://imtec.ba/laptopi/64674-laptop-hp-15s-eq3013nm-65c54ea-.html";

    @BeforeEach
    public void setUpHelpers() {
        localWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        localAction = new Actions(webDriver);
        localJs = (JavascriptExecutor) webDriver;
        webDriver.get(baseUrl);
    }

    @Test
    @Order(1)
    public void testResponsiveDesign()  {

        webDriver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        System.out.println("Testing at Desktop size (1920x1080)...");
        checkResponsiveElement();


        webDriver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
        System.out.println("Testing at Tablet size (768x1024)...");
        checkResponsiveElement();


        webDriver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
        System.out.println("Testing at Mobile size (375x667)...");
        checkResponsiveElement();
    }
    private void checkResponsiveElement() {
        try {

            By responsiveElement = By.cssSelector(".col-xl-12:nth-child(1) .tvimage-lazy");
            boolean isElementVisible = webDriver.findElement(responsiveElement).isDisplayed();
            System.out.println("Responsive element visible: " + isElementVisible);
        } catch (Exception e) {
            System.out.println("Responsive element not found for this size.");
        }
    }
}
