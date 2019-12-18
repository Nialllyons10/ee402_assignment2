import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.*;
import javax.swing.*;

public class Application extends JPanel{
	
    private int thePadding = 30;
    private int thelabelPadding = 30;
    private Color lineColor = new Color(56, 176, 150, 210);
    private Color pointColor = new Color(255, 255, 255, 255);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 5;
    private int numberYDivisions = 10;
    private List<Double> scores;

    public Application(List<Double> scores) {
        this.scores = scores;
    }
  
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * thePadding) - thelabelPadding) / (scores.size() - 1);
        double yScale = ((double) getHeight() - 2 * thePadding - thelabelPadding) / (getMaxScore() - getMinScore());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            int x1 = (int) (i * xScale + thePadding + thelabelPadding);
            int y1 = (int) ((getMaxScore() - scores.get(i)) * yScale + thePadding);
            graphPoints.add(new Point(x1, y1));
        }

        // this will a draw white background
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(thePadding + thelabelPadding, thePadding, getWidth() - (2 * thePadding) - thelabelPadding, getHeight() - 2 * thePadding - thelabelPadding);
        g2.setColor(Color.BLACK);

        // create marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = thePadding + thelabelPadding;
            int x1 = pointWidth + thePadding + thelabelPadding;
            int y0 = getHeight() - ((i * (getHeight() - thePadding * 2 - thelabelPadding)) / numberYDivisions + thePadding + thelabelPadding);
            int y1 = y0;
            if (scores.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(thePadding + thelabelPadding + 1 + pointWidth, y0, getWidth() - thePadding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < scores.size(); i++) {
            if (scores.size() > 1) {
                int x0 = i * (getWidth() - thePadding * 2 - thelabelPadding) / (scores.size() - 1) + thePadding + thelabelPadding;
                int x1 = x0;
                int y0 = getHeight() - thePadding - thelabelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - thePadding - thelabelPadding - 1 - pointWidth, x1, thePadding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes 
        g2.drawLine(thePadding + thelabelPadding, getHeight() - thePadding - thelabelPadding, thePadding + thelabelPadding, thePadding);
        g2.drawLine(thePadding + thelabelPadding, getHeight() - thePadding - thelabelPadding, getWidth() - thePadding, getHeight() - thePadding - thelabelPadding);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }
    
    static void createAndShowGui(List<Double> scores) {
    	
    	Application mainPanel = new Application(scores);
    	mainPanel.setPreferredSize(new Dimension(900, 400));
    	JFrame frame = new JFrame("Raspberry Pi Temperature Graph");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.getContentPane().add(mainPanel);
    	frame.pack();
    	frame.setLocationRelativeTo(null);
    	frame.setVisible(true);
    }

    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        for (Double score : scores) {
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }

    private double getMaxScore() {
        double maxScore = Double.MIN_VALUE;
        for (Double score : scores) {
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }

    public void setScores(List<Double> scores) {
        this.scores = scores;
        invalidate();
        this.repaint();
    }

    public List<Double> getScores() {
        return scores;
    }

}

