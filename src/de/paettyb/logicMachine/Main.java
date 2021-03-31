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
    
    private final String name;
    private final int width;
    private final int height;
    
    private Display display;
    private Graphics g;
    private BufferStrategy bs;
    
    private Thread run;
    private boolean running = false;
    
    private Visualizer visualizer;
    
    public Main(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        
        init();
    }
    
    private void init() {
        Klausel k = Parser.parseString("A and B");
        display = new Display(name, width, height);
        display.getTextField().addKeyListener(new KeyManager(this));
        display.getCanvas().addMouseListener(new MouseManager(this));
        visualizer = new Visualizer(k);
        render();
    }
    
    public void recalculateValues() {
        String s = display.getTextField().getText();
        Klausel k = Parser.parseString(s);
        if(k == null) {
            display.getTextField().setBorder(BorderFactory.createLineBorder(new Color(255, 77, 77), 3));
        } else {
            display.getTextField().setBorder(BorderFactory.createLineBorder(new Color(21, 118, 0),3));
        }
        visualizer.recalculateValues(k);
        render();
    }
    
    private void tick() {
    
    }
    
    public synchronized void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        
        // draw
        visualizer.updateDisplay((Graphics2D) g);
        // end draw
        
        g.dispose();
        bs.show();
    }
    
    public synchronized void start() {
        if (running)
            return;
        running = true;
        /*run = new Thread("MainEngineThread") {
            public void run() {
                while(running){
                    //tick();
                    //render();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //System.exit(0);
            }
        };
        run.start();
        */
    }
    
    public synchronized void stopGame() {
        if (!running)
            return;
        running = false;
        display.dispose();
        //run.join();
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public Display getDisplay() {
        return display;
    }
    
    public Graphics getGraphics() {
        return g;
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
