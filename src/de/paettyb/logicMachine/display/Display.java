package de.paettyb.logicMachine.display;

import de.paettyb.logicMachine.control.ScrollListener;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class Display extends JFrame {
    
    
    public static String NAME;
    public static int WIDTH, HEIGHT;
    
    private Canvas tableCanvas, kvCanvas;
    private Dimension tableDimension, kvDimension, scrollDimension;
    private JTextField textField;
    
    public Display(String name, int width, int height) {
        NAME = name;
        HEIGHT = height;
        WIDTH = width;
        createDisplay();
        showOnScreen(1, this, 100, 100);
    }
    
    private void createDisplay() {
        setTitle(NAME);
        Dimension dimension = new Dimension(WIDTH, HEIGHT);
        System.out.println(getLayout());
        setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
        setSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setBackground(Color.cyan);
        
        
        
        //setLocation(-1600,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        requestFocus();
        setVisible(true);
        
        
        
        
        
        scrollDimension = new Dimension(780, 700);
        tableDimension = new Dimension(750, 2000);
        kvDimension = new Dimension(400, 400);
        
        JScrollPane scrollPane = new JScrollPane();
        //scrollPane.setMaximumSize(scrollDimension);
        //scrollPane.setMinimumSize(scrollDimension);
        //scrollPane.setPreferredSize(scrollDimension);
        //scrollPane.setSize(scrollDimension);
        
        
        
        tableCanvas = new Canvas();
        
        tableCanvas.setMaximumSize(tableDimension);
        tableCanvas.setMinimumSize(tableDimension);
        tableCanvas.setPreferredSize(tableDimension);
        tableCanvas.setSize(tableDimension);
        tableCanvas.setBackground(Color.RED);
        
        scrollPane.add(tableCanvas);
        //scrollPane.createVerticalScrollBar();
        //scrollPane.getVerticalScrollBar().set
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new ScrollListener());
        add(scrollPane);
        
        kvCanvas = new Canvas();
        kvCanvas.setMaximumSize(kvDimension);
        kvCanvas.setMinimumSize(kvDimension);
        kvCanvas.setPreferredSize(kvDimension);
        kvCanvas.setSize(kvDimension);
        kvCanvas.setBackground(Color.BLUE);
        add(kvCanvas);
        kvCanvas.setLocation(new Point(800, 50));
    
        textField = new JTextField();
        textField.setVisible(true);
        textField.setForeground(Color.BLACK);
        textField.setSize(450, 50);
        textField.setLocation(WIDTH - 500, HEIGHT - 100);
        textField.setFont(new Font("Courir", Font.PLAIN, 20));
        add(textField);
        
        //setComponentZOrder(textField, 100);
        //pack();
    }
    
    public static void showOnScreen(int screen, JFrame frame, int x, int y) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        if (screen > -1 && screen < gd.length) {
            frame.setLocation(gd[screen].getDefaultConfiguration().getBounds().x + x, gd[screen].getDefaultConfiguration().getBounds().y + y);
        } else if (gd.length > 0) {
            frame.setLocation(gd[0].getDefaultConfiguration().getBounds().x + x, gd[0].getDefaultConfiguration().getBounds().y + y);
        } else {
            throw new RuntimeException("No Screens Found");
        }
    }
    
    
    public Canvas getTableCanvas() {
        return tableCanvas;
    }
    
    public Canvas getKVCanvas() {
        return kvCanvas;
    }
    
    public JTextField getTextField() {
        return textField;
    }
    
    public Dimension getTableDimension() {
        return tableDimension;
    }
    
    public void setTableDimension(Dimension tableDimension) {
        this.tableDimension = tableDimension;
    }
    
    public Dimension getKvDimension() {
        return kvDimension;
    }
    
    public void setKvDimension(Dimension kvDimension) {
        this.kvDimension = kvDimension;
    }
    
    public Dimension getScrollDimension() {
        return scrollDimension;
    }
    
    public void setScrollDimension(Dimension scrollDimension) {
        this.scrollDimension = scrollDimension;
    }
}
