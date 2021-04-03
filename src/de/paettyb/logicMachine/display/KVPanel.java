package de.paettyb.logicMachine.display;

import de.paettyb.logicMachine.Main;

import javax.swing.*;
import java.awt.*;

public class KVPanel extends JPanel {
    
    private Main main;
    
    public KVPanel(Main main) {
        this.main = main;
    }
    
    @Override
    public void paintComponent(Graphics g){
        main.renderKV(g);
    }
}
