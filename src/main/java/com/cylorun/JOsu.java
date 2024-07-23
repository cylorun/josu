package com.cylorun;

import com.cylorun.beatmap.Beatmap;
import com.cylorun.beatmap.data.BeatmapObjectData;
import com.cylorun.game.JOsuFrame;
import com.cylorun.game.objects.Circle;
import com.cylorun.game.objects.HitObject;

import java.nio.file.Path;

public class JOsu implements Runnable {

    private boolean running;
    private Thread thread;
    private Beatmap currentMap;

    private BeatmapObjectData objectData;
    public static final String VERSION = "0.0.1";
    private final int TARGET_FPS = 60;
    private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    private static JOsu instance;

    public static void main(String[] args) {
        JOsu.getInstance().start();
    }

    private JOsu() {
        this.running = false;
        this.thread = new Thread(this);

        this.currentMap = new Beatmap(Path.of("C:\\Users\\alfgr\\Desktop\\projects\\JAVA\\josu\\src\\main\\resources\\bpm_120.osu"));

        JOsuFrame.getInstance(); // opens and initializes game window
    }


    public static JOsu getInstance() {
        if (instance == null) {
            instance = new JOsu();
        }

        return instance;
    }

    public synchronized void start() {
        this.running = true;
        this.thread.start();
    }

    public synchronized void stop() {
        this.running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();
        long lastFpsTime = 0;
        int fps = 0;

        while (this.running) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;

            double delta = updateLength / ((double) OPTIMAL_TIME);

            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1000000000) {
                System.out.println("(FPS: " + fps + ")");
                lastFpsTime = 0;
                fps = 0;
            }

            this.update(delta);
            this.render();

            try {
                Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
            } catch (Exception e) {
            }
        }
        this.stop();
    }

    private BeatmapObjectData getObjectData(Beatmap map) {
        if (this.objectData == null) {
            this.objectData = BeatmapObjectData.from(map);
        }

        return this.objectData;
    }

    private void update(double delta) {
        BeatmapObjectData objData = this.getObjectData(this.currentMap);
        if (!objData.hasNext()) {
            System.out.println("end of map, GG");
            this.running = false;
            return;
        }

//        HitObject currObj = objData.current();
//        HitObject newObj = objData.next();
        for (HitObject obj : objectData.objects) {
            if (obj.isCircle()) {
                Circle c = obj.getAsCircle();
                c.tick();
            }
        }
    }

    private void render() {

    }

    public Beatmap getCurrentMap() {
        return this.currentMap;
    }

}