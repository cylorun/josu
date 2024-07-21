package com.cylorun.game.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Circle extends HitObject {
    private int diameter;
    private Color color;
    public Point point;
    private boolean clicked = false;
    private boolean isAlive = true;
    private int approachLevel = APPROACH_RATE_LEVELS;
    private long circleDeathTime = -1;
    private long circleClickTime = -1;
    private JComponent parent;
    private Runnable onRemove;

    private static final int APPROACH_RATE = 1;
    private static final int APPROACH_RATE_LEVELS = 100;

    public Circle(int diameter, Point point, Color color) {
        this(diameter, point, color, null);
    }

    public Circle(int diameter, Point point, Color color, JComponent parent) {
        this.diameter = diameter;
        this.color = color;
        this.point = point;
        this.parent = parent;

        this.setPreferredSize(new Dimension(this.diameter + this.approachLevel, this.diameter + this.approachLevel));
        this.setBounds(point.x - this.approachLevel / 2, point.y - this.approachLevel / 2, this.diameter + this.approachLevel, this.diameter + this.approachLevel);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Circle.this.circleClickTime = System.currentTimeMillis();
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
        if (this.parent instanceof JPanel) {
            this.parent.remove(this);
            this.parent.revalidate();
            this.parent.repaint();
        }
    }

    private void onClick(MouseEvent e) {
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
        if (this.onRemove != null) {
            this.onRemove.run();
        }

    }

    private int getApproachCircleDiameter() {
        return this.approachLevel + this.diameter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (!this.isAlive) {
            return;
        }

        g2d.setColor(this.color);
        g2d.fillOval(this.point.x, this.point.y, this.diameter, this.diameter);

        int arDiameter = this.getApproachCircleDiameter();

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawOval(this.point.x - (this.approachLevel / 2), this.point.y - (this.approachLevel / 2), arDiameter, arDiameter);
    }
}
