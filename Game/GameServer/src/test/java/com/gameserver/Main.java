package com.gameserver;

import java.util.*;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            int[] a = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            System.out.println(solve(a));
        }
    }
    private static int solve(int[] a){
        int res = 0;
        int n = a.length;
        int sum = Arrays.stream(a).sum();
        int[] aa = new int[n + 1];
        System.arraycopy(a, 0, aa, 1, n);
        sum /= n;
        for (int i = 1; i <=n; i++) {
            aa[i] -= sum;
        }
        for (int i = 1; i <=n; i++) {
            if(aa[i] != 0){
                aa[i + 1] +=aa[i];
                res++;
            }
        }
        return res;
    }
}

