package com.openAI.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInputReader {

    private final BufferedReader reader;

    public UserInputReader() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getUserQuestion() {
        // Read user input
        System.out.print("请输入问题：");
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
