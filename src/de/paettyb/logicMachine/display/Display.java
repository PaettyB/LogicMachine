package de.paettyb.logicMachine.display;

import java.awt.*;
import java.util.prefs.Preferences;

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
        showOnScreen(1, this, 300,200);
    }
    
    private void createDisplay() {
        setTitle(NAME);
        setSize(WIDTH, HEIGHT);
        
        //setLocation(-1600,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        requestFocus();
        setVisible(true);
    
        textField = new JTextField();
        textField.setVisible(true);
        textField.setForeground(Color.BLACK);
        
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
    
    public static void showOnScreen( int screen, JFrame frame, int x, int y ) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        if( screen > -1 && screen < gd.length ) {
            frame.setLocation(gd[screen].getDefaultConfiguration().getBounds().x+x, gd[screen].getDefaultConfiguration().getBounds().y+y);
        } else if( gd.length > 0 ) {
            frame.setLocation(gd[0].getDefaultConfiguration().getBounds().x+x, gd[0].getDefaultConfiguration().getBounds().y+y);
        } else {
            throw new RuntimeException( "No Screens Found" );
        }
    }
    
    
    public Canvas getCanvas() {
        return canvas;
    }
    
    public JTextField getTextField() {
        return textField;
    }
}
