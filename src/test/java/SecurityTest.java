import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityTest extends BaseTest {
    private HttpURLConnection connection;

    @BeforeEach
    public void createConnection() throws IOException {
        connection = (HttpURLConnection) new URL("https://imtec.ba/").openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
    }

    @BeforeAll
    public static void getUrl(){
        webDriver.get(baseUrl);
    }

    @Test
    public void testUsingHttps(){
        String url = webDriver.getCurrentUrl();
        assertTrue(url.startsWith("https://"), "URL should start with https://");
    }

    @Test
    public void testValidSSLCertificate(){
        try{
            URL url = new URL(webDriver.getCurrentUrl());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();

            Certificate[] certs = connection.getServerCertificates();

            for (Certificate cert : certs) {
                if (cert instanceof X509Certificate) {
                    X509Certificate x509Cert = (X509Certificate) cert;
                    x509Cert.checkValidity(); //baci exception ako je certifikat expired
                    assertNotNull(x509Cert.getIssuerX500Principal(), "Certificate issuer is not valid.");
                    assertNotNull(x509Cert.getSubjectX500Principal(), "Certificate subject is not valid.");
                }
            }

        } catch (Exception e){
            fail("SSL certificate is invalid or connection failed: " + e.getMessage());
        }
    }

    @Test
    public void testHSTSHeader() throws Exception {
        String hstsHeader = connection.getHeaderField("Strict-Transport-Security");
        assertNotNull(hstsHeader, "HSTS header should be present");
        assertFalse(hstsHeader.isEmpty(), "HSTS header should not be empty");
    }


    @Test
    public void testxContentTypeOptionsHeader() throws Exception {
        String xContentTypeOptionsHeader = connection.getHeaderField("X-Content-Type-Options");
        assertNotNull(xContentTypeOptionsHeader, "X-Content-Type-Options header should be present");
        assertEquals(xContentTypeOptionsHeader, "nosniff", "X-Content-Type-Options header should be 'nosniff'" );
    }

    @Test
    public void testpermissionsPolicyHeader() throws Exception {
        String cacheControlHeader = connection.getHeaderField("Cache-Control");
        assertNotNull(cacheControlHeader, "Cache-Control header should be present");
        assertTrue(cacheControlHeader.contains("no-store") || cacheControlHeader.contains("no-cache"), "Cache-Control header should contain 'no-store' or 'no-cache'");
    }
}
