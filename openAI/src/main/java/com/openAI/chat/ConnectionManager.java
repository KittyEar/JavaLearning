package com.openAI.chat;


import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionManager {

    public HttpURLConnection createConnection() {
        try {
            // Create URL object
            String url1 = "https://api.openai.com/v1/chat/completions";
            URL url = new URL(url1);
            // Create HTTP connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // Set request method
            conn.setRequestMethod("POST");
            // Set request properties
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer your Api Key");
            // Enable output
            conn.setDoOutput(true);
            return conn;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
