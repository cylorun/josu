package com.cylorun.game.objects;

import com.cylorun.JOsu;
import com.cylorun.beatmap.Beatmap;
import com.cylorun.util.ConversionUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Circle extends HitObject {
    private int diameter;
    private Color color;
    private boolean clicked = false;
    private boolean isAlive = true;
    private long circleDeathTime = -1;
    private long circleClickTime = -1;
    private double approachRate;
    private boolean isDisplayingInfo = false;
    private JComponent parent;
    private long mapMsIn = 0; // milliseconds into the map

    public Circle(Point point, Color color) {
        this(point, color, null);
    }

    public Circle(Point point, Color color, JComponent parent) {
        Beatmap currMap = JOsu.getInstance().getCurrentMap();
        this.approachRate = currMap.getDifficultyData().getApproachRate();
        this.diameter = ConversionUtil.getCircleRadius(currMap.getDifficultyData().getCircleSize()) * 2;
        this.color = new Color(new Random().nextInt(0, 254), new Random().nextInt(0, 254), new Random().nextInt(0, 254));
        this.parent = parent;
        this.setPosition(point);
        this.setPreferredSize(new Dimension((int) (this.diameter * 2.5), (int) (this.diameter * 2.5)));
        this.setBounds(this.getPosition().x - this.diameter / 2, this.getPosition().y - this.diameter / 2, 550, 550);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Circle.this.onClick(e);
                Circle.this.disappear();

                System.out.println("click");
            }
        });
    }

    public synchronized void tick(long mapMsIn) {
        this.mapMsIn = mapMsIn;
        this.tickApproachRate();

        if (this.isAlive) {
            this.repaint();
        }
    }

    public void disappear() {
//        if (this.parent instanceof JPanel) {
//            this.parent.remove(this);
//            this.parent.revalidate();
//            this.parent.repaint();
//        }
        this.isDisplayingInfo = true;
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            this.isDisplayingInfo = false;
        }, 2, TimeUnit.SECONDS);
        if (this.onDeath != null) {
            this.onDeath.accept(this.getResult());
        }
    }

    private HitResult getResult() {
        if (this.isAlive) { // circle is still alive
            return HitResult.NONE;
        }

        if (!clicked) {
            return HitResult.MISS;
        }

        return HitResult.PERFECT;
    }

    private void onClick(MouseEvent e) {
        this.circleClickTime = System.currentTimeMillis();
        this.clicked = true;
    }

    private void tickApproachRate() {
        if ((this.getApproachCircleDiameter() == this.diameter) && this.isAlive) {
            System.out.println("death");
            this.circleDeathTime = System.currentTimeMillis();
            this.onApproachEnd();
        }
    }

    private void onApproachEnd() {
        this.isAlive = false;
        if (!this.clicked) {
            System.out.println("miss");
        } else {
            System.out.println((this.circleDeathTime - this.circleClickTime) + "ms");
        }

        this.disappear();
    }

    private int getApproachCircleDiameter() {
        int totalArMs = ConversionUtil.getARms(this.approachRate);
        int maxArDiameter = this.diameter * 2;

        long msLeft = this.getTime() - (long) totalArMs;
        msLeft = Math.max(0, Math.min(msLeft, totalArMs));

        int arDiameter = (int) ((1 - ((double) msLeft / totalArMs)) * maxArDiameter);

        return this.diameter + arDiameter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (!this.isAlive && !this.isDisplayingInfo) {
            return;
        }
//        if (!this.isAlive || this.isDisplayingInfo) {
//            g2d.setColor(this.getResult().equals(HitResult.PERFECT) ? Color.GREEN : Color.RED);
//            g2d.setFont(new Font("Arial", Font.BOLD, 20));
//            g2d.drawString(this.getResult().toString(), this.getPosition().x, this.getPosition().y);
//            return;
//        }

        g2d.setColor(this.color);
        g2d.fillOval(this.getPosition().x, this.getPosition().y, this.diameter, this.diameter);

        int arDiameter = this.getApproachCircleDiameter();

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawOval(this.getPosition().x, this.getPosition().y, arDiameter, arDiameter);
    }
}
