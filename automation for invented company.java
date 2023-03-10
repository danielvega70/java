import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class APIClient {

    // Email API
    public static void sendEmail(String subject, String body, String recipient) throws IOException {
        String apiKey = "INSERT_API_KEY_HERE";
        String endpoint = "https://api.email.com/send";
        String payload = "{\"subject\": \"" + subject + "\", \"body\": \"" + body + "\", \"recipient\": \"" + recipient + "\"}";
        HttpPost postRequest = new HttpPost(endpoint);
        postRequest.addHeader("Authorization", apiKey);
        postRequest.setEntity(new StringEntity(payload, ContentType.APPLICATION_JSON));
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                CloseableHttpResponse response = httpClient.execute(postRequest)) {
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("Email sent successfully!");
            } else {
                System.out.println("Error sending email");
            }
            EntityUtils.consume(entity);
        }
    }

    // Call center API
    public static void makeCall(String number) throws IOException {
        String apiKey = "INSERT_API_KEY_HERE";
        String endpoint = "https://api.callcenter.com/call";
        String payload = "{\"number\": \"" + number + "\"}";
        HttpPost postRequest = new HttpPost(endpoint);
        postRequest.addHeader("Authorization", apiKey);
        postRequest.setEntity(new StringEntity(payload, ContentType.APPLICATION_JSON));
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                CloseableHttpResponse response = httpClient.execute(postRequest)) {
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("Call made successfully!");
            } else {
                System.out.println("Error making call");
            }
            EntityUtils.consume(entity);
        }
    }

    // Marketing API
    public static void sendMarketingEmail(String subject, String body, List<String> recipientList) throws IOException {
        String apiKey = "INSERT_API_KEY_HERE";
        String endpoint = "https://api.marketing.com/send";
        String recipients = String.join(",", recipientList);
        String payload = "{\"subject\": \"" + subject + "\", \"body\": \"" + body + "\", \"recipients\": \"" + recipients + "\"}";
        HttpPost postRequest = new HttpPost(endpoint);
        postRequest.addHeader("Authorization", apiKey);
        postRequest.setEntity(new StringEntity(payload, ContentType.APPLICATION_JSON));
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                CloseableHttpResponse response = httpClient.execute(postRequest)) {
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("Marketing email sent successfully!");
            } else {
                System.out.println("Error sending marketing email");
            }
            EntityUtils.consume(entity);
        }
    }

    // Example usage
    public static void main(String[] args) throws IOException {
        sendEmail("Welcome to our company", "Thank you for choosing our company", "john.doe@example.com");
        makeCall("123-456-7890");
        List<String> recipients = new ArrayList<String>();
        recipients.add("john.doe@example.com");
        recipients.add("jane.doe@example.com");
        sendMarketingEmail("Exclusive offer for our
