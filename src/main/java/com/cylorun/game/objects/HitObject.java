package com.cylorun.game.objects;

import javax.swing.*;
import java.awt.*;

public class HitObject extends JComponent {
    private Point position;
    private long time;
    private HitObjectType type;
    private HitSoundType hitSound;
    private String params;
    private String hitSample;

    // Hit object syntax: x,y,time,type,hitSound,objectParams,hitSample

    public HitObject() {

    }

    public static HitObject fromLine(String line) throws IndexOutOfBoundsException, IllegalArgumentException, NumberFormatException {
        HitObject obj = new HitObject();
        String[] data = line.split(","); // 5 objects, params?, hitsample

        obj.setPosition(new Point(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
        obj.setTime(Long.parseLong(data[2]));
        obj.setType(HitObjectType.fromId(Integer.parseInt(data[3])));
        obj.setHitSound(HitSoundType.fromId(Integer.parseInt(data[4])));

        return obj;
    }

    public void waitForAppearance(long startMs) {
        while ((System.currentTimeMillis() - startMs) < this.time) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Circle getAsCircle() {
        if (this.type.equals(HitObjectType.CIRCLE)) {
            return (Circle) this;
        }

        throw new IllegalStateException("This is not a circle object: " + this);
    }


    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public HitObjectType getType() {
        return type;
    }

    public void setType(HitObjectType type) {
        this.type = type;
    }

    public HitSoundType getHitSound() {
        return hitSound;
    }

    public void setHitSound(HitSoundType hitSound) {
        this.hitSound = hitSound;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getHitSample() {
        return hitSample;
    }

    public void setHitSample(String hitSample) {
        this.hitSample = hitSample;
    }

    public enum HitObjectType {
        CIRCLE("CIRCLE", 0),
        SLIDER("SLIDER", 1),
        NEW_COMBO("NEW_COMBO", 2),
        SPINNER("SPINNER", 3),
        NEW_COMBO_4("NEW_COMBO_4", 4),
        NEW_COMBO_5("NEW_COMBO_5", 5),
        NEW_COMBO_6("NEW_COMBO_6", 6);

        private String label;
        private int value;

        private HitObjectType(String label, int value) {
            this.label = label;
            this.value = value;
        }

        public static HitObjectType fromId(int id) {
            for (HitObjectType type : HitObjectType.values()) {
                if (type.value == id) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No HitObjectType with id " + id);
        }

        @Override
        public String toString() {
            return this.label;
        }
    }

    public enum HitSoundType {
        NORMAL("NORMAL", 0),
        WHISTLE("WHISTLE", 1),
        FINISH("FINISH", 2),
        CLAP("CLAP", 3);

        private String label;
        private int value;

        private HitSoundType(String label, int value) {
            this.label = label;
            this.value = value;
        }

        public static HitSoundType fromId(int id) {
            for (HitSoundType type : HitSoundType.values()) {
                if (type.value == id) {
                    return type;
                }
            }

            throw new IllegalArgumentException("No HitSoundType with id " + id);
        }

        @Override
        public String toString() {
            return this.label;
        }
    }
}
