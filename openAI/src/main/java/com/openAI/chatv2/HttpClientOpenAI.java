package com.openAI.chatv2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Deprecated
public class HttpClientOpenAI {
    public static void main(String[] args) {
        try {
            // Enable system proxy
            System.setProperty("java.net.useSystemProxies", "true");
            // 创建一个HttpClient对象
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();

            // 设置请求体
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入问题：");
            String question = scanner.nextLine();
            String requestBody = """
                    {
                        "model": "gpt-3.5-turbo",
                        "messages": [{
                            "role": "user",
                            "content": "%s"
                        }]
                    }
                    """.formatted(question);

            // 创建一个HttpRequest对象
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer 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")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // 发送请求并处理响应
            CompletableFuture<HttpResponse<InputStream>> responseFuture = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream());
            StreamProcessor streamProcessor = new StreamProcessor(responseFuture);

            // 逐步处理响应数据
            JsonNode jsonNode;
            while ((jsonNode = streamProcessor.nextJsonNode()) != null) {
                // 获取choices数组中的第一个元素
                JsonNode choice = jsonNode.get("choices").get(0);
                // 获取message中的content字段
                String content = choice.get("message").get("content").asText();
                // 输出content字段
                System.out.println(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class StreamProcessor {
    private static final int BUFFER_SIZE = 4096;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final CompletableFuture<HttpResponse<InputStream>> responseFuture;
    private final byte[] buffer = new byte[BUFFER_SIZE];
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private boolean endOfStream = false;

    public StreamProcessor(CompletableFuture<HttpResponse<InputStream>> responseFuture) {
        this.responseFuture = responseFuture;
    }

    public JsonNode nextJsonNode() throws IOException, InterruptedException, ExecutionException {
        if (endOfStream) {
            return null;
        }

        // 读取HTTP响应数据并写入字节数组流
        int bytesRead = responseFuture.get().body().read(buffer);
        if (bytesRead == -1) {
            endOfStream = true;
        } else {
            baos.write(buffer, 0, bytesRead);
        }

        // 尝试将字节数组流中的数据解析为JSON对象
        byte[] bytes = baos.toByteArray();
        JsonNode jsonNode = null;
        try {
            jsonNode = OBJECT_MAPPER.readTree(bytes);
        } catch (JsonProcessingException e) {
            // 如果无法解析为JSON对象，则什么都不做
        }

        // 如果已经到达字节数组流的末尾，则关闭字节数组流
        if (endOfStream) {
            baos.close();
        }

        return jsonNode;
    }
}
