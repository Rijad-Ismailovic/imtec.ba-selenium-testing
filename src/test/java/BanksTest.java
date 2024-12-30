import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BanksTest extends BaseTest{

    public static WebElement container;

    @BeforeEach
    public void getUrl() throws InterruptedException {
        webDriver.get(baseUrl);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(1000);
        container = webDriver.findElement(By.className("placanje"));
    }

    @Test
    public void testNLBBanka(){
        container.findElement(By.xpath("//*[@id=\"footer\"]/div[2]/div[1]/div[1]/div/div[3]/a[1]")).click();
        Object[] windowHandles = webDriver.getWindowHandles().toArray();
        String originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().window((String) windowHandles[1]);
        assertEquals("https://www.nlb-fbih.ba/", webDriver.getCurrentUrl(), "URLs don't match");
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }

    @Test
    public void testMonriPayments(){
        container.findElement(By.xpath("//*[@id=\"footer\"]/div[2]/div[1]/div[1]/div/div[3]/a[2]")).click();
        Object[] windowHandles = webDriver.getWindowHandles().toArray();
        String originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().window((String) windowHandles[1]);
        assertEquals("https://monri.com/", webDriver.getCurrentUrl(), "URLs don't match");
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }

    @Test
    public void testMastercard(){
        container.findElement(By.xpath("//*[@id=\"footer\"]/div[2]/div[1]/div[1]/div/div[3]/a[3]")).click();
        Object[] windowHandles = webDriver.getWindowHandles().toArray();
        String originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().window((String) windowHandles[1]);
        assertEquals("https://www.mastercard.ba/bs-ba.html", webDriver.getCurrentUrl(), "URLs don't match");
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }

    @Test
    public void testMastercard2(){
        container.findElement(By.xpath("//*[@id=\"footer\"]/div[2]/div[1]/div[1]/div/div[3]/a[4]")).click();
        Object[] windowHandles = webDriver.getWindowHandles().toArray();
        String originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().window((String) windowHandles[1]);
        assertEquals("https://www.mastercard.hr/hr-hr/privatni/pronadite-karticu.html", webDriver.getCurrentUrl(), "URLs don't match");
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }

    @Test
    public void testVisa(){
        container.findElement(By.xpath("//*[@id=\"footer\"]/div[2]/div[1]/div[1]/div/div[3]/a[5]")).click();
        Object[] windowHandles = webDriver.getWindowHandles().toArray();
        String originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().window((String) windowHandles[1]);
        assertEquals("https://www.visa.co.uk/about-visa/visa-in-europe.html", webDriver.getCurrentUrl(), "URLs don't match");
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }

    @Test
    public void testMastercarGlobal(){
        container.findElement(By.xpath("//*[@id=\"footer\"]/div[2]/div[1]/div[1]/div/div[3]/a[6]")).click();
        Object[] windowHandles = webDriver.getWindowHandles().toArray();
        String originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().window((String) windowHandles[1]);
        assertEquals("https://www.mastercard.us/en-us.html", webDriver.getCurrentUrl(), "URLs don't match");
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }

    @Test
    public void testVisaGlobal(){
        container.findElement(By.xpath("//*[@id=\"footer\"]/div[2]/div[1]/div[1]/div/div[3]/a[7]")).click();
        Object[] windowHandles = webDriver.getWindowHandles().toArray();
        String originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().window((String) windowHandles[1]);
        assertEquals("https://www.visa.co.uk/products/visa-secure.html", webDriver.getCurrentUrl(), "URLs don't match");
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }

    @Test
    public void testMonri2(){
        container.findElement(By.xpath("//*[@id=\"footer\"]/div[2]/div[1]/div[1]/div/div[3]/a[8]")).click();
        Object[] windowHandles = webDriver.getWindowHandles().toArray();
        String originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().window((String) windowHandles[1]);
        assertEquals("https://monri.com/", webDriver.getCurrentUrl(), "URLs don't match");
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }


}
