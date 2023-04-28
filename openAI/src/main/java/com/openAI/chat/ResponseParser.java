package com.openAI.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseParser {

    private final ObjectMapper objectMapper;

    public ResponseParser() {
        this.objectMapper = new ObjectMapper();
    }

    public String[] parseResponse(String responseBody)  {
        // 解析响应结果并输出content字段
        // 将JSON字符串解析为JsonNode对象
        JsonNode jsonNode = null;
        String[] result = new String[2];
        try {
            jsonNode = objectMapper.readTree(responseBody);
            // 获取choices数组中的第一个元素
            JsonNode choice = jsonNode.get("choices").get(0).get("message").get("content");
            // 获取message中的content字段
            // 输出content字段
            String temp = choice.toString();
            result[1] = temp.startsWith("\"") && temp.endsWith("\"") ? temp.substring(1, temp.length() - 1) : temp;
            result[0] = choice.asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
