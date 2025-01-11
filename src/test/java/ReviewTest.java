import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewTest extends BaseTest{
    @BeforeEach
    public void getUrl(){
        webDriver.get("https://imtec.ba/olovke-i-markeri/26654-hemijska-olovka-schneider-k-15-crvena-7200000001032.html");
    }

    @Test
    public void testOneMissingField() throws InterruptedException {
        js.executeScript("window.scrollTo(0, 1000)");
        Thread.sleep(1000);
        webDriver.findElement(By.linkText("Dojmovi")).click();
        webDriver.findElement(By.linkText("Napišite dojam")).click();
        webDriver.findElement(By.id("comment_title")).sendKeys("Hemijskak Review");
        webDriver.findElement(By.id("commentCustomerName")).sendKeys("SVVT Test");

        webDriver.findElement(By.id("submitNewMessage")).click();
        assertNotEquals("https://imtec.ba/olovke-i-markeri/26654-hemijska-olovka-schneider-k-15-crvena-7200000001032.html", webDriver.getCurrentUrl(), "Did not stay on same page");
    }

    @Test
    public void testAllMissingFields() throws InterruptedException {
        js.executeScript("window.scrollTo(0, 1000)");
        Thread.sleep(1000);
        webDriver.findElement(By.linkText("Dojmovi")).click();
        webDriver.findElement(By.linkText("Napišite dojam")).click();

        webDriver.findElement(By.id("submitNewMessage")).click();
        assertNotEquals("https://imtec.ba/olovke-i-markeri/26654-hemijska-olovka-schneider-k-15-crvena-7200000001032.html", webDriver.getCurrentUrl(), "Did not stay on same page");
    }

    @Test
    public void testOtkaziButton() throws InterruptedException {
        js.executeScript("window.scrollTo(0, 1000)");
        Thread.sleep(1000);
        webDriver.findElement(By.linkText("Dojmovi")).click();
        webDriver.findElement(By.linkText("Napišite dojam")).click();
        webDriver.findElement(By.linkText("Otkaži")).click();
        assertEquals("https://imtec.ba/olovke-i-markeri/26654-hemijska-olovka-schneider-k-15-crvena-7200000001032.html#", webDriver.getCurrentUrl(), "Did not stay on same page");
    }

    @Test
    public void testSendReview() throws InterruptedException {
        js.executeScript("window.scrollTo(0, 1000)");
        Thread.sleep(1000);
        webDriver.findElement(By.linkText("Dojmovi")).click();
        webDriver.findElement(By.linkText("Napišite dojam")).click();
        webDriver.findElement(By.id("comment_title")).sendKeys("Hemijskak Review");
        webDriver.findElement(By.id("review_content")).sendKeys("This hemijska is crazy good!! No matter which way I turn it it always writes correctly!! 10/10");
        webDriver.findElement(By.id("commentCustomerName")).sendKeys("SVVT Test");

        webDriver.findElement(By.id("submitNewMessage")).click();
        assertNotEquals("https://imtec.ba/olovke-i-markeri/26654-hemijska-olovka-schneider-k-15-crvena-7200000001032.html?criterion%5B1%5D=5&title=asd&content=asd&customer_name=sd&id_product=26654&submitMessage=#", webDriver.getCurrentUrl(), "Did not stay on same page");
    }


}
