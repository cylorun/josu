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
        this.setResizable(false);
        this.setSize(new Dimension(400, 400));
        this.setLayout(new BorderLayout());

        this.circles = new ArrayList<>();

        this.gamePanel = new JPanel();
        this.add(this.gamePanel, BorderLayout.CENTER);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addCircle(Circle circle) {
        this.gamePanel.add(circle);
        this.circles.add(circle);

        this.repaint();
        this.revalidate();
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