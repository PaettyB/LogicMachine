package de.paettyb.logicMachine.core.operators;

import de.paettyb.logicMachine.core.BinOp;
import de.paettyb.logicMachine.core.Klausel;

import java.util.HashMap;

public class And extends BinOp {
    
    public And(Klausel k1, Klausel k2) {
        super(k1, k2);
    }
    
    public And(Klausel[] klausels, int index, Klausel parent) {
        super(null, null);
        if (index >= klausels.length - 2) {
            if (klausels[index] instanceof BinOp)
                ((BinOp) klausels[index]).setParent(this);
            if (klausels[index + 1] instanceof BinOp)
                ((BinOp) klausels[index + 1]).setParent(this);
            setK1(klausels[index]);
            setK2(klausels[index + 1]);
        } else {
            if (klausels[index] instanceof BinOp)
                ((BinOp) klausels[index]).setParent(this);
            setK1(klausels[index]);
            setK2(new And(klausels, index + 1, this));
        }
        
        this.parent = parent;
    }
    
    public boolean eval(HashMap<String, Boolean> besetzung) {
        boolean b1 = k1.eval(besetzung);
        boolean b2 = k2.eval(besetzung);
        return (b1 && b2);
    }
    
    @Override
    public String toString() {
        if (SPECIAL_PRINT_MODE) {
            if (parent instanceof And || parent == null) {
                return k1.toString() + " \u2227 " + k2.toString();
            }
            return "(" + k1.toString() + " \u2227 " + k2.toString() + ")";
        }
        return super.toString();
    }
}
