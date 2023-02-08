package com.gameserver;


import java.util.BitSet;


public class Test01 {
    public static void main(String[] args) {


    }
    private static int solve(int[] a){
        BitSet bitSet = new BitSet(a.length + 1);
        for (int j : a) {
            if (bitSet.get(j)) {
                return j;
            } else {
                bitSet.set(j, true);
            }
        }
        return 0;
    }
}
