package com.cylorun.game.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Circle extends HitObject {
    private int diameter = 150;
    private Color color;
    private boolean clicked = false;
    private boolean isAlive = true;
    private int approachLevel = APPROACH_RATE_LEVELS;
    private long circleDeathTime = -1;
    private long circleClickTime = -1;

    private boolean isDisplayingInfo = false;
    private JComponent parent;

    private static final int APPROACH_RATE = 20;
    private static final int APPROACH_RATE_LEVELS = 100;

    public Circle(Point point, Color color) {
        this(point, color, null);
    }

    public Circle(Point point, Color color, JComponent parent) {
        this.color = color;
        this.parent = parent;
        this.setPosition(point);
        this.setPreferredSize(new Dimension(this.diameter + this.approachLevel, this.diameter + this.approachLevel));
        this.setBounds(this.getPosition().x - this.approachLevel / 2, this.getPosition().y - this.approachLevel / 2, 550, 550);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Circle.this.onClick(e);
                Circle.this.disappear();

                System.out.println("click");
            }
        });
    }

    public void tick() {
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
        if (this.approachLevel > 0) {
            this.approachLevel -= APPROACH_RATE;
        } else {
            if (this.isAlive) {
                this.circleDeathTime = System.currentTimeMillis();
                this.onApproachEnd();
            }
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
        return this.approachLevel + this.diameter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (!this.isAlive && !this.isDisplayingInfo) {
            return;
        }
        if (!this.isAlive || this.isDisplayingInfo) {
            g2d.setColor(this.getResult().equals(HitResult.PERFECT) ? Color.GREEN : Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString(this.getResult().toString(), this.getPosition().x, this.getPosition().y);
            return;
        }

        g2d.setColor(this.color);
        g2d.fillOval(this.getPosition().x, this.getPosition().y, this.diameter, this.diameter);

        int arDiameter = this.getApproachCircleDiameter();

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawOval(this.getPosition().x - (this.approachLevel / 2), this.getPosition().y - (this.approachLevel / 2), arDiameter, arDiameter);
    }
}
