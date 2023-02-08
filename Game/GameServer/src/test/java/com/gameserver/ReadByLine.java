package com.gameserver;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class ReadByLine {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File("C:/Users/25852/Desktop/test.txt");
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;

            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            arrayList.forEach(i -> {
                String[] a = i.split(" ");
                String sub = a[4].substring(0,3).toLowerCase(Locale.ROOT);
                //System.out.println();
                FileWriter writer = null;
                try {
                    //File file1 = new File("C:/Users/25852/Desktop/test2.txt");
                    writer = new FileWriter("C:/Users/25852/Desktop/test2.txt",true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BufferedWriter bw = new BufferedWriter(writer);

                try {
                    bw.write( a[4]+ ", ");
                    //bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bf.close();
            inputReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
