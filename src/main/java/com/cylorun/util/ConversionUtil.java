package com.cylorun.util;

public class ConversionUtil {

    public static int getCircleRadius(double circleSize) {
        return (int) (54.4 - (4.48 * circleSize));
    }

    public static int translateOsuCoordinate(int coord) {
        return (int) (coord * 1.5);
    }



}
