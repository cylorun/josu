package com.cylorun.beatmap.data;

import com.cylorun.beatmap.Beatmap;
import com.cylorun.game.objects.HitObject;
import com.cylorun.util.ConversionUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeatmapObjectData extends BeatmapDataParser<BeatmapObjectData> implements Iterator<HitObject> {
    public List<HitObject> objects;
    private int currentIdx = 0;
    private Beatmap map;

    private static String SECTION_INDENTIFIER = "[HitObjects]";

    private BeatmapObjectData(Beatmap map) {
        this.objects = new ArrayList<>();
        this.map = map;
    }

    public HitObject current() {
        return this.objects.get(this.currentIdx - (this.currentIdx == 0 ? 0 : 1)); // -1 cause this.next() post increments
    }


    /***
     * returns the circles that should be displayed now
     ***/
    public List<HitObject> getCurrentObjects(long msIn) {
        return this.objects.stream().filter(o -> {
            return msIn - ConversionUtil.getARms(this.map.getDifficultyData().getApproachRate()) >= o.getTime();
        }).toList();
    }

    @Override
    public boolean hasNext() {
        return this.objects.size() > this.currentIdx;
    }

    @Override
    public HitObject next() {
        return this.objects.get(currentIdx++);
    }

    public static BeatmapObjectData from(Beatmap map) {
        return BeatmapDataParser.from(map, SECTION_INDENTIFIER, new Factory<>() {
            @Override
            public BeatmapObjectData create(Beatmap map) {
                return new BeatmapObjectData(map);
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
