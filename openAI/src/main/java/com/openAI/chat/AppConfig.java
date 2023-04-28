package com.openAI.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class AppConfig {
    @Bean(name = "connectionManager")
    public ConnectionManager connectionManager() {
        return new ConnectionManager();
    }

    @Bean(name = "requestSender")
    public RequestSender requestSender() {
        return new RequestSender();
    }

    @Bean(name = "responseParser")
    public ResponseParser responseParser() {
        return new ResponseParser();
    }

    @Bean(name = "userInputReader")
    public UserInputReader userInputReader() {
        return new UserInputReader();
    }

}