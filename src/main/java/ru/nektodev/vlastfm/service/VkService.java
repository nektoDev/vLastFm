package ru.nektodev.vlastfm.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Service to work with VK.com API
 *
 * @author nektodev
 */
public class VkService {

    //TODO remove access token
    public static final String ACCESS_TOKEN = "b7e28a0bcaee17302bf7237fa8b75579bb5c832984c9d9d572ed867811530e534917cbec7982dcf8b4b0d";

    //TODO Максимальное качество

    /**
     * Method to find download URL on VK.com
     *
     * @param query query to search track. Typically looks like Atrtis - Track
     *
     * @return url string
     */
    //TODO make more readable. Split it!
    public static synchronized String getURL(String query) {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            //TODO sleep needed time, not CONST
            Thread.sleep(350);

            //TODO make more readable and add more params
            HttpGet getRequest = new HttpGet(
                    "https://api.vk.com/method/audio.search?access_token=" + ACCESS_TOKEN +
                            "&q=" + URLEncoder.encode(query) +
                            "&auto_complete=1&lyrics=0&performer_only=0&sort=2&search_own=0&offset=0&count=1&v=5.16");
            getRequest.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            StringBuffer result = new StringBuffer();
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                result.append(output);
            }
            JSONObject o = new JSONObject(result.toString());

            //TODO add another response types
            long count = o.optJSONObject("response").optLong("count");
            if (count != 0) {
                return o.optJSONObject("response").optJSONArray("items").optJSONObject(0).optString("url");
            } else {
                return "";
            }

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();

        }
        return "";
    }

}
