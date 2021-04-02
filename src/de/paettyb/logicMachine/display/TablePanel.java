package de.paettyb.logicMachine.display;

import de.paettyb.logicMachine.Main;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel {
    
    private Main main;
    
    public TablePanel (Main main){
        this.main = main;
    }
    
    @Override
    public void paintComponent(Graphics g){
        main.renderTable(g);
    }
}
