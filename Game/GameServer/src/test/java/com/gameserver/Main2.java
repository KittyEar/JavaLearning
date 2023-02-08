package com.gameserver;

import java.io.*;

public class Main2 {
    public static void main(String[] args) {
//        String[] strings = {
//                "SysBasic",
//                "SysConfig",
//                "SysLog",
//                "SysAdmin",
//                "SysData",
//                "SysInit",
//        };
//        for (String str : strings){
//            File file = new File("C:/Users/25852/Desktop/"+ str +".vue");
//            try {
//                OutputStream outputStream = new FileOutputStream(file);
//                try {
//                    outputStream.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
        File file = new File("C:/Users/25852/Desktop/test.txt");
        String str = "你好";
        try (OutputStream outputStream = new FileOutputStream(file)) {
          outputStream.write(str.getBytes());
          outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
