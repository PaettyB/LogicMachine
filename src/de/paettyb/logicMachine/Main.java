package de.paettyb.logicMachine;

import de.paettyb.logicMachine.control.KeyManager;
import de.paettyb.logicMachine.control.MouseManager;
import de.paettyb.logicMachine.core.Klausel;
import de.paettyb.logicMachine.core.Parser;
import de.paettyb.logicMachine.display.Display;
import de.paettyb.logicMachine.display.Visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;


public class Main {
    
    private Display display;
    private Graphics g;
    private BufferStrategy bs;
    
    private boolean running = false;
    
    private Visualizer visualizer;

    public static Color BACKGROUND_COLOR = new Color(0x15356D);
    public static Color LIGHT_BLUE = new Color(0xCDE6F5);
    public static Color LIGHT_COLOR = new Color(0xFFFFFA);
    public static Color ACCENT_COLOR = new Color(0xFF9F1C);

    public Main(String name, int width, int height) {
        Klausel k = Parser.parseString("A and B");
        visualizer = new Visualizer(k);
        display = new Display(this, name, width, height);
        display.getTextField().addKeyListener(new KeyManager(this));
        display.getScrollPane().addMouseListener(new MouseManager(this));
    }
    
    public void recalculateValues() {
        String s = display.getTextField().getText();
        Klausel k = Parser.parseString(s);
        if (k == null) {
            display.getTextField().setBorder(BorderFactory.createLineBorder(new Color(255, 77, 77), 3));
        } else {
            display.getTextField().setBorder(BorderFactory.createLineBorder(new Color(35, 210, 0), 3));
        }
        visualizer.recalculateValues(k);
        display.setTableDimension(new Dimension(display.getTableDimension().width, visualizer.getNewCanvasHeight()));
        display.updateComponents();
    }
    
    public synchronized void renderKV(Graphics g) {
        g.clearRect(0, 0, display.getKvDimension().width, display.getKvDimension().height);
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, Display.WIDTH, Display.HEIGHT);
        visualizer.updateKV((Graphics2D) g);
    }
    
    public synchronized void renderTable(Graphics g) {
        g.clearRect(0, 0, Display.WIDTH, Display.HEIGHT);
        //g.clearRect(0, 0, display.getTableDimension().width, display.getTableDimension().height);
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, display.getTableDimension().width, display.getTableDimension().height);
        visualizer.updateTable((Graphics2D) g);
    }
    
    public synchronized void start() {
        if (running)
            return;
        running = true;
    }
    
    public synchronized void stopGame() {
        if (!running)
            return;
        running = false;
        display.dispose();
    }
    
    public Display getDisplay() {
        return display;
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public Visualizer getVisualizer() {
        return visualizer;
    }
    
    public static void main(String[] args) {
        Main main = new Main("LogicMachine", 1280, 720);
        main.start();
    }
}
