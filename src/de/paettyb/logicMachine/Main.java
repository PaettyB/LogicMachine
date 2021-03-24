package de.paettyb.logicMachine;

import de.paettyb.logicMachine.control.MouseManager;
import de.paettyb.logicMachine.core.Klausel;
import de.paettyb.logicMachine.core.Parser;
import de.paettyb.logicMachine.display.Display;
import de.paettyb.logicMachine.display.Visualizer;

import javax.net.ssl.KeyManager;
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
        String s = "NOT((A and B) or (C and not A))";
        Klausel k = Parser.parseString(s);
        
        display = new Display(name, width, height);
        //display.getFrame().addKeyListener(new KeyManager());
        display.getCanvas().addMouseListener(new MouseManager(this));
        visualizer = new Visualizer(k);
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
        run = new Thread("MainEngineThread") {
            public void run() {
                
                /*long lastTime = System.nanoTime();
                int fps = 5;
                double timePerTick = 1000000000 / fps;
                double delta = 0;
                long now;
                long timer = 0;
                long ticks = 0;
                
                while (running) {
                    now = System.nanoTime();
                    delta += (now - lastTime) / timePerTick;
                    timer += now - lastTime;
                    lastTime = now;
                    if (delta > 1) {
                        tick();
                        render();
                        ticks++;
                        delta--;
                    }
                    
                    if(timer >= 1000000000) {
                        ticks = 0;
                        timer = 0;
                    }
                }
                
                stopGame();
                System.exit(0);
                
                 */
                
                while(running){
                    tick();
                    render();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopGame();
                System.exit(0);
            }
            
            
        };
        
        run.start();
        
    }
    
    public synchronized void stopGame() {
        if (!running)
            return;
        running = false;
        try {
            run.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    
    public Visualizer getVisualizer() {
        return visualizer;
    }
    
    public static void main(String[] args) {
        Main main = new Main("LogicMachine", 1280, 720);
        main.start();
    }
}
