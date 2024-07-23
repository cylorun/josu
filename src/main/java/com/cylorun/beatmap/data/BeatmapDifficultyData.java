package com.cylorun.beatmap.data;

import com.cylorun.beatmap.Beatmap;

import java.lang.reflect.Field;
import java.util.Arrays;

public class BeatmapDifficultyData extends BeatmapDataParser<BeatmapDifficultyData> {

    private double hpDrainRate;
    private double circleSize;
    private double overallDifficulty;
    private double approachRate;
    private double sliderMultiplier;
    private double sliderTickRate;

    private static String SECTION_IDENTIFIER = "[Difficulty]";

    public static BeatmapDifficultyData from(Beatmap map) {
        return BeatmapDataParser.from(map, SECTION_IDENTIFIER, new Factory<BeatmapDifficultyData>() {
            @Override
            public BeatmapDifficultyData create() {
                return new BeatmapDifficultyData();
            }

            @Override
            public void parseLine(BeatmapDifficultyData instance, String line) {
                try {
                    String[] splitLine = line.split(":", 2);
                    String fieldName = splitLine[0].strip();
                    String fieldValue = splitLine[1].strip();
                    setFieldValue(instance, fieldName, fieldValue);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            private <T> void setFieldValue(BeatmapDifficultyData instance, String fieldName, T value) throws NoSuchFieldException, IllegalAccessException {
                Field field = this.getFieldIgnoreCase(instance, fieldName);
                field.setAccessible(true);

                field.set(instance, Double.parseDouble((String) value));
            }

            private Field getFieldIgnoreCase(BeatmapDifficultyData instance, String fieldName) throws NoSuchFieldException {
                for (Field f : instance.getClass().getDeclaredFields()) {
                    if (f.getName().equalsIgnoreCase(fieldName)) {
                        return f;
                    }
                }
                throw new NoSuchFieldException("No field found: " + fieldName);
            }
        });
    }

    public double getHpDrainRate() {
        return hpDrainRate;
    }

    public double getCircleSize() {
        return circleSize;
    }

    public double getOverallDifficulty() {
        return overallDifficulty;
    }

    public double getApproachRate() {
        return approachRate;
    }

    public double getSliderMultiplier() {
        return sliderMultiplier;
    }

    public double getSliderTickRate() {
        return sliderTickRate;
    }
}
