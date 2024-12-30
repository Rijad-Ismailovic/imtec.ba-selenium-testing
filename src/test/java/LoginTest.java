import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends BaseTest{

    @BeforeEach
    public void getUrl(){
        webDriver.get("https://imtec.ba/prijava-lscfl?back=my-account");
    }

    @Test
    @Order(1)
    public void testLoginNavigation() throws InterruptedException {
        webDriver.navigate().to(baseUrl);
        webDriver.findElement(By.xpath("//*[text()='Prijavite se']")).click();
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("//*[@id=\"tvcmsdesktop-user-info\"]/div/a/span")).click();

        assertEquals("https://imtec.ba/prijava-lscfl?back=my-account", webDriver.getCurrentUrl());
    }

    @Order(7)
    @Test
    public void testValidCredentials() throws InterruptedException {
        webDriver.findElement(By.name("email")).sendKeys("zetzolzar@gmail.com");
        webDriver.findElement(By.name("password")).sendKeys("rijadarmin123");
        Thread.sleep(1000);
        webDriver.findElement(By.id("submit-login")).click();
        Thread.sleep(1000);

        String fullName = webDriver.findElement(By.xpath("/html/body/main/div/header/div[2]/div[2]/div/div/div[3]/div/div[1]/div/div/div/button/span")).getText();
        assertEquals("RijadArmin IsmaBuza", fullName);
        assertEquals("https://imtec.ba/moj-racun-lscfl", webDriver.getCurrentUrl());
    }

    @Test
    @Order(2)
    public void testInvalidCredentials() throws InterruptedException {
        webDriver.findElement(By.name("email")).sendKeys("invalid@gmail.com");
        webDriver.findElement(By.name("password")).sendKeys("invalid");
        Thread.sleep(1000);
        webDriver.findElement(By.id("submit-login")).click();
        Thread.sleep(1000);

        String alertText = webDriver.findElement(By.className("alert-danger")).getText();
        assertEquals("Prijava nije uspjela. Pokušajte opet.", alertText);
        assertEquals("https://imtec.ba/prijava-lscfl?back=my-account", webDriver.getCurrentUrl());
    }

    @Test
    @Order(3)
    public void testMissingField() throws InterruptedException {
        webDriver.findElement(By.name("email")).sendKeys("zetzolzar@gmail.com");
        Thread.sleep(1000);
        webDriver.findElement(By.id("submit-login")).click();
        Thread.sleep(1000);

        assertEquals("https://imtec.ba/prijava-lscfl?back=my-account", webDriver.getCurrentUrl(), "All fields must be inputed");
    }

    @Test
    @Order(4)
    public void testPasswordFieldMasking() throws InterruptedException {
        WebElement passwordField = webDriver.findElement(By.name("password"));
        String inputType = passwordField.getAttribute("type");
        assertEquals(inputType, "password", "Password field should be masked");
    }

    @Test
    @Order(5)
    public void testShowPassword() throws InterruptedException {
        webDriver.findElement(By.name("password")).sendKeys("123456789");
        webDriver.findElement(By.xpath("//*[@id=\"login-form\"]/div/div[2]/div[1]/div/span/button")).click();
        Thread.sleep(1000);
        String unhiddenPassword = webDriver.findElement(By.name("password")).getAttribute("value");
        assertEquals("123456789", unhiddenPassword);

        webDriver.findElement(By.xpath("//*[@id=\"login-form\"]/div/div[2]/div[1]/div/span/button")).click();
        Thread.sleep(1000);
        String hiddenPassword = webDriver.findElement(By.name("password")).getAttribute("value");
        assertEquals("123456789", hiddenPassword);
    }

    @Test
    @Order(6)
    public void testForgotPassword() throws InterruptedException {
        webDriver.findElement(By.linkText("Zaboravili ste lozinku?")).click();
        webDriver.findElement(By.name("email")).sendKeys("zetzolzar@gmail.com");
        webDriver.findElement(By.xpath("//*[text()='Pošalji novi link']")).click();

        String confirmationText = webDriver.findElement(By.xpath("//*[@id=\"content\"]/ul/li/p")).getText();
        assertEquals("Ako je ova adresa e-pošte registrirana u našoj trgovini, dobit ćete vezu za resetiranje zaporke na zetzolzar@gmail.com.", confirmationText);

    }

    @Test
    @Order(8)
    public void testLogout() throws InterruptedException {
        webDriver.get(baseUrl);
        if(webDriver.findElement(By.xpath("//*[@id=\"tvcmsdesktop-account-button\"]/div/div/div/button/span")).getText() == "Prijavite se"){
            testValidCredentials();
        } else{
            webDriver.findElement(By.xpath("//*[@id=\"tvcmsdesktop-account-button\"]/div/div/div/button")).click();
            Thread.sleep(1000);
            webDriver.findElement(By.linkText("Odjavi se")).click();
            assertEquals("https://imtec.ba/", webDriver.getCurrentUrl());
        }
    }
}
