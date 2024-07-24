package com.cylorun.beatmap;

import com.cylorun.beatmap.data.BeatmapDifficultyData;

import java.nio.file.Path;

public class Beatmap {

    private Path dataPath;
    private BeatmapDifficultyData difficultyData;

    private long startTimeMs;

    public Beatmap(Path dataPath) {
        this.dataPath = dataPath;
        this.difficultyData = BeatmapDifficultyData.from(this);

        this.startTimeMs = System.currentTimeMillis();
    }

    public long getStartTimeMs() {
        return this.startTimeMs;
    }

    public BeatmapDifficultyData getDifficultyData() {
        return difficultyData;
    }

    public Path getDataPath() {
        return this.dataPath;
    }

}
