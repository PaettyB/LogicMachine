package de.paettyb.logicMachine.control;

import de.paettyb.logicMachine.Main;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ScrollListener implements AdjustmentListener {
    
    private Main main;
    
    public ScrollListener(Main main){
        this.main = main;
    }
    
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        //main.getDisplay().updateComponents();
        main.getDisplay().getTableCanvas().repaint();
        main.render();
    }
}
