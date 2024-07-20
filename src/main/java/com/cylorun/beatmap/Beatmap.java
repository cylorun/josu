package com.cylorun.beatmap;

import java.nio.file.Path;

public class Beatmap {

    private Path dataPath;
    public Beatmap(Path dataPath) {
        this.dataPath = dataPath;
    }

    public Path getDataPath() {
        return this.dataPath;
    }

}
