import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KorpaTest extends BaseTest{

    public static final String testArticle = "https://imtec.ba/laptopi/64674-laptop-hp-15s-eq3013nm-65c54ea-.html";

    @BeforeEach
    public void ClearBrowserCache() throws InterruptedException {
        webDriver.manage().deleteAllCookies(); //delete all cookies
        Thread.sleep(7000); //wait 7 seconds to clear cookies.
    }

    @Order(1)
    @Test
    public void testAddToCart() throws InterruptedException {
        webDriver.get(testArticle);
        webDriver.findElement(By.className("cc-compliance")).click();


        String title = webDriver.findElement(By.className("h1")).getText().toLowerCase();
        String price = webDriver.findElement(By.className("current-price")).getText();
        price = price.replaceAll("[^0-9.,]", "");

        WebElement addToCartButton = webDriver.findElement(By.className("add-to-cart"));
        action.moveToElement(addToCartButton).perform();
        addToCartButton.click();

        Thread.sleep(2000);
        WebElement modal = webDriver.findElement(By.id("blockcart-modal"));
        String confirmationText = modal.findElement(By.className("modal-title")).getText().substring(1);
        String productName = modal.findElement(By.className("product-name")).getText().toLowerCase();
        //trebao bi naci i cijenu da se podudara ali je confusing. dodaje se pdv pa popust pa nesto nesto i nikako da skontam sta se desava

        assertEquals(confirmationText, "Proizvod je uspješno dodan u vašu korpu", "Confirmation text not matching");
        assertEquals(productName, title, "Product name is wrong");

    }

    @Order(2)
    @Test
    public void testNastaviSaKupovinomButton() throws InterruptedException {
        webDriver.get(testArticle);
        //webDriver.findElement(By.className("cc-compliance")).click();
        js.executeScript("window.scroll(0, 500)");
        Thread.sleep(1000);


        WebElement addToCartButton = webDriver.findElement(By.className("add-to-cart"));
        action.moveToElement(addToCartButton).perform();
        addToCartButton.click();

        Thread.sleep(2000);
        WebElement modal = webDriver.findElement(By.id("blockcart-modal"));
        modal.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/button")).click();
        Thread.sleep(1000);
        assertEquals(webDriver.getCurrentUrl(), testArticle, "URLs dont match");
    }

    @Order(3)
    @Test
    public void testZavrsiKupovinuButton() throws InterruptedException {
        webDriver.get(testArticle);
        //webDriver.findElement(By.className("cc-compliance")).click();
        js.executeScript("window.scroll(0, 500)");
        Thread.sleep(1000);



        WebElement addToCartButton = webDriver.findElement(By.className("add-to-cart"));
        action.moveToElement(addToCartButton).perform();
        addToCartButton.click();

        Thread.sleep(2000);
        WebElement modal = webDriver.findElement(By.id("blockcart-modal"));
        modal.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/a")).click();
        assertEquals(webDriver.getCurrentUrl(), "https://imtec.ba/korpa-lscfl?action=show", "URLs dont match");
    }

    @Order(4)
    @Test
    public void testEmptyCart() throws InterruptedException { //samo da kliknes na korpu i da izadje onaj textbox
        webDriver.get(baseUrl);
        webDriver.findElement(By.className("tv-cart-icon")).click();
        Thread.sleep(1000);
        assertEquals(baseUrl, webDriver.getCurrentUrl(), "URLs not matching. Did not stay on homepage");
    }

    @Order(5)
    @Test
    public void testRemoveItemFromCart() throws InterruptedException {
        webDriver.get(testArticle);
        //webDriver.findElement(By.className("cc-compliance")).click();
        js.executeScript("window.scroll(0, 500)");
        Thread.sleep(1000);

        WebElement addToCartButton = webDriver.findElement(By.className("add-to-cart"));
        action.moveToElement(addToCartButton).perform();
        addToCartButton.click();

        Thread.sleep(2000);
        WebElement modal = webDriver.findElement(By.id("blockcart-modal"));
        modal.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/a")).click();

        WebElement container = webDriver.findElement(By.className("cart-grid-body"));
        List<WebElement> articles = container.findElements(By.className("card"));
        articles.get(0).findElement(By.className("remove-from-cart")).click();
        Thread.sleep(2000);
        WebElement xcontainer = webDriver.findElement(By.className("cart-grid-body"));
        List<WebElement> xarticles = xcontainer.findElements(By.className("card"));
        if(xarticles.get(0).findElements(By.className("cart-napomena")).size() != 0){
            xarticles.remove(0); //removes onaj defaultni card item sto bude ako nema nista u korpi
        }

        assertEquals(0, xarticles.size(), "Korpa not empty");
    }

    @Order(6)
    @Test
    public void testIncreaseItemQuantity() throws InterruptedException {
        webDriver.get(testArticle);
        //webDriver.findElement(By.className("cc-compliance")).click();
        js.executeScript("window.scroll(0, 500)");
        Thread.sleep(1000);


        WebElement addToCartButton = webDriver.findElement(By.className("add-to-cart"));
        action.moveToElement(addToCartButton).perform();
        addToCartButton.click();

        Thread.sleep(2000);
        WebElement modal = webDriver.findElement(By.id("blockcart-modal"));
        modal.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/a")).click();

        WebElement container = webDriver.findElement(By.className("cart-grid-body"));
        WebElement article = container.findElement(By.className("card"));

        String oldPriceString = article.findElement(By.className("product-price")).getText();
        oldPriceString = oldPriceString.replaceAll("[^0-9.,]", "");
        oldPriceString = oldPriceString.replace(".", "").replace(",", ".");
        double oldPrice = Float.valueOf(oldPriceString);
        String quantity = article.findElement(By.tagName("input")).getAttribute("value");

        List<WebElement> upDownButtons = article.findElements(By.className("input-group-btn-vertical"));
        upDownButtons.get(1).click(); //increase quantity
        Thread.sleep(2000);

        String newPriceString = article.findElement(By.className("product-price")).getText();
        newPriceString = newPriceString.replaceAll("[^0-9.,]", "");
        newPriceString = newPriceString.replace(".", "").replace(",", ".");
        double newPrice = Float.valueOf(newPriceString);
        String newQuantity = article.findElement(By.tagName("input")).getAttribute("value");

        assertEquals(newPrice, oldPrice * 2, "New price not 2x old price");
        assertEquals(newQuantity, "2", "Quantity not good");
    }

    @Order(7)
    @Test
    public void testDecreaseItemQuantity() throws InterruptedException {
        webDriver.get(testArticle);

        js.executeScript("window.scroll(0, 500)");
        Thread.sleep(1000);


        WebElement addToCartButton = webDriver.findElement(By.className("add-to-cart"));
        action.moveToElement(addToCartButton).perform();
        addToCartButton.click();

        Thread.sleep(2000);
        WebElement modal = webDriver.findElement(By.id("blockcart-modal"));
        modal.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/button")).click();

        action.moveToElement(addToCartButton).perform();
        addToCartButton.click();

        Thread.sleep(2000);
        WebElement ymodal = webDriver.findElement(By.id("blockcart-modal"));
        ymodal.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/button")).click();

        //Thread.sleep(2000);
        WebElement xmodal = webDriver.findElement(By.id("blockcart-modal"));
        xmodal.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/a")).click();

        WebElement container = webDriver.findElement(By.className("cart-grid-body"));
        WebElement article = container.findElement(By.className("card"));

        String oldPriceString = article.findElement(By.className("product-price")).getText();
        oldPriceString = oldPriceString.replaceAll("[^0-9.,]", "");
        oldPriceString = oldPriceString.replace(".", "").replace(",", ".");
        double oldPrice = Float.valueOf(oldPriceString);
        String quantity = article.findElement(By.tagName("input")).getAttribute("value");

        List<WebElement> upDownButtons = article.findElements(By.className("input-group-btn-vertical"));
        upDownButtons.get(0).click(); //decrease quantity
        Thread.sleep(2000);

        String newPriceString = article.findElement(By.className("product-price")).getText();
        newPriceString = newPriceString.replaceAll("[^0-9.,]", "");
        newPriceString = newPriceString.replace(".", "").replace(",", ".");
        double newPrice = Float.valueOf(newPriceString);
        String newQuantity = article.findElement(By.tagName("input")).getAttribute("value");

        assertEquals(newPrice, oldPrice / 2, "New price not 2x old price");
        assertEquals(newQuantity, "1", "Quantity not good");
    }

    @Order(8)
    @Test
    public void testKorpaFinalization() throws InterruptedException {
        webDriver.get("https://imtec.ba/olovke-i-markeri/26654-hemijska-olovka-schneider-k-15-crvena-7200000001032.html");
        //webDriver.findElement(By.className("cc-compliance")).click();
        js.executeScript("window.scroll(0, 500)");
        Thread.sleep(1000);

        String title = webDriver.findElement(By.className("h1")).getText().toLowerCase();

        WebElement addToCartButton = webDriver.findElement(By.className("add-to-cart"));
        action.moveToElement(addToCartButton).perform();
        addToCartButton.click();

        Thread.sleep(2000);
        WebElement modal = webDriver.findElement(By.id("blockcart-modal"));
        modal.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/div/a")).click();

        webDriver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/div[1]/div[2]/div/a")).click();

        webDriver.findElement(By.name("id_gender")).click();
        webDriver.findElement(By.name("firstname")).click();
        webDriver.findElement(By.name("firstname")).sendKeys("svvttest");
        webDriver.findElement(By.name("lastname")).click();
        webDriver.findElement(By.name("lastname")).sendKeys("svvttest");
        webDriver.findElement(By.name("email")).click();
        webDriver.findElement(By.name("email")).sendKeys("4@test.com");
        webDriver.findElement(By.name("optin")).click();
        js.executeScript("window.scroll(0, 500)");
        Thread.sleep(1000);
        webDriver.findElement(By.name("gdpr_consent_chkbox")).click();

        webDriver.findElement(By.name("continue")).click();

        webDriver.findElement(By.name("address1")).click();
        webDriver.findElement(By.name("address1")).sendKeys("svvttest");
        webDriver.findElement(By.name("postcode")).click();
        webDriver.findElement(By.name("postcode")).sendKeys("svvttest");
        js.executeScript("window.scroll(0, 500)");
        Thread.sleep(1000);
        webDriver.findElement(By.name("city")).click();
        webDriver.findElement(By.name("city")).sendKeys("svvttest");
        js.executeScript("window.scroll(0, 500)");
        Thread.sleep(1000);
        webDriver.findElement(By.name("phone")).click();
        webDriver.findElement(By.name("phone")).sendKeys("066 666 666");

        webDriver.findElement(By.name("confirm-addresses")).click();

        webDriver.findElement(By.id("delivery_message")).click();
        webDriver.findElement(By.id("delivery_message")).sendKeys("Some komentar");

        webDriver.findElement(By.name("confirmDeliveryOption")).click();

        webDriver.findElement(By.id("payment-option-1")).click();
        webDriver.findElement(By.id("payment-option-2")).click();
        webDriver.findElement(By.id("payment-option-3")).click();
        /*try{ //blokira me jer valjda ne da robotim da unesu bank details
            webDriver.findElement(By.id("bankart-payment-gateway-ccCardHolder-p34e4b953d3eaf4850c95")).click();
            webDriver.findElement(By.id("bankart-payment-gateway-ccCardHolder-pc471783a35be26a1dd75")).sendKeys("Some bank");
            webDriver.switchTo().frame(10);
            webDriver.findElement(By.id("pan")).click();
            webDriver.findElement(By.id("pan")).sendKeys("0000 0000 0000 0000");
            webDriver.switchTo().defaultContent();
            webDriver.switchTo().frame(11);
            webDriver.findElement(By.id("cvv")).click();
            webDriver.findElement(By.id("cvv")).sendKeys("000");
            webDriver.switchTo().defaultContent();
            webDriver.findElement(By.id("bankart-payment-gateway-ccExpiryMonth-pc471783a35be26a1dd75")).click();
        }catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }*/
        js.executeScript("window.scroll(0, 500)");
        Thread.sleep(1000);
        webDriver.findElement(By.id("payment-option-4")).click();
        webDriver.findElement(By.id("payment-option-5")).click();
        webDriver.findElement(By.id("payment-option-1")).click();
        webDriver.findElement(By.id("conditions_to_approve[terms-and-conditions]")).click();
        webDriver.findElement(By.cssSelector(".ps-shown-by-js > .btn")).click();

        //https://imtec.ba/potvrda-narudzbe-lscfl?key=ec7c6b3aedab28b8d1afbee3aa3f0615&id_cart=62111&id_module=109&id_order=10401
        WebElement container = webDriver.findElement(By.className("card-block"));
        String confirmationText = container.findElement(By.className("card-title")).getText();
        assertEquals("vaša narudžba je potvrđena", confirmationText.substring(1).toLowerCase(), "URLs dont match");

        WebElement xContainer = webDriver.findElement(By.id("order-items"));
        String productName = xContainer.findElement(By.xpath("//*[@id=\"order-items\"]/div/div/div[2]/span")).getText().toLowerCase();
        assertEquals(title, productName, "Product name not matching");

        Thread.sleep(1000);
    }

}
