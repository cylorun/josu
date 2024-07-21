package com.cylorun;

import com.cylorun.beatmap.Beatmap;
import com.cylorun.beatmap.data.BeatmapObjectData;
import com.cylorun.game.JOsuFrame;
import com.cylorun.game.objects.Circle;
import com.cylorun.game.objects.HitObject;

import java.nio.file.Path;
import java.util.List;

public class JOsu {
    public static final String VERSION = "0.0.1";

    public static void main(String[] args) {
        JOsuFrame f = JOsuFrame.getInstance();



        Thread thread = new Thread(JOsu::runLoop, "game-loop");
        thread.start();


    }

    private static void runLoop() {
        JOsuFrame f = JOsuFrame.getInstance();

        Beatmap loadedMap = new Beatmap(Path.of("E:\\coding\\projects\\java\\josu\\src\\main\\resources\\aim_map.osu"));
        BeatmapObjectData objectData = BeatmapObjectData.from(loadedMap);

        long lastns = System.nanoTime();
        long startTime = System.currentTimeMillis();

        HitObject current = objectData.current();

        while (true) {
            if (System.nanoTime() - lastns > 8_500_000 ) {
                current.waitForAppearance(startTime);

                if (current.getType().equals(HitObject.HitObjectType.CIRCLE)){
                    f.addCircle(current.getAsCircle());
                }

                current = objectData.next();


                lastns = System.nanoTime();
                List<Circle> circles = JOsuFrame.getInstance().getCircles();
                for (Circle circle : circles) {
                    circle.tick();
                }
            }
        }
    }
}