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
        String[] data = line.split(","); // 5 objects, params?, hitsample

        Point position = new Point(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
        long time = Long.parseLong(data[2]);
        HitObjectType type = HitObjectType.fromInt(Integer.parseInt(data[3]));
        HitSoundType hitSound = HitSoundType.fromId(Integer.parseInt(data[4]));
        HitObject obj;
        switch (type) {
            case CIRCLE -> obj = new Circle(position, Color.CYAN);
            default -> obj = new HitObject();
        }

        obj.setType(type);
        obj.setTime(time);
        obj.setHitSound(hitSound);
        obj.setPosition(position);

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
        if (this instanceof Circle) {
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

    @Override
    public String toString() {
        return "HitObject{" +
                "position=" + position +
                ", time=" + time +
                ", type=" + type +
                ", hitSound=" + hitSound +
                ", params='" + params + '\'' +
                ", hitSample='" + hitSample + '\'' +
                '}';
    }

    public enum HitObjectType {
        CIRCLE("CIRCLE", 1 << 0),
        SLIDER("SLIDER", 1 << 1),
        NEW_COMBO("NEW_COMBO", 1 << 2),
        SPINNER("SPINNER", 1 << 3),
        NEW_COMBO_4("NEW_COMBO_4", 1 << 4),
        NEW_COMBO_5("NEW_COMBO_5", 1 << 5),
        NEW_COMBO_6("NEW_COMBO_6", 1 << 6),
        MANIA_HOLD("MANIA_HOLD", 1 << 7);

        private String label;
        public int bitIdx;

        private HitObjectType(String label, int bitIdx) {
            this.label = label;
            this.bitIdx = bitIdx;
        }

        public static HitObjectType fromInt(int i) {
            for (HitObjectType type : HitObjectType.values()) {
                if ((i & type.bitIdx) != 0) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No HitObjectType with id " + i);
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
