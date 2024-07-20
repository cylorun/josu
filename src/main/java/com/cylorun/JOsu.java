package com.cylorun;

import com.cylorun.game.JOsuFrame;
import com.cylorun.game.objects.Circle;

import java.awt.*;
import java.util.List;

public class JOsu {
    public static final String VERSION = "0.0.1";

    public static void main(String[] args) {
        JOsuFrame f = JOsuFrame.getInstance();
        Circle c1 = new Circle(150, new Point(50, 50), Color.BLUE, f.gamePanel);
        Circle c2 = new Circle(150, new Point(80, 50), Color.RED, f.gamePanel);

        f.addCircle(c1);
        f.addCircle(c2);

        long lastns = System.nanoTime();
        while (true) {
            if (System.nanoTime() - lastns > 1_500_000_0 ) {
                lastns = System.nanoTime();
                List<Circle> circles = JOsuFrame.getInstance().getCircles();
                for (Circle c : circles) {
                    c.tick();
                }
            }
        }
    }
}