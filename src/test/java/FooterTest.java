import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FooterTest extends BaseTest { // ovaj zeza nekad radi cc-compliance clicka. pogusaj nekoliko puta ako ne uspije

    WebElement container;

    @BeforeAll
    public static void login(){
        webDriver.get("https://imtec.ba/prijava-lscfl?back=my-account");
        webDriver.findElement(By.name("email")).sendKeys(testemail);
        webDriver.findElement(By.name("password")).sendKeys(testpassword);
        webDriver.findElement(By.id("submit-login")).click();
        //webDriver.findElement(By.className("cc-compliance")).click();

    }

    @BeforeEach
    public void getUrl() throws InterruptedException {
        webDriver.get(baseUrl);
        Thread.sleep(1000);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(1000);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        container = webDriver.findElement(By.className("tvfooter-storelogo-imformation"));
    }

    @Test
    public void testInformacijeOWebShopu(){
        WebElement subContainer = container.findElement(By.xpath("//*[@id=\"footer\"]/div[2]/div[1]/div[2]/div[1]"));
        String address = subContainer.findElement(By.className("tvfooter-addresses")).getText();
        String posaljiteMail = subContainer.findElement(By.xpath("//*[@id=\"footer_sub_menu_store_info\"]/div[2]")).getText();
        String nazoviteNas = subContainer.findElement(By.className("tvfooter-store-link-content")).getText();

        assertEquals("Adresa:\nImtec web shop\nStari Ilijaš 201\n71380 Ilijaš\nBosnia and Herzegovina", address, "Address does not match");
        assertEquals("Pošaljite Email: webprodaja@imtec.ba", posaljiteMail, "Mails do not match");
        assertEquals("Nazovite nas: 0800 223 38", nazoviteNas, "Number does not match");
    }

    @Test
    public void testVasKorisnickiRacun() throws InterruptedException {
        WebElement subContainer = container.findElement(By.id("block_myaccount_infos"));
        List<WebElement> hrefs = subContainer.findElements(By.tagName("a"));

        for (int i = 0; i < hrefs.size()- 3; i++){
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);
            WebElement xsubContainer = webDriver.findElement(By.id("block_myaccount_infos"));
            List<WebElement> xhrefs = xsubContainer.findElements(By.tagName("a"));

            String hrefString = xhrefs.get(i).getAttribute("href");
            xhrefs.get(i).click();
            assertEquals(hrefString, webDriver.getCurrentUrl());
            System.out.println(hrefString);
            webDriver.navigate().back();
            webDriver.navigate().refresh();
            Thread.sleep(1000);
        }
    }

    @Test
    public void testKompanija() throws InterruptedException {
        WebElement subContainer = container.findElement(By.className("tvfooter-account-link"));
        List<WebElement> hrefs = subContainer.findElements(By.tagName("a"));

        for(WebElement href : hrefs){
            System.out.println(href.getAttribute("href"));
        }

        for (int i = 0; i < hrefs.size(); i++){
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);
            container = webDriver.findElement(By.className("tvfooter-storelogo-imformation"));
            WebElement xsubContainer = container.findElement(By.className("tvfooter-account-link"));
            List<WebElement> xhrefs = xsubContainer.findElements(By.tagName("a"));

            String hrefString = xhrefs.get(i).getAttribute("href");
            xhrefs.get(i).click();
            assertEquals(hrefString, webDriver.getCurrentUrl());
            System.out.println(hrefString);
            webDriver.navigate().back();
            webDriver.navigate().refresh();
            Thread.sleep(1000);
        }
    }
}
