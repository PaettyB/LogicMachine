package de.paettyb.logicMachine.core.operators;

import de.paettyb.logicMachine.core.BinOp;
import de.paettyb.logicMachine.core.Klausel;

import java.util.HashMap;

public class Gdw extends BinOp {
    public Gdw(Klausel k1, Klausel k2) {
        super(k1, k2);
    }
    
    @Override
    public boolean eval(HashMap<String, Boolean> besetzung) {
        boolean b1 = k1.eval(besetzung);
        boolean b2 = k2.eval(besetzung);
        if ((!b1 && !b2) || (b1 && b2)) return true;
        return false;
    }
    
    @Override
    public String toString() {
        if (SPECIAL_PRINT_MODE)
            return "(" + k1.toString() + " \u2194 " + k2.toString() + ")";
        return super.toString();
    }
}
