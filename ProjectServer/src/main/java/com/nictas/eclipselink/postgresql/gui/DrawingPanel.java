package com.nictas.eclipselink.postgresql.gui;
import com.nictas.eclipselink.postgresql.entity.Problem;
import com.nictas.eclipselink.postgresql.domain.ClientJPA;
import com.nictas.eclipselink.postgresql.domain.VehicleJPA;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DrawingPanel extends JPanel implements Serializable {
    private final MainFrame frame;
    int rows, cols;
    int canvasWidth = 400, canvasHeight = 400;
    int boardWidth, boardHeight;
    int cellWidth, cellHeight;
    int padX, padY;
    int stoneSize = 20;
    StringBuilder output;

    Problem problem;
    public DrawingPanel(MainFrame frame, Problem problem, StringBuilder output) {
        this.frame = frame;
        this.problem = problem;
        this.output = output;
        init(frame.configPanel.getRows(), frame.configPanel.getCols());
    }

    public DrawingPanel(MainFrame frame, int rows, int cols, Problem problem, StringBuilder output) {
        this.frame = frame;
        this.rows = rows;
        this.cols = cols;
        this.problem = problem;
        this.output = output;
        init(rows, cols);
    }

    final void init(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.padX = stoneSize + 10;
        this.padY = stoneSize + 10;
        this.cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
        this.cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
        this.boardWidth = (cols - 1) * cellWidth;
        this.boardHeight = (rows - 1) * cellHeight;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        paintGrid(g);
        paintPaths(g);
    }

    private void paintGrid(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        for (int row = 0; row < rows; row++) {
            int x1 = padX;
            int y1 = padY + row * cellHeight;
            int x2 = padX + boardWidth;
            int y2 = y1;
            g.drawLine(x1, y1, x2, y2);
        }
        for (int col = 0; col < cols; col++) {
            int x1 = padX + col * cellWidth;
            int y1 = padY;
            int x2 = x1;
            int y2 = padY + boardHeight;
            g.drawLine(x1, y1, x2, y2);
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = padX + col * cellWidth;
                int y = padY + row * cellHeight;
                g.setColor(Color.LIGHT_GRAY);
                g.drawOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
            }
        }
    }

    private void paintPaths(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));

        Map<ClientJPA, VehicleJPA> allocation = problem.getAllocationProblem();

        ClientJPA[] clients = problem.getClients();
        VehicleJPA[] vehicles = problem.getVehicles();

        List<ClientJPA> clientsList = Arrays.asList(clients);
        List<VehicleJPA> vehicleList = Arrays.asList(vehicles);

        int clientIndexGrid = frame.configPanel.getClient();
        for (Map.Entry<ClientJPA, VehicleJPA> entry : allocation.entrySet()) {
            ClientJPA client = entry.getKey();
            VehicleJPA vehicle = entry.getValue();

            int clientIndex = clientsList.indexOf(client);
            if(clientIndex == clientIndexGrid) {
                int vehicleIndex = vehicleList.indexOf(vehicle);
                System.out.println(problem.getClientsLocation(clientIndex) + " " + problem.getVehiclesLocation(vehicleIndex));
                drawShortestPath(g, problem.getClientsLocation(clientIndex), problem.getVehiclesLocation(vehicleIndex));
            }
        }
    }

    private void drawShortestPath(Graphics2D g, int start, int end) {
        List<Integer> path = problem.getPath(start, end);
        for (int i = 0; i < path.size() - 1; i++) {
            int current = path.get(i);
            int next = path.get(i + 1);

            int currentRow = current % rows;
            int currentCol = current / rows;
            int nextRow = next % rows;
            int nextCol = next / rows;

            int startX = padX + currentCol * cellWidth;
            int startY = padY + currentRow * cellHeight;
            int endX = padX + nextCol * cellWidth;
            int endY = padY + nextRow * cellHeight;

            g.drawLine(startX, startY, endX, endY);
        }
    }


}