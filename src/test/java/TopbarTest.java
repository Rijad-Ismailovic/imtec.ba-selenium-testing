import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TopbarTest extends BaseTest{

    public static WebElement container;

    @BeforeEach
    public void getUrl(){
        webDriver.get(baseUrl);
        container = webDriver.findElement(By.className("tvheader-nav-offer-text"));
    }
    @Test
    public void testEmailLink(){
        container.findElement(By.partialLinkText("webprodaja@imtec.ba")).click();
        assertNotEquals(baseUrl, webDriver.getCurrentUrl(), "Link should redirect");
        webDriver.close();
    }

    @Test
    public void testFacebookRedirect(){
        container.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div[1]/div/div[1]/div/div[2]/a[1]")).click();
        Object[] windowHandles = webDriver.getWindowHandles().toArray();
        String originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().window((String) windowHandles[1]);
        assertEquals("https://bs-ba.facebook.com/imtec.ba", webDriver.getCurrentUrl(), "URLs don't match");
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }

    @Test
    public void testInstagramRedirect(){
        container.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div[1]/div/div[1]/div/div[2]/a[2]")).click();
        Object[] windowHandles = webDriver.getWindowHandles().toArray();
        String originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().window((String) windowHandles[1]);
        assertEquals("https://www.instagram.com/imtec_ba/", webDriver.getCurrentUrl(), "URLs don't match");
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }

    @Test
    public void testLinkedinRedirect(){
        container.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div[1]/div/div[1]/div/div[2]/a[3]")).click();
        Object[] windowHandles = webDriver.getWindowHandles().toArray();
        String originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().window((String) windowHandles[1]);
        assertEquals("https://www.linkedin.com/company/imtec-d-o-o-/", webDriver.getCurrentUrl(), "URLs don't match");
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }


    @Test
    public void testYoutubeRedirect(){
        container.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div[1]/div/div[1]/div/div[2]/a[4]")).click();
        Object[] windowHandles = webDriver.getWindowHandles().toArray();
        String originalWindow = webDriver.getWindowHandle();
        webDriver.switchTo().window((String) windowHandles[1]);
        assertEquals("https://www.youtube.com/channel/UCMitJ_3Do6JQLzpnGalMpAg", webDriver.getCurrentUrl(), "URLs dont match");
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }
}
