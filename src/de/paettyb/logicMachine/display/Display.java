package de.paettyb.logicMachine.display;

import de.paettyb.logicMachine.control.ScrollListener;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {
    
    
    public static String NAME;
    public static int WIDTH, HEIGHT;
    
    private Canvas tableCanvas, kvCanvas;
    private Dimension tableDimension, kvDimension, scrollDimension;
    private JTextField textField;
    private JScrollPane scrollPane;
    
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
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setBackground(Color.cyan);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        requestFocus();
        
        
        scrollDimension = new Dimension(750, 700);
        tableDimension = new Dimension(750, Visualizer.MINIMUM_CANVAS_HEIGHT);
        kvDimension = new Dimension(400, 400);
        
        
        tableCanvas = new Canvas();
        tableCanvas.setPreferredSize(tableDimension);
        tableCanvas.setMinimumSize(tableDimension);
        tableCanvas.setMaximumSize(tableDimension);
        tableCanvas.setBackground(Color.lightGray);
        JPanel scrollPanel = new JPanel();
        scrollPanel.add(tableCanvas);
        scrollPanel.setBackground(Color.magenta);
        
        scrollPane = new JScrollPane(scrollPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(scrollDimension);
        scrollPane.setForeground(Color.gray);
        add(scrollPane, BorderLayout.LINE_START);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        kvCanvas = new Canvas();
        kvCanvas.setMinimumSize(kvDimension);
        kvCanvas.setPreferredSize(kvDimension);
        
        textField = new JTextField();
        textField.setVisible(true);
        textField.setForeground(Color.BLACK);
        textField.setPreferredSize(new Dimension(500, 50));
        textField.setMaximumSize(new Dimension(500, 50));
        
        
        textField.setFont(new Font("Courir", Font.PLAIN, 20));
        rightPanel.add(kvCanvas);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        rightPanel.add(textField);
        add(rightPanel, BorderLayout.LINE_END);
        pack();
        setVisible(true);

    }
    
    public void updateComponents() {
        tableCanvas.setPreferredSize(tableDimension);
        tableCanvas.setMinimumSize(tableDimension);
        tableCanvas.setMaximumSize(tableDimension);
        scrollPane.validate();
        
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
    
    public JScrollPane getScrollPane() {
        return scrollPane;
    }
    
    
}
