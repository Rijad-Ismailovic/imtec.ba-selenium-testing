import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileTest extends BaseTest{

    @BeforeAll
    public static void login(){
        webDriver.get("https://imtec.ba/prijava-lscfl?back=my-account");
        webDriver.findElement(By.name("email")).sendKeys("zetzolzar@gmail.com");
        webDriver.findElement(By.name("password")).sendKeys("rijadarmin123");
        webDriver.findElement(By.id("submit-login")).click();
    }


    @BeforeEach
    public void getUrl(){
        webDriver.get("https://imtec.ba/moj-racun-lscfl");
    }

    @Test
    public void testClickEveryLink(){
        WebElement container = webDriver.findElement(By.className("links"));
        List<WebElement> links = container.findElements(By.tagName("a"));

        webDriver.findElement(By.id("identity-link")).click();
        assertEquals("https://imtec.ba/identitet-lscfl", webDriver.getCurrentUrl(), "Didnt redirect to Informacije");
        webDriver.navigate().back();

        webDriver.findElement(By.id("addresses-link")).click();
        assertEquals("https://imtec.ba/adrese-lscfl", webDriver.getCurrentUrl(), "Didnt redirect to Dodajte Vasu Adresu");
        webDriver.navigate().back();

        webDriver.findElement(By.id("history-link")).click();
        assertEquals("https://imtec.ba/istorija-narudzbi-lscfl", webDriver.getCurrentUrl(), "Didnt redirect to Historija Narudzbi i Detalji");
        webDriver.navigate().back();

        webDriver.findElement(By.id("order-slips-link")).click();
        assertEquals("https://imtec.ba/kreditna-nota-lscfl", webDriver.getCurrentUrl(), "Didnt redirect to Kreditni Listici");
        webDriver.navigate().back();

        webDriver.findElement(By.id("discounts-link")).click();
        assertEquals("https://imtec.ba/popust-lscfl", webDriver.getCurrentUrl(), "Didnt redirect to Vauceri");
        webDriver.navigate().back();

        webDriver.findElement(By.className("link_wishlist")).click();
        assertEquals("https://imtec.ba/tvcmswishlist-mywishlist-lscfl", webDriver.getCurrentUrl(), "Didnt redirect to Moji Artikli");
        webDriver.navigate().back();

        webDriver.findElement(By.xpath("//*[@id=\"content\"]/div/div/a[7]")).click();
        assertEquals("https://imtec.ba/my-account/delete-my-data", webDriver.getCurrentUrl(), "Didnt redirect to Brisanje Mojih Podataka");
        webDriver.navigate().back();

        webDriver.findElement(By.xpath("//*[@id=\"content\"]/div/div/a[8]")).click();
        assertEquals("https://imtec.ba/my-account/request-my-data", webDriver.getCurrentUrl(), "Didnt redirect to Zatrazi Moje Podatke");
        webDriver.navigate().back();
    }

    @Test
    public void testInformacije() throws InterruptedException {
        webDriver.findElement(By.id("identity-link")).click();

        WebElement ime = webDriver.findElement(By.name("firstname"));
        WebElement prezime = webDriver.findElement(By.name("lastname"));
        WebElement email = webDriver.findElement(By.name("email"));
        WebElement lozinka = webDriver.findElement(By.name("password"));
        WebElement nova_lozinka = webDriver.findElement(By.name("new_password"));

        ime.clear();
        prezime.clear();
        email.clear();

        ime.sendKeys("svvt");
        prezime.sendKeys("test");
        email.sendKeys("zetzolzar@gmail.com");
        lozinka.sendKeys("rijadarmin123");
        nova_lozinka.sendKeys("rijadarmin123");
        js.executeScript("window.scrollTo(0, 500);");
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("//*[@id=\"customer-form\"]/footer/button")).click();

        WebElement success_container = webDriver.findElement(By.className("alert-success"));
        String success_text = success_container.findElement(By.tagName("li")).getText();
        assertEquals("Informacije uspješno nadograđene.", success_text, "Success text doesnt match");
        assertEquals("https://imtec.ba/identitet-lscfl", webDriver.getCurrentUrl(), "Didnt correctly redirect to same page");
    }

    @Test
    public void testDodajteVasuAdresu() throws InterruptedException {
        webDriver.findElement(By.id("addresses-link")).click();
        WebElement mrzimovo = webDriver.findElement(By.className("addresses-footer"));
        mrzimovo.findElement(By.tagName("a")).click();
        js.executeScript("window.scrollTo(0, 450);");
        Thread.sleep(1000);

        WebElement alias = webDriver.findElement(By.name("alias"));
        WebElement ime = webDriver.findElement(By.name("firstname"));
        WebElement prezime = webDriver.findElement(By.name("lastname"));
        WebElement kompanija = webDriver.findElement(By.name("company"));
        WebElement vat_broj = webDriver.findElement(By.name("vat_number"));
        WebElement adresa = webDriver.findElement(By.name("address1"));
        WebElement dopuna_adrese = webDriver.findElement(By.name("address2"));
        WebElement postanski_broj = webDriver.findElement(By.name("postcode"));
        WebElement grad = webDriver.findElement(By.name("city"));
        WebElement broj_telefona = webDriver.findElement(By.name("phone"));

        alias.clear();
        ime.clear();
        prezime.clear();
        kompanija.clear();
        vat_broj.clear();
        adresa.clear();
        dopuna_adrese.clear();
        postanski_broj.clear();
        grad.clear();
        broj_telefona.clear();

        alias.sendKeys("svvt testeroni");
        ime.sendKeys("svvt");
        prezime.sendKeys("test");
        kompanija.sendKeys("ibu");
        vat_broj.sendKeys("0000");
        adresa.sendKeys("adresa");
        adresa.sendKeys("adresa2");
        postanski_broj.sendKeys("00000");
        grad.sendKeys("holywood");
        broj_telefona.sendKeys("012345678");

        webDriver.findElement(By.xpath("//*[@id=\"content\"]/div/div/form/footer/button")).click();
        WebElement success_container = webDriver.findElement(By.className("alert-success"));
        String success_text = success_container.findElement(By.tagName("li")).getText();
        assertEquals("Adresa je uspješno dodana!", success_text, "Success text doesnt match");
    }

    @Test
    public void testAddWishlistArticle() throws InterruptedException {
        String wishlist_name = "a dragon";

        webDriver.findElement(By.className("link_wishlist")).click();
        webDriver.findElement(By.name("name")).sendKeys(wishlist_name);
        webDriver.findElement(By.id("submitWishlist")).click();
        Thread.sleep(1000);

        WebElement table = webDriver.findElement(By.tagName("table"));
        WebElement tableBody = table.findElement(By.tagName("tbody"));
        List<WebElement> tableRows = tableBody.findElements(By.tagName("tr"));

        boolean isWishlistFound = false;

        for (WebElement row : tableRows) {
            String rowText = row.findElement(By.xpath("./td[1]")).getText();
            if (rowText.equals(wishlist_name)) {
                isWishlistFound = true;
                break;
            }
        }

        assertTrue(isWishlistFound, "Wishlist item was not found in the table.");
    }
}
