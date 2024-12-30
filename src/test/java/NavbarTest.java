import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavbarTest extends BaseTest{

    @BeforeEach
    public void getUrl(){
        webDriver.get(baseUrl);
    }

    @Test
    public void testProizvodi() throws InterruptedException {
        Thread.sleep(1000);
        webDriver.findElement(By.className("cc-compliance")).click();
        WebElement proizvodiNavbarItem = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div/ul/li[2]"));
        proizvodiNavbarItem.click();

        WebElement ul = webDriver.findElement(By.className("mm_columns_ul_tab"));
        List<WebElement> tabItems = ul.findElements(By.className("mm_tab_toggle_title"));
        for(int j = 0; j < tabItems.size(); j++){
            webDriver.get(baseUrl);
            proizvodiNavbarItem = webDriver.findElement(By.className("mm_menu_content_title"));
            //proizvodiNavbarItem = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div/ul/li[2]"));
            proizvodiNavbarItem.click();

            ul = webDriver.findElement(By.className("mm_columns_ul_tab"));
            tabItems = ul.findElements(By.className("mm_tabs_has_content")); //mm_tab_toggle_title
            WebElement tabItem = tabItems.get(j);
            tabItem.click();

            WebElement categoryContainer = tabItem.findElement(By.className("mm_columns_contents_ul"));
            List<WebElement> hrefs = categoryContainer.findElements(By.tagName("a"));

            for(int i = 0; i < hrefs.size(); i++){
                webDriver.get(baseUrl);

                proizvodiNavbarItem = webDriver.findElement(By.className("mm_menu_content_title"));
                proizvodiNavbarItem.click();

                WebElement xul = webDriver.findElement(By.className("mm_columns_ul_tab"));
                List<WebElement> xtabItems = xul.findElements(By.className("mm_tabs_has_content"));
                WebElement xtabItem = xtabItems.get(j);
                xtabItem.click();

                WebElement xcategoryContainer = xtabItem.findElement(By.className("mm_columns_contents_ul"));
                List<WebElement> xhrefs = xcategoryContainer.findElements(By.tagName("a"));

                String xhrefText = xhrefs.get(i).getAttribute("href");
                xhrefs.get(i).click();
                assertEquals(xhrefText, webDriver.getCurrentUrl(), "Wrong href on: ".concat(xhrefText));
            }
        }
    }

    @Test
    public void testAktuelnaSnizenja(){
        WebElement aktuelnaSnizenjaNavbarItem = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div/ul/li[3]"));

        action.moveToElement(aktuelnaSnizenjaNavbarItem).perform();
        WebElement categoryContainer = aktuelnaSnizenjaNavbarItem.findElement(By.className("mm_columns_ul"));
        List<WebElement> hrefs = categoryContainer.findElements(By.tagName("a"));

        List<WebElement> categorySubcontainers = categoryContainer.findElements(By.className("mm_columns_li"));
        for(int i = 0; i < categorySubcontainers.size(); i++){
            webDriver.get(baseUrl);
            aktuelnaSnizenjaNavbarItem = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div/ul/li[3]"));
            action.moveToElement(aktuelnaSnizenjaNavbarItem).perform();

            categoryContainer = aktuelnaSnizenjaNavbarItem.findElement(By.className("mm_columns_ul"));
            categorySubcontainers = categoryContainer.findElements(By.className("mm_columns_li"));

            List<WebElement> xhrefs = categoryContainer.findElements(By.tagName("a"));
            xhrefs.remove(1);
            xhrefs.remove(1);
            String hrefText = xhrefs.get(i).getAttribute("href");

            categorySubcontainers.get(i).click();
            assertEquals(hrefText, webDriver.getCurrentUrl(), "Wrong href on: ".concat(hrefText));
        }
    }

    @Test
    public void testNovosti() throws InterruptedException {
        WebElement novostiNavbarElement = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div/ul/li[4]"));
        String href = novostiNavbarElement.findElement(By.tagName("a")).getAttribute("href");
        novostiNavbarElement.click();
        assertEquals(href, webDriver.getCurrentUrl(), "href doesnt match");
    }

    @Test
    public void testInfo(){
        WebElement infoNavbarItem = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div/ul/li[5]"));
        action.moveToElement(infoNavbarItem).perform();
        WebElement container = infoNavbarItem.findElement(By.className("mm_columns_ul"));
        List<WebElement> hrefs = container.findElements(By.tagName("a"));
        for (WebElement href : hrefs){
            System.out.println(href.getAttribute("href"));
        }
        for(int i = 0; i < hrefs.size(); i++){
            webDriver.get(baseUrl);
            infoNavbarItem = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div/ul/li[5]"));
            action.moveToElement(infoNavbarItem).perform();
            container = infoNavbarItem.findElement(By.className("mm_columns_ul"));
            hrefs = container.findElements(By.tagName("a"));
            String hrefText = hrefs.get(i).getAttribute("href");
            hrefs.get(i).click();
            assertEquals(hrefText, webDriver.getCurrentUrl(), "Wrong href on: ".concat(hrefText));
        }
    }

    @Test
    public void testTrgovine(){
        WebElement trgovineNavbarElement = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div/ul/li[6]"));
        String href = trgovineNavbarElement.findElement(By.tagName("a")).getAttribute("href");
        trgovineNavbarElement.click();
        assertEquals(href, webDriver.getCurrentUrl(), "href doesnt match");
    }

    @Test
    public void testKupovinaNaRate(){
        WebElement kupovinaNaRateNavbarElement = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div/ul/li[7]"));
        String href = kupovinaNaRateNavbarElement.findElement(By.tagName("a")).getAttribute("href");
        kupovinaNaRateNavbarElement.click();
        assertEquals(href, webDriver.getCurrentUrl(), "href doesnt match");
    }

    @Test
    public void testImtecSolutions(){
        WebElement imtecSolutionsElement = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div/ul/li[8]"));
        String href = imtecSolutionsElement.findElement(By.tagName("a")).getAttribute("href");
        imtecSolutionsElement.click();
        assertEquals(baseUrl, webDriver.getCurrentUrl(), "href doesnt match");

        Set<String> handles = webDriver.getWindowHandles();
        ArrayList<String> tabs = new ArrayList<>(handles);
        webDriver.switchTo().window(tabs.get(1));
        assertEquals(href, webDriver.getCurrentUrl(), "href doesnt match");
    }

    @Test
    public void testOutlet(){
        WebElement outletNavbarElement = webDriver.findElement(By.xpath("//*[@id=\"header\"]/div[3]/div/div/div/ul/li[9]"));
        String href = outletNavbarElement.findElement(By.tagName("a")).getAttribute("href");
        outletNavbarElement.click();
        assertEquals(href, webDriver.getCurrentUrl(), "href doesnt match");
    }
}
