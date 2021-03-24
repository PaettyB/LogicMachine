package de.paettyb.logicMachine.core.truthTable;

import de.paettyb.logicMachine.core.Klausel;
import de.paettyb.logicMachine.core.Literal;
import de.paettyb.logicMachine.core.operators.And;
import de.paettyb.logicMachine.core.operators.Not;
import de.paettyb.logicMachine.core.operators.Or;

import java.util.ArrayList;
import java.util.HashMap;

public class TruthTable {
    
    private Klausel klausel;
    private int length;
    private String[] literals;
    private boolean[] values;
    private boolean[][] allocations;
    
    
    public TruthTable(Klausel klausel) {
        this.klausel = klausel;
        this.literals = klausel.getLiterals();
        length = (int) Math.pow(2, literals.length);
        values = new boolean[length];
        allocations = new boolean[length][literals.length];
        calculateValues();
    }
    
    private void calculateValues() {
        boolean[] currentAlloc = new boolean[literals.length];
        for (int i = 0; i < length; i++) {
            int mask = 1;
            for (int bit = 0; bit < literals.length; bit++) {
                if ((i & mask) == mask) {
                    currentAlloc[literals.length - 1 - bit] = true;
                } else {
                    currentAlloc[literals.length - 1 - bit] = false;
                }
                mask = mask << 1;
            }
            HashMap<String, Boolean> alloc = new HashMap<>();
            
            for (int v = 0; v < literals.length; v++) {
                alloc.put(literals[v], currentAlloc[v]);
                allocations[i][v] = currentAlloc[v];
            }
            values[i] = klausel.eval(alloc);
        }
    }
    
    public void printValues() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String l : literals) {
            stringBuilder.append(l + " ");
        }
        stringBuilder.append("| " + klausel + ": ");
        System.out.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        
        for (int i = 0; i < literals.length; i++) {
            stringBuilder.append("--");
        }
        stringBuilder.append("|");
        stringBuilder.append("--------------------");
        System.out.println(stringBuilder.toString());
        for (int i = 0; i < values.length; i++) {
            System.out.println(allocToString(allocations[i]) + "| " + (values[i] == true ? "1" : "0"));
        }
    }
    
    public static String allocToString(boolean[] alloc) {
        String s = "";
        for (boolean b : alloc) {
            s += (b) ? "1" : "0";
            s += " ";
        }
        return s;
    }
    
    public Klausel getDNF() {
        ArrayList<Klausel> orList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (values[i] == true) {
                Klausel[] ls = new Klausel[literals.length];
                for (int l = 0; l < literals.length; l++) {
                    if (allocations[i][l])
                        ls[l] = new Literal(literals[l]);
                    else
                        ls[l] = new Not(new Literal(literals[l]));
                }
                And and = new And(ls, 0, null);
                orList.add(and);
            }
        }
        Klausel[] orArr = new Klausel[orList.size()];
        Or or = new Or(orList.toArray(orArr), 0, null);
        return or;
    }
    
    public Klausel getKNF() {
        ArrayList<Klausel> andList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (values[i] == false) {
                Klausel[] ls = new Klausel[literals.length];
                for (int l = 0; l < literals.length; l++) {
                    if (!allocations[i][l])
                        ls[l] = new Literal(literals[l]);
                    else
                        ls[l] = new Not(new Literal(literals[l]));
                }
                Or or = new Or(ls, 0, null);
                andList.add(or);
            }
        }
        Klausel[] andArr = new Klausel[andList.size()];
        And and = new And(andList.toArray(andArr), 0, null);
        return and;
    }
    
    public int getNumLiterals() {
        return literals.length;
    }
    
    public void printKVDiagramm() {
        KVDiagramm.print(literals, values);
    }
    
    public Klausel getKlausel() {
        return klausel;
    }
    
    public void setKlausel(Klausel klausel) {
        this.klausel = klausel;
    }
    
    public boolean[] getValues() {
        return values;
    }
    
    public boolean[][] getAllocations() {
        return allocations;
    }
}
