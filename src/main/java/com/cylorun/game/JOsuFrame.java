package com.cylorun.game;

import com.cylorun.JOsu;
import com.cylorun.game.objects.Circle;
import com.cylorun.util.ConversionUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        this.add(this.gamePanel, BorderLayout.CENTER);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addCircle(Circle circle) {
        if (this.circles.contains(circle)) {
            return;
        }
        this.gamePanel.add(circle);
        circle.setLocation(ConversionUtil.translateOsuCoordinate(circle.getPosition().x), ConversionUtil.translateOsuCoordinate(circle.getPosition().y));
        circle.onDeath((result) -> {

            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
//                this.gamePanel.remove(miss);
                this.circles.remove(circle);
            }, 2, TimeUnit.SECONDS);

        });

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
