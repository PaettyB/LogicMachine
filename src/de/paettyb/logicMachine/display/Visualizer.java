package de.paettyb.logicMachine.display;

import de.paettyb.logicMachine.core.Klausel;
import de.paettyb.logicMachine.core.truthTable.KVDiagramm;
import de.paettyb.logicMachine.core.truthTable.TruthTable;

import java.awt.*;

public class Visualizer {
    
    public static int FONT_SIZE = 30;
    public static int PADDING = 10;
    private Point tableOrigin;
    
    private Klausel klausel;
    private TruthTable truthTable;
    private KVDiagramm kvDiagramm;
    
    public Visualizer(Klausel klausel) {
        this.klausel = klausel;
        truthTable = new TruthTable(klausel);
        tableOrigin = new Point(truthTable.getNumLiterals() * 30 + PADDING, 50);
        kvDiagramm = new KVDiagramm(klausel.getLiterals(), truthTable.getValues(), 50, new Point(Display.WIDTH-400, 200));
    }
    
    public void updateDisplay(Graphics2D g) {
        
        g.setFont(new Font("Courir", Font.BOLD, FONT_SIZE));
        FontMetrics fm = g.getFontMetrics();
        
        g.setColor(Color.black);
        
        String[] literals = klausel.getLiterals();
        for (int i = 0; i < literals.length; i++) {
            g.drawString(literals[i], PADDING + i * 30, tableOrigin.y - PADDING);
        }
        
        boolean[][] allocations = truthTable.getAllocations();
        
        for (int a = 0; a < allocations.length; a++) {
            int y = fm.getAscent() + tableOrigin.y + a * fm.getHeight();
            for (int l = 0; l < literals.length; l++) {
                String s = (allocations[a][l]) ? "1" : "0";
                g.drawString(s, PADDING + l * 30, y);
            }
        }
        
        boolean[] values = truthTable.getValues();
        for (int i = 0; i < values.length; i++) {
            g.drawString(values[i] ? "1":"0", tableOrigin.x + PADDING, fm.getAscent() + tableOrigin.y + i * fm.getHeight());
        }
        
        g.drawString(klausel.toString(), tableOrigin.x + PADDING, tableOrigin.y - PADDING);
        
        g.drawLine(tableOrigin.x, 0, tableOrigin.x, Display.HEIGHT);
        g.drawLine(0, tableOrigin.y, Display.WIDTH - 500, tableOrigin.y);
        
        kvDiagramm.draw(g);
    }
}
