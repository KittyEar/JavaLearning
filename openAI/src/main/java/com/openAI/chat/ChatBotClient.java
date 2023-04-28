package com.openAI.chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.net.http.HttpRequest;

import java.net.URI;
public class ChatBotClient {
    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfig.class);
        applicationContext.refresh();

        UserInputReader userInputReader = applicationContext.getBean(UserInputReader.class);
        ConnectionManager connectionManager = applicationContext.getBean(ConnectionManager.class);
        RequestSender requestSender = applicationContext.getBean(RequestSender.class);
        ResponseParser responseParser = applicationContext.getBean(ResponseParser.class);
        LinkedHashMap<String, String> prompt =  new LinkedHashMap<>();// question -> answer
        showGeo();
        while (true) {
            String question = userInputReader.getUserQuestion();
            if (question.equals("clear")) {
                prompt.clear();
            } else {
                HttpURLConnection conn = connectionManager.createConnection();
                String responseBody = requestSender.sendRequest(conn, question, prompt);
                String[] answer = responseParser.parseResponse(responseBody);
                System.out.print("回答：");
                System.out.println(answer[0]);
                prompt.put(question, answer[1]);
                conn.disconnect();
            }
            System.out.println();
        }
    }
    private static void showGeo() {
        String url = "https://api.ip.sb/geoip";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            String country = jsonNode.get("country").asText();
            String city = jsonNode.get("city").asText();
            String isp = jsonNode.get("isp").asText();

            System.out.println("Country: " + country);
            System.out.println("City: " + city);
            System.out.println("ISP: " + isp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

