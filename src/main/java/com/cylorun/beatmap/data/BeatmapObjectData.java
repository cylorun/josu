package com.cylorun.beatmap.data;

import com.cylorun.beatmap.Beatmap;
import com.cylorun.game.objects.HitObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeatmapObjectData extends BeatmapDataParser<BeatmapObjectData> implements Iterator<HitObject> {
    public List<HitObject> objects;
    private int currentIdx = 0;

    private static String SECTION_INDENTIFIER = "[HitObjects]";

    private BeatmapObjectData() {
        this.objects = new ArrayList<>();
    }

    public HitObject current() {
        return this.objects.get(this.currentIdx - (this.currentIdx == 0 ? 0 : 1)); // -1 cause this.next() post increments
    }

    @Override
    public boolean hasNext() {
        return this.objects.size() < this.currentIdx;
    }

    @Override
    public HitObject next() {
        return this.objects.get(currentIdx++);
    }

    public static BeatmapObjectData from(Beatmap map) {
        return BeatmapDataParser.from(map, SECTION_INDENTIFIER, new Factory<>() {
            @Override
            public BeatmapObjectData create() {
                return new BeatmapObjectData();
            }

            @Override
            public void parseLine(BeatmapObjectData instance, String line) {
                try {
                    instance.objects.add(HitObject.fromLine(line));
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Error processing object from: " + line);
                }
            }
        });
    }
}
