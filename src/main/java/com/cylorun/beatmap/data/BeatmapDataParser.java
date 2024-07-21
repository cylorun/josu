package com.cylorun.beatmap.data;

import com.cylorun.beatmap.Beatmap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

public abstract class BeatmapDataParser<T> {

    private static final Pattern DATA_SECTION_PATTERN = Pattern.compile("\\[[\\w\\s]*\\]\n");

    public static <T> T from(Beatmap map, String sectionIdentifier, Factory<T> factory) {
        T instance = factory.create();
        try (BufferedReader reader = new BufferedReader(new FileReader(map.getDataPath().toFile()))) {
            String line;
            boolean foundSection = false;

            while ((line = reader.readLine()) != null) {
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

                factory.parseLine(instance, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public interface Factory<T> {
        T create();

        void parseLine(T instance, String line);
    }
}
