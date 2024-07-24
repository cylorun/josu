package com.cylorun.util;

public class ConversionUtil {

    // https://osu.ppy.sh/wiki/en/Beatmap/Circle_size
    public static int getCircleRadius(double circleSize) {
        return (int) (54.4 - (4.48 * circleSize));
    }

    public static int translateOsuCoordinate(int coord) {
        return (int) (coord * 1.5);
    }

    public static int getARms(double ar) {
        return ConversionUtil.getARPreemt(ar);
    }

    public static int getHitWindow(double ar) {
        int p = ConversionUtil.getARPreemt(ar);
        int f = ConversionUtil.getARPreemt(ar);
        return p - f;
    }


    // https://osu.ppy.sh/wiki/en/Beatmap/Approach_rate
    public static int getARPreemt(double ar) {
        if (ar == 5.0) {
            return 1200;
        }

        return (int) (ar < 5.0 ? (1200 + ((600 * (5 - ar)) / 5)) : (1200 - ((750 * (ar - 5)) / 5)));
    }

    // https://osu.ppy.sh/wiki/en/Beatmap/Approach_rate
    public static int getARFadeIn(double ar) {
        if (ar == 5.0) {
            return 800;
        }

        return (int) (ar < 5.0 ? (800 + ((400 * (5 - ar)) / 5)) : (800 - ((500 * (ar - 5)) / 5)));

    }


}
