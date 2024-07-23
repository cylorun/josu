package com.cylorun.beatmap;

import com.cylorun.beatmap.data.BeatmapDifficultyData;

import java.nio.file.Path;

public class Beatmap {

    private Path dataPath;
    private BeatmapDifficultyData difficultyData;

    public Beatmap(Path dataPath) {
        this.dataPath = dataPath;
        this.difficultyData = BeatmapDifficultyData.from(this);
    }

    public BeatmapDifficultyData getDifficultyData() {
        return difficultyData;
    }

    public Path getDataPath() {
        return this.dataPath;
    }

}
