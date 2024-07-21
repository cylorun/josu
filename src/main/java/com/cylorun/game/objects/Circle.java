package com.cylorun.game.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Circle extends HitObject {
    private int diameter = 100;
    private Color color;
    private boolean clicked = false;
    private boolean isAlive = true;
    private int approachLevel = APPROACH_RATE_LEVELS;
    private long circleDeathTime = -1;
    private long circleClickTime = -1;
    private JComponent parent;
    private Runnable onRemove;

    private static final int APPROACH_RATE = 5;
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
        g2d.fillOval(this.getPosition().x, this.getPosition().y, this.diameter, this.diameter);

        int arDiameter = this.getApproachCircleDiameter();

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawOval(this.getPosition().x - (this.approachLevel / 2), this.getPosition().y - (this.approachLevel / 2), arDiameter, arDiameter);
    }
}
