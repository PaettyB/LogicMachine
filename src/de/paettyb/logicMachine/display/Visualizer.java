package de.paettyb.logicMachine.display;

import de.paettyb.logicMachine.core.Klausel;
import de.paettyb.logicMachine.core.truthTable.KVDiagramm;
import de.paettyb.logicMachine.core.truthTable.TruthTable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class Visualizer {
    
    public static int FONT_SIZE = 30;
    public static int MINIMUM_CANVAS_HEIGHT = 678;
    public static int PADDING = 10;
    private Point tableOrigin;
    
    private Klausel klausel;
    private TruthTable truthTable;
    private KVDiagramm kvDiagramm;
    
    private int highlightedIndex = -1;
    private int highlightedX = -1;
    
    private int newCanvasHeight = 700;
    
    private boolean[] values;
    
    private Graphics2D g;
    
    public Visualizer(Klausel klausel) {
        if (klausel != null) {
            this.klausel = klausel;
            truthTable = new TruthTable(klausel);
            tableOrigin = new Point(truthTable.getNumLiterals() * 30 + PADDING, 50);
            kvDiagramm = new KVDiagramm(klausel.getLiterals(), truthTable.getValues(), 50, new Point(50, 50));
            values = truthTable.getValues();
            
        }
    }
    
    public void updateTable(Graphics2D g) {
        this.g = g;
        
        String str = klausel.toString();
        
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
        
        //---HIGHLIGHT---
        g.setColor(Color.CYAN);
        
        if (highlightedIndex >= 0) {
            g.fillRect(highlightedX, tableOrigin.y - PADDING - fm.getAscent(), fm.charWidth(str.charAt(highlightedIndex)), fm.getHeight());
        } else {
            int topIndex;
            if ((topIndex = getTopLevelIndex()) > -1) {
                highlightedX = tableOrigin.x + PADDING + g.getFontMetrics().stringWidth(klausel.toString().substring(0, topIndex));
            } else {
                highlightedX = tableOrigin.x + PADDING;
            }
        }
        g.setColor(Color.black);
        int valuesX = (highlightedX == -1) ? tableOrigin.x + PADDING : highlightedX;
        for (int i = 0; i < values.length; i++) {
            
            g.drawString(values[i] ? "1" : "0", valuesX, fm.getAscent() + tableOrigin.y + i * fm.getHeight());
        }
        
        
        g.drawString(str, tableOrigin.x + PADDING, tableOrigin.y - PADDING);
        
        g.drawLine(tableOrigin.x, 0, tableOrigin.x, newCanvasHeight);
        g.drawLine(0, tableOrigin.y, Display.WIDTH - 500, tableOrigin.y);
        
        
    }
    
    public void updateKV(Graphics2D g) {
        g.setFont(new Font("Courir", Font.BOLD, FONT_SIZE));
        kvDiagramm.draw(g);
    }
    
    public void recalculateValues(Klausel k) {
        if (k != null) {
            this.klausel = k;
            truthTable = new TruthTable(klausel);
            kvDiagramm = new KVDiagramm(klausel.getLiterals(), truthTable.getValues(), 50, new Point(50, 50));
            tableOrigin = new Point(truthTable.getNumLiterals() * 30 + PADDING, 50);
            values = truthTable.getValues();
            highlightedIndex = -1;
            newCanvasHeight = (int) (g.getFontMetrics().getHeight() * Math.pow(2, klausel.getLiterals().length) + 50);
            if (newCanvasHeight < MINIMUM_CANVAS_HEIGHT)
                newCanvasHeight = MINIMUM_CANVAS_HEIGHT;
        }
    }
    
    public void clickEvent(MouseEvent e) {
        Point point = getOffsetToText(e.getPoint());
        if (isInTextBounds(point)) {
            int index = getTextIndex(point);
            highlightIndex(index);
            kvDiagramm.setValues(values);
            return;
        }
        highlightIndex(-1);
        kvDiagramm.setValues(values);
        
    }
    
    private void highlightIndex(int index) {
        highlightedIndex = index;
        if (index == -1) {
            values = truthTable.getValues();
            int topIndex;
            if ((topIndex = getTopLevelIndex()) > -1) {
                highlightedX = tableOrigin.x + PADDING + g.getFontMetrics().stringWidth(klausel.toString().substring(0, topIndex));
            } else {
                highlightedX = tableOrigin.x + PADDING;
            }
            return;
        }
        if (truthTable.getOperatorIndices().containsKey(index)) {
            TruthTable newTable = new TruthTable(truthTable.getOperatorIndices().get(index), klausel.getLiterals());
            values = newTable.getValues();
            highlightedX = tableOrigin.x + PADDING + g.getFontMetrics().stringWidth(klausel.toString().substring(0, highlightedIndex));
        } else {
            highlightedIndex = -1;
            values = truthTable.getValues();
            int topIndex = getTopLevelIndex();
            if (topIndex > -1) {
                highlightedX = tableOrigin.x + PADDING + g.getFontMetrics().stringWidth(klausel.toString().substring(0, topIndex));
            } else {
            
            }
            return;
        }
    }
    
    private int getTopLevelIndex() {
        if (truthTable.getOperatorIndices().containsValue(klausel)) {
            return truthTable.getOperatorIndices().entrySet().stream()
                    .filter(entry -> Objects.equals(entry.getValue(), klausel))
                    .findFirst().get().getKey();
        }
        return -1;
    }
    
    private Point getOffsetToText(Point mouse) {
        Point textOrigin = new Point(tableOrigin.x + PADDING, tableOrigin.y - PADDING);
        return new Point(mouse.x - textOrigin.x, mouse.y - textOrigin.y);
    }
    
    private boolean isInTextBounds(Point p) {
        FontMetrics fontMetrics = g.getFontMetrics();
        return (p.x >= 0 && p.x < fontMetrics.stringWidth(klausel.toString()) && p.y <= 0 && p.y > -fontMetrics.getAscent());
    }
    
    private int getTextIndex(Point p) {
        FontMetrics fontMetrics = g.getFontMetrics();
        String s = klausel.toString();
        for (int i = 0; i < s.length(); i++) {
            if (p.x < fontMetrics.stringWidth(s.substring(0, i + 1))) {
                return i;
            }
        }
        return -1;
    }
    
    public int getNewCanvasHeight() {
        return newCanvasHeight;
    }
}
