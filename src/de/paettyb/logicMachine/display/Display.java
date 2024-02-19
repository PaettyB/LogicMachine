package de.paettyb.logicMachine.display;

import de.paettyb.logicMachine.Main;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {
    
    
    public static String NAME;
    public static int WIDTH, HEIGHT;
    
    private Dimension tableDimension, kvDimension, scrollDimension;
    private JTextField textField;
    private JScrollPane scrollPane;
    private TablePanel tablePanel;
    private KVPanel kvPanel;
    
    public Display(Main main, String name, int width, int height) {
        NAME = name;
        HEIGHT = height;
        WIDTH = width;
        createDisplay(main);
        showOnScreen(1, this, 100, 100);
    }
    
    private void createDisplay(Main main) {
        setTitle(NAME);
        Dimension dimension = new Dimension(WIDTH, HEIGHT);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setBackground(Color.cyan);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        scrollDimension = new Dimension(750, 700);
        tableDimension = new Dimension(750, Visualizer.MINIMUM_CANVAS_HEIGHT);
        kvDimension = new Dimension(400, 400);
        
        tablePanel = new TablePanel(main);
        tablePanel.setPreferredSize(tableDimension);
        tablePanel.setBackground(Color.cyan);
        
        scrollPane = new JScrollPane(tablePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(scrollDimension);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        add(scrollPane, BorderLayout.LINE_START);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.setBackground(Main.BACKGROUND_COLOR);
        
        kvPanel = new KVPanel(main);
        kvPanel.setPreferredSize(kvDimension);
        textField = new JTextField();
        textField.setVisible(true);
        textField.setForeground(Color.BLACK);
        textField.setPreferredSize(new Dimension(500, 50));
        textField.setMaximumSize(new Dimension(500, 50));
        textField.setFont(new Font("Courir", Font.PLAIN, 20));
        textField.setBackground(Main.LIGHT_BLUE);

        rightPanel.add(kvPanel);
        rightPanel.add(textField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        add(rightPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
        textField.requestFocus();
        
    }
    
    public void updateComponents() {
        tablePanel.setPreferredSize(tableDimension);
        scrollPane.getVerticalScrollBar().setValue(0);
        tablePanel.revalidate();
        kvPanel.repaint();
        tablePanel.repaint();
        
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
