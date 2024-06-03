package com.nictas.eclipselink.postgresql.gui;
import com.nictas.eclipselink.postgresql.entity.Problem;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import static javax.swing.SwingConstants.*;


public class MainFrame extends JFrame implements Serializable {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;
    StringBuilder output;

    Problem problem;

    public MainFrame(Problem problem, StringBuilder output) {
        super("Vehicle Routing Problem (VRP)");
        this.problem = problem;
        this.output = output;
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        configPanel = new ConfigPanel(this, problem, output);
        controlPanel = new ControlPanel(this);
        canvas = new DrawingPanel(this, problem, output);

        add(configPanel, BorderLayout.NORTH);
        add(canvas, CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        //invoke the layout manager
        pack();
    }

    public DrawingPanel getCanvas() {
        return canvas;
    }

    public void setCanvas(DrawingPanel canvas) {
        this.canvas = canvas;
    }

    public void saveAsImage(String fileName) {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        this.paint(g2d);
        try {
            File file = new File(fileName);
            ImageIO.write(image, "png", file);
            //System.out.println("Image saved as " + fileName);
            output.append("Image saved as ").append(fileName).append("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}