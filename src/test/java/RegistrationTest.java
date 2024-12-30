import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest extends BaseTest{

    public String getTempMail() throws InterruptedException {
        webDriver.get("https://temp-mail.org/en/");
        Thread.sleep(10000);
        String tempEmail = webDriver.findElement(By.id("email")).getText();
        webDriver.navigate().back();
        webDriver.navigate().refresh();
        System.out.println(tempEmail);
        return tempEmail;
    }

    @BeforeEach
    public void getUrl(){
        webDriver.get("https://imtec.ba/prijava-lscfl?create_account=1");
    }

    @Test
    public void testRegistrationNavigation() throws InterruptedException {
        webDriver.navigate().to(baseUrl);
        webDriver.findElement(By.xpath("//*[text()='Prijavite se']")).click();
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("//*[@id=\"tvcmsdesktop-user-info\"]/div/a/span")).click();
        webDriver.findElement(By.linkText("Niste kreirali korisnički profil? Kreirajte ovdje")).click();

    }

    @Test
    public void testValidRegistration() throws InterruptedException {
        //String tempMail = getTempMail();
        String tempMail = "temp@mail.com";

        WebElement gender = webDriver.findElement(By.name("id_gender"));
        gender.click();
        WebElement email = webDriver.findElement(By.name("email"));
        email.sendKeys(tempMail);
        WebElement firstName = webDriver.findElement(By.name("firstname"));
        firstName.sendKeys("svtt_test_name");
        WebElement lastName = webDriver.findElement(By.name("lastname"));
        lastName.sendKeys("svtt_test_lastname");
        WebElement password = webDriver.findElement(By.name("password"));
        password.sendKeys("svtt_test_password");
        for (WebElement checkbox : webDriver.findElements(By.xpath("//input[@type='checkbox']"))){
            checkbox.click();
        }
        //reCAPTHCA nece da se klikne. Pravit cemo se da se kliknula

        assertTrue(gender.isSelected(), "Gender must be selected");
        assertFalse(firstName.getAttribute("value").isEmpty(), "Name field must not be empty");
        assertFalse(lastName.getAttribute("value").isEmpty(), "Last name field must not be empty");
        assertFalse(email.getAttribute("value").isEmpty(), "Email field must not be empty");
        assertFalse(password.getAttribute("value").isEmpty(), "Password field must not be empty");
        for (WebElement checkbox : webDriver.findElements(By.xpath("//input[@type='checkbox']"))){
            assertTrue(checkbox.isSelected(), "Checkboxes must be selected");
        }
    }

    @Test
    public void testMissingField(){
        //String tempMail = getTempMail();
        String tempMail = "temp@mail.com";

        WebElement gender = webDriver.findElement(By.name("id_gender"));
        gender.click();
        WebElement email = webDriver.findElement(By.name("email"));
        email.sendKeys(tempMail);
        WebElement firstName = webDriver.findElement(By.name("firstname"));
        firstName.sendKeys("svtt_test_name");
        WebElement lastName = webDriver.findElement(By.name("lastname"));
        lastName.sendKeys("svtt_test_lastname");
        WebElement password = webDriver.findElement(By.name("password"));
        for (WebElement checkbox : webDriver.findElements(By.xpath("//input[@type='checkbox']"))){
            checkbox.click();
        }
        //reCAPTHCA nece da se klikne. Pravit cemo se da se kliknula

        System.out.println(password.getAttribute("value"));

        assertTrue(gender.isSelected(), "Gender must be selected");
        assertFalse(firstName.getAttribute("value").isEmpty(), "Name field must not be empty");
        assertFalse(lastName.getAttribute("value").isEmpty(), "Last name field must not be empty");
        assertFalse(email.getAttribute("value").isEmpty(), "Email field must not be empty");
        assertTrue(password.getAttribute("value").isEmpty(), "Password field is not empty");
        for (WebElement checkbox : webDriver.findElements(By.xpath("//input[@type='checkbox']"))){
            assertTrue(checkbox.isSelected(), "Checkboxes must be selected");
        }
        assertEquals("https://imtec.ba/prijava-lscfl?create_account=1", webDriver.getCurrentUrl());
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
        webDriver.findElement(By.xpath("//*[@id=\"customer-form\"]/div[1]/div[5]/div[1]/div/span/button")).click();
        Thread.sleep(1000);
        String unhiddenPassword = webDriver.findElement(By.name("password")).getAttribute("value");
        assertEquals("123456789", unhiddenPassword);

        webDriver.findElement(By.xpath("//*[@id=\"customer-form\"]/div[1]/div[5]/div[1]/div/span/button")).click();
        Thread.sleep(1000);
        String hiddenPassword = webDriver.findElement(By.name("password")).getAttribute("value");
        assertEquals("123456789", hiddenPassword);






    }
}
