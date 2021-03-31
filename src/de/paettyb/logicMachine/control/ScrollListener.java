package de.paettyb.logicMachine.control;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ScrollListener implements AdjustmentListener {
    
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        System.out.println(e.getAdjustable());
    }
}
