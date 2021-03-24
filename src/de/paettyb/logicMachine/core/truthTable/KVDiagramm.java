package de.paettyb.logicMachine.core.truthTable;

import de.paettyb.logicMachine.display.Visualizer;

import java.awt.*;

public class KVDiagramm {
    
    private String[] literals;
    private int size;
    private Point pos;
    private boolean[] values;
    
    public KVDiagramm(String[] literals, boolean[] values, int size, Point pos) {
        this.literals = literals;
        this.values = values;
        this.size = size;
        this.pos = pos;
    }
    
    
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        
        int[][] indices = {};
        if (literals.length == 1) {
            g.drawRect(pos.x, pos.y, size, size);
            g.drawRect(pos.x, pos.y, size * 2, size);
            
            g.drawString(literals[0], pos.x + size * 1.25f, pos.y - Visualizer.PADDING);
            
            
            indices = new int[][]{{0}, {1}};
            
        } else if (literals.length == 2) {
            g.drawRect(pos.x, pos.y, size * 2, size * 2);
            g.drawLine(pos.x + size, pos.y, pos.x + size, pos.y + size * 2);
            g.drawLine(pos.x, pos.y + size, pos.x + size * 2, pos.y + size);
            
            g.drawString(literals[0], pos.x + size * 1.25f, pos.y - Visualizer.PADDING);
            g.drawString(literals[1], pos.x - fm.stringWidth(literals[1]) - Visualizer.PADDING, (pos.y + size + fm.getHeight()));
            
            indices = new int[][]{{0, 1}, {2, 3}};
        } else if (literals.length == 3) {
            g.drawRect(pos.x, pos.y, size * 4, size * 2);
            g.drawRect(pos.x + size, pos.y, size * 2, size * 2);
            g.drawLine(pos.x + size * 2, pos.y, pos.x + size * 2, pos.y + size * 2);
            g.drawLine(pos.x, pos.y + size, pos.x + size * 4, pos.y + size);
            
            g.drawString(literals[0], pos.x + size * 2 - fm.stringWidth(literals[0]) * 0.5f, pos.y - Visualizer.PADDING);
            g.drawString(literals[1], pos.x - fm.stringWidth(literals[1]) - Visualizer.PADDING, (pos.y + size + fm.getHeight()));
            g.drawString(literals[2], pos.x + size * 3 - fm.stringWidth(literals[0]) * 0.5f, pos.y + size * 2 + fm.getAscent());
            
            indices = new int[][]{{0, 2}, {4, 6}, {5, 7}, {1, 3}};
        } else if (literals.length == 4) {
            g.drawRect(pos.x, pos.y, size * 4, size * 4);
            g.drawRect(pos.x + size, pos.y, size * 2, size * 4);
            g.drawRect(pos.x, pos.y + size, size * 4, size * 2);
            g.drawLine(pos.x + size * 2, pos.y, pos.x + size * 2, pos.y + size * 4);
            g.drawLine(pos.x, pos.y + size * 2, pos.x + size * 4, pos.y + size * 2);
            
            g.drawString(literals[0], pos.x + size * 2 - fm.stringWidth(literals[0]) * 0.5f, pos.y - Visualizer.PADDING);
            g.drawString(literals[1], pos.x - fm.stringWidth(literals[1]) - Visualizer.PADDING, (pos.y + size * 2 + fm.getAscent() * 0.5f));
            g.drawString(literals[2], pos.x + size * 3 - fm.stringWidth(literals[0]) * 0.5f, pos.y + size * 4 + fm.getAscent());
            g.drawString(literals[3], pos.x + size * 4 + Visualizer.PADDING, (pos.y + size * 3 + fm.getAscent() * 0.5f));
            
            indices = new int[][]{{0, 4, 5, 1}, {8, 12, 13, 9}, {10, 14, 15, 11}, {2, 6, 7, 3}};
        }
        
        g.setColor(Color.GREEN);
        for (int i = 0; i < indices.length; i++) {
            for (int j = 0; j < indices[i].length; j++) {
                boolean val = values[indices[i][j]];
                if (val)
                    g.fillRect((pos.x + 1 )+ i * size, (pos.y + 1) + j * size, size - 1, size-1);
            }
        }
    }
    
    
    
    
    
    /*       A
            ---
            |0|
            ---
               A
            |-----|
          B |0 | 2|
            |1 | 3|
            |-----|
            
                   A
            |-------------|
          B |0 | 4 | 5 | 1|
            |2 | 6 | 7 | 3|
            |-------------|
                       C
                   A
            |-------------|
            |0 | 8 | 10| 2|
          B |4 | 12| 14| 6|
            |5 | 13| 15| 7| D
            |1 | 9 | 11| 3|
            |-------------|
                       C
     */
    
    
    public static void print(String[] literals, boolean[] values) {
        StringBuilder stringBuilder = new StringBuilder();
        if (literals.length == 1) {
            stringBuilder.append(" A \n");
            stringBuilder.append("---\n");
            stringBuilder.append("|" + values[0] + "|\n");
            stringBuilder.append("---");
        } else if (literals.length == 2) {
            stringBuilder.append("     A\n");
            stringBuilder.append("  |-----|\n");
            stringBuilder.append("B |" + values[0] + " | " + values[2] + "|\n");
            stringBuilder.append("  |" + values[1] + " | " + values[3] + "|\n");
            stringBuilder.append("  |-----|");
        } else if (literals.length == 3) {
            stringBuilder.append("         A\n");
            stringBuilder.append("  |-------------|\n");
            stringBuilder.append("B |" + values[0] + " | " + values[4] + " | " + values[5] + " | " + values[1] + "|\n");
            stringBuilder.append("  |" + values[2] + " | " + values[6] + " | " + values[7] + " | " + values[3] + "|\n");
            stringBuilder.append("  |-------------|\n");
            stringBuilder.append("             C");
        } else if (literals.length == 4) {
            stringBuilder.append("         A\n");
            stringBuilder.append("  |-------------|\n");
            stringBuilder.append("B |" + values[0] + " | " + values[8] + " | " + values[10] + " | " + values[2] + "|\n");
            stringBuilder.append("  |" + values[4] + " | " + values[12] + " | " + values[14] + " | " + values[6] + "|\n");
            stringBuilder.append("  |" + values[5] + " | " + values[13] + " | " + values[15] + " | " + values[7] + "| D\n");
            stringBuilder.append("  |" + values[1] + " | " + values[9] + " | " + values[11] + " | " + values[3] + "|\n");
            stringBuilder.append("  |-------------|\n");
            stringBuilder.append("             C");
        }
        String s = stringBuilder.toString();
        s = s.replaceAll("true", "1");
        s = s.replaceAll("false", "0");
        System.out.println(s);
    }
    
    
}
