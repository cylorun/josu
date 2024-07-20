package com.cylorun.beatmap.data;

import com.cylorun.beatmap.Beatmap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class BeatmapMetadata extends BeatmapDataParser<BeatmapMetadata>{
    private String title;
    private String titleUnicode;
    private String artist;
    private String artistUnicode;
    private String creator;
    private String version;
    private String source;
    private String tags;
    private Long beatmapID;
    private Long beatmapSetID;

    private static String SECTION_IDENTIFIER = "[Metadata]";


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleUnicode() {
        return titleUnicode;
    }

    public void setTitleUnicode(String titleUnicode) {
        this.titleUnicode = titleUnicode;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtistUnicode() {
        return artistUnicode;
    }

    public void setArtistUnicode(String artistUnicode) {
        this.artistUnicode = artistUnicode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getBeatmapID() {
        return beatmapID;
    }

    public void setBeatmapID(long beatmapID) {
        this.beatmapID = beatmapID;
    }

    public Long getBeatmapSetID() {
        return beatmapSetID;
    }

    public void setBeatmapSetID(long beatmapSetID) {
        this.beatmapSetID = beatmapSetID;
    }

    @Override
    public String toString() {
        return "BeatmapMetadata{" +
                "title='" + title + '\'' +
                ", titleUnicode='" + titleUnicode + '\'' +
                ", artist='" + artist + '\'' +
                ", artistUnicode='" + artistUnicode + '\'' +
                ", creator='" + creator + '\'' +
                ", version='" + version + '\'' +
                ", source='" + source + '\'' +
                ", tags='" + tags + '\'' +
                ", beatmapId=" + beatmapID +
                ", beatmapSetId=" + beatmapSetID +
                '}';
    }

    @Override
    protected String getDataSectionIdentifier() {
        return SECTION_IDENTIFIER;
    }

    public static BeatmapMetadata from(Beatmap map) {
        return BeatmapDataParser.from(map, BeatmapMetadata::new, SECTION_IDENTIFIER);
    }
}
