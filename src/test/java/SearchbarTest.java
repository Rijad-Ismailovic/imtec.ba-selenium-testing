import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchbarTest extends BaseTest{

    public static WebElement searchbar;
    public static WebElement searchButton;
    public static String searchWord = "router";

    @BeforeEach
    public void getUrl(){
        webDriver.get(baseUrl);
        searchbar = webDriver.findElement(By.name("s"));
        searchButton = webDriver.findElement(By.xpath("//*[@id=\"_desktop_search\"]/div/div/div[2]/div/form/div[2]/button/i"));
    }

    @Test
    public void testExactMatch(){
        searchbar.sendKeys("Laptop HP EliteBook 840 G10 (819Y8EA)");
        searchButton.click();

        List<WebElement> items = webDriver.findElements(By.className("item"));
        items.get(0).click();

        assertEquals("Laptop HP EliteBook 840 G10 (819Y8EA)".toLowerCase(), webDriver.findElement(By.className("h1")).getText().trim().toLowerCase(), "Listing title not matching");
        assertEquals("https://imtec.ba/laptopi/71086-laptop-hp-elitebook-840-g10-819y8ea-.html", webDriver.getCurrentUrl(), "Listing URL not matching");
    }

    @Test
    public void testPartialSearch() throws InterruptedException {
        searchbar.sendKeys(searchWord);
        searchButton.click();

        List<WebElement> items = webDriver.findElements(By.className("product-miniature"));
        for(WebElement item : items){
            String title = item.findElement(By.tagName("h6")).getText().toLowerCase();
            assertTrue(title.contains(searchWord), "Title doesnt contain searchWord:".concat(title));
        }
    }

    @Test
    public void testNoResultSearch() throws InterruptedException {
        searchbar.sendKeys("banana");
        searchButton.click();

        List<WebElement> items = webDriver.findElements(By.className("product-miniature"));
        assertTrue(items.isEmpty());
    }

    @Test
    public void testDropdown() throws InterruptedException {
        //searchbar.sendKeys(searchWord);
        for (char c : searchWord.toCharArray()) { //ovo sam uradio jer ako odma inputa searchWord ne registurje je i pokaze pogresne dropdowns
            searchbar.sendKeys(String.valueOf(c));
            Thread.sleep(200);
        }
        Thread.sleep(1000);
        List<WebElement> dropdownItems = webDriver.findElements(By.className("tvsearch-dropdown-wrapper"));
        for(WebElement dropdownTitle : dropdownItems){
            String title = dropdownTitle.findElement(By.className("tvsearch-dropdown-title")).getText().toLowerCase();
            assertTrue(title.contains(searchWord), "Dropdown title doesnt contain searchWord:".concat(title));
        }
    }
}
