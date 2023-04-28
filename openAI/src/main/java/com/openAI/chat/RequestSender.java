package com.openAI.chat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RequestSender {

    public String sendRequest(HttpURLConnection conn, String question, Map<String, String> prompt) {

        StringBuilder response = new StringBuilder();
        try {
            // Set request body
            StringBuilder builder = new StringBuilder();
            if (!prompt.isEmpty()) {
                builder.append("之前的聊天记录如下: \\n");
                prompt.forEach((k, v) -> builder.append(k).append(" -> ").append(v).append("\\n"));
                builder.append("下面是当前的问题: \\n");
            }
            builder.append(question);
            String requestBody = """
                    {
                        "model": "gpt-3.5-turbo",
                        "messages": [{
                            "role": "user",
                            "content": "%s"
                        }]
                    }
                    """.formatted(builder.toString());
            // Write request body to output stream
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
            // Read response from input stream
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // Return response body
            } catch (Exception exception) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}
