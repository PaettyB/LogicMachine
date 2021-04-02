package de.paettyb.logicMachine.control;

import de.paettyb.logicMachine.Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseManager implements MouseListener {
    
    private Main main;
    
    public MouseManager(Main main) {
        this.main = main;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        main.getVisualizer().clickEvent(e);
        main.getDisplay().updateComponents();
        main.renderKV();
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    
    }
}
