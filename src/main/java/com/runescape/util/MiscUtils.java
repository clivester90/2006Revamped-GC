package com.runescape.util;

public class MiscUtils {

    public static int[] d2Tod1(int[][] array) {
        int[] newArray = new int[array.length*array[0].length];

        for (int i = 0; i < array.length; ++i)
            System.arraycopy(array[i], 0, newArray, i * array[0].length, array[i].length);

        return newArray;
    }

    public static int[][] d1Tod2(int[] array, int width) {
        int[][] newArray = new int[array.length/width][width];

        for (int i = 0; i < array.length; ++i) {
            newArray[i/width][i%width] = array[i];
        }

        return newArray;
    }

}
