package com.cylorun.game;

import com.cylorun.JOsu;
import com.cylorun.game.objects.Circle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JOsuFrame extends JFrame {

    private List<Circle> circles;

    public final JPanel gamePanel;
    private static JOsuFrame instance;

    public JOsuFrame() {
        super("JOsu! v" + JOsu.VERSION);
        this.setVisible(true);
        this.setSize(new Dimension(600, 450));
        this.setLayout(new BorderLayout());

        this.circles = new ArrayList<>();

        this.gamePanel = new JPanel(null);
        this.gamePanel.setBackground(Color.RED);
        this.add(this.gamePanel, BorderLayout.CENTER);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addCircle(Circle circle) {
        this.gamePanel.add(circle);
        circle.setLocation(translateOsuCoordinate(circle.getPosition().x), translateOsuCoordinate(circle.getPosition().y));
        this.circles.add(circle);
        this.repaint();
        this.revalidate();
    }

    public static int translateOsuCoordinate(int coord) {
        return (int) (coord * 2);
    }

    public List<Circle> getCircles() {
        return this.circles;
    }

    public static JOsuFrame getInstance() {
        if (instance == null) {
            instance = new JOsuFrame();
        }

        return instance;
    }
}
