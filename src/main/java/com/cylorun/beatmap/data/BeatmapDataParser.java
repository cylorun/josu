package com.cylorun.beatmap.data;

import com.cylorun.beatmap.Beatmap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public abstract class BeatmapDataParser<T> {

    private static Pattern DATA_SECTION_PATTERN = Pattern.compile("\\[[\\w\\s]*\\]\n");

    public static <T> T from(Beatmap map, Supplier<T> factory, String sectionIdentifier) {
        T instance = factory.get();
        try (BufferedReader reader = new BufferedReader(new FileReader(map.getDataPath().toFile()))) {
            String line;
            boolean foundSection = false;

            while (((line = reader.readLine()) != null)) {
                if (!line.strip().equals(sectionIdentifier) && !foundSection) {
                    continue;
                }

                foundSection = true;
                if (line.equals(sectionIdentifier)) {
                    continue;
                }

                if (DATA_SECTION_PATTERN.matcher(line).find() || line.isEmpty()) {
                    break;
                }

                parseLine(instance, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private static <T> void parseLine(T instance, String line) {
        try {
            String[] splitLine = line.split(":", 2);
            String fieldName = Character.toString(splitLine[0].charAt(0)).toLowerCase() + splitLine[0].strip().substring(1);
            String fieldValue = splitLine[1].strip();
            setFieldValue(instance, fieldName, fieldValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static <T, V> void setFieldValue(T instance, String fieldName, V value) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = instance.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        if (field.getType().getName().equals("java.lang.Long")) {
            field.set(instance, Long.valueOf((String) value));
        } else {
            field.set(instance, value);
        }
    }

    protected abstract String getDataSectionIdentifier();
}
