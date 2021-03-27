package de.paettyb.logicMachine.display;

import java.awt.*;

import javax.swing.*;

public class Display extends JFrame{
    
    
    public static String NAME;
    public static int WIDTH,HEIGHT;
    
    private Canvas canvas;
    private JTextField textField;
    
    public Display(String name, int width, int height) {
        this.NAME = name;
        this.HEIGHT = height;
        this.WIDTH = width;
        createDisplay();
    }
    
    private void createDisplay() {
        setTitle(NAME);
        setSize(WIDTH, HEIGHT);
        
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        requestFocus();
        setVisible(true);
    
        textField = new JTextField();
        textField.setVisible(true);
        textField.setSize(450, 50);
        textField.setLocation(WIDTH-500, HEIGHT-100);
        textField.setFont(new Font("Courir", Font.PLAIN, 20));
        add(textField);
        
        canvas = new Canvas();
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setBackground(Color.lightGray);
        add(canvas);
        
        
        pack();
    }
    
    
    public Canvas getCanvas() {
        return canvas;
    }
    
    public JTextField getTextField() {
        return textField;
    }
}
