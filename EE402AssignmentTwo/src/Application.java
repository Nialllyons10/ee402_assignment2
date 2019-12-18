import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Application extends JPanel implements ActionListener{
	
    private int thePadding = 30;
    private int thelabelPadding = 30;
    private Color lineColor = new Color(56, 176, 150, 210);
    private Color pointColor = new Color(255, 255, 255, 255);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 5;
    private int numberYDivisions = 10;
    private List<Double> temps;
    private static JButton button1, button2, button3;
    private static JTextField minTemp, maxTemp, avTemp;

    public Application(List<Double> temps) {
        this.temps = temps;
    }
  
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * thePadding) - thelabelPadding) / (temps.size() - 1);
        double yScale = ((double) getHeight() - 2 * thePadding - thelabelPadding) / (getMaxTemp() - getMinTemp());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            int x1 = (int) (i * xScale + thePadding + thelabelPadding);
            int y1 = (int) ((getMaxTemp() - temps.get(i)) * yScale + thePadding);
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
            if (temps.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(thePadding + thelabelPadding + 1 + pointWidth, y0, getWidth() - thePadding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinTemp() + (getMaxTemp() - getMinTemp()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < temps.size(); i++) {
            if (temps.size() > 1) {
                int x0 = i * (getWidth() - thePadding * 2 - thelabelPadding) / (temps.size() - 1) + thePadding + thelabelPadding;
                int x1 = x0;
                int y0 = getHeight() - thePadding - thelabelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((temps.size() / 20.0)) + 1)) == 0) {
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
    
    static void showGui(List<Double> temps) {
    	
    	Application mainPanel = new Application(temps);
    	mainPanel.setPreferredSize(new Dimension(900, 400));
    	
    	maxTemp = new JTextField(10);
    	maxTemp.setEditable(false);
    	minTemp = new JTextField(10);
    	minTemp.setEditable(false);
    	avTemp = new JTextField(10);
    	avTemp.setEditable(false);
    	button1 = new JButton("Get Maximum Temperature");
    	button2 = new JButton("Get Minimum Temperature");
    	button3 = new JButton("Get Average Temperature");
    	
    	JFrame frame = new JFrame("Raspberry Pi Temperature Graph");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.getContentPane().add(mainPanel);
   
    	JPanel top = new JPanel();
    	top.setLayout(new GridLayout(3, 6));
    	button1.addActionListener(mainPanel);
        button2.addActionListener(mainPanel);
        button3.addActionListener(mainPanel);
    	top.add(button1);
    	top.add(maxTemp);
    	top.add(button2);
    	top.add(minTemp);
    	top.add(button3);
    	top.add(avTemp);
    	frame.add("North",top);
    	
    	frame.pack();
    	frame.setLocationRelativeTo(null);
    	frame.setVisible(true);
    }

    private double getMinTemp() {
        double minScore = Double.MAX_VALUE;
        for (Double score : temps) {
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }

    private double getMaxTemp() {
        double max = Double.MIN_VALUE;
        for (Double temp : temps) {
        	max = Math.max(max, temp);
        }
        return max;
    }

    public void setTemps(List<Double> temps) {
        this.temps = temps;
        invalidate();
        this.repaint();
    }

    public List<Double> getTemps() {
        return temps;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("Get Maximum Temperature")){
        	maxTemp.setText("Max Temp is 42°C");
        }
        else if (e.getActionCommand().equals("Get Minimum Temperature")){
        	minTemp.setText("Min Temp is 41°C");
        }
        else if (e.getActionCommand().equals("Get Average Temperature")){
        	avTemp.setText("Min Temp is 33°C");
        }
        else{
          System.out.print("No press");
        }
    
    }

}

