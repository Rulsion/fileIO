package com.rulsion.file.util;

import java.math.BigDecimal;

public class test {

    private final static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static void main(String[] args) {
        System.out.println(uniquePaths(20, 16));

    }

    public static int uniquePaths(int m, int n) {
        int min = (m > n ? n : m);
        min--;
        int count = m + n - 2;
        int a =count-min;
        int b=min;
        if (b>a){
            a=min;
            b=count-min;
        }

        Double outPut = 1D;
        int j = b;
        for (int i = count; i > a; i--) {
            outPut=outPut*i;
            if(j>0){
                outPut=outPut/j;
                j--;
            }

        }

        return (int) Math.round(outPut);
        // write your code here
    }

}
