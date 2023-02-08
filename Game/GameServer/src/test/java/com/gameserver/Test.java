package com.gameserver;

import java.util.LinkedList;
import java.util.List;

public class Test {
    private static List<Integer> list = new LinkedList<>();
    static class RunnableA implements Runnable{
        @Override
        public void run() {
            if(list.size() > 0){
                System.out.println(list.remove(0));
            }
        }
    }
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        for (int i = 0; i < 10; i++) {
            new Thread(new RunnableA()).start();
        }
    }
}
