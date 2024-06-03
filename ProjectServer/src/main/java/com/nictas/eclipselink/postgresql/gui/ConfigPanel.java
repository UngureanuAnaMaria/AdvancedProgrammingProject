package com.nictas.eclipselink.postgresql.gui;
import com.nictas.eclipselink.postgresql.entity.Problem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class ConfigPanel extends JPanel implements Serializable {
    final MainFrame frame;
    JLabel label;
    JSpinner client;
    JButton newGameBtn;
    StringBuilder output;

    Problem problem;
    public ConfigPanel(MainFrame frame, Problem problem, StringBuilder output) {
        this.frame = frame;
        this.problem = problem;
        this.output = output;
        init();
    }
    private void init() {
        //create the label and the spinner
        label = new JLabel("Client: ");
        client = new JSpinner(new SpinnerNumberModel(0,0, problem.getClients().length - 1, 1));
        newGameBtn = new JButton("Create");

        newGameBtn.addActionListener(this::createGame);

        add(label); //JPanel uses FlowLayout by default
        add(client);
        add(newGameBtn);
    }

    private void createGame(ActionEvent e) {
        int rows = problem.getM();
        int cols = problem.getN();
        if (frame.getCanvas() != null) {
            frame.remove(frame.getCanvas());
        }

        DrawingPanel newCanvas = new DrawingPanel(frame, rows, cols, problem, output);
        frame.setCanvas(newCanvas);

        frame.add(newCanvas, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();


    }

    public int getRows() {
        return problem.getM();
    }

    public int getCols() {
        return problem.getN();
    }

    public int getClient() {
        return (int) client.getValue();
    }
}