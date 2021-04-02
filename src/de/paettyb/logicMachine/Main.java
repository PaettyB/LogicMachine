package de.paettyb.logicMachine;

import de.paettyb.logicMachine.control.KeyManager;
import de.paettyb.logicMachine.control.MouseManager;
import de.paettyb.logicMachine.control.ScrollListener;
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
        visualizer = new Visualizer(k);
        display = new Display(this, name, width, height);
        display.getTextField().addKeyListener(new KeyManager(this));
        display.getScrollPane().addMouseListener(new MouseManager(this));
        display.getScrollPane().getVerticalScrollBar().addAdjustmentListener(new ScrollListener(this));
        render();
    }
    
    public void render() {
        renderKV();
    }
    
    public void recalculateValues() {
        String s = display.getTextField().getText();
        Klausel k = Parser.parseString(s);
        if (k == null) {
            display.getTextField().setBorder(BorderFactory.createLineBorder(new Color(255, 77, 77), 3));
        } else {
            display.getTextField().setBorder(BorderFactory.createLineBorder(new Color(21, 118, 0), 3));
        }
        visualizer.recalculateValues(k);
        display.setTableDimension(new Dimension(display.getTableDimension().width, visualizer.getNewCanvasHeight()));
        display.updateComponents();
        render();
    }
    
    private void tick() {
    
    }
    
    public synchronized void renderKV() {
        bs = display.getKVCanvas().getBufferStrategy();
        if (bs == null) {
            display.getKVCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        
        // draw
        visualizer.updateKV((Graphics2D) g);
        // end draw
        
        g.dispose();
        bs.show();
    }
    
    public synchronized void renderTable(Graphics g) {
        g.clearRect(0, 0, display.getTableDimension().width, display.getTableDimension().height);
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, display.getTableDimension().width, display.getTableDimension().height);
        visualizer.updateTable((Graphics2D) g);
    }
    
    public synchronized void start() {
        if (running)
            return;
        running = true;
       /* run = new Thread("MainEngineThread") {
            public void run() {
                while (running) {
                    //tick();
                    render();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //System.exit(0);
            }
        };
        run.start();*/
        
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
