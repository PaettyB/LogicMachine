package de.paettyb.logicMachine.core.operators;

import de.paettyb.logicMachine.core.Klausel;
import de.paettyb.logicMachine.core.Literal;
import de.paettyb.logicMachine.core.UnOp;

public class Not extends UnOp {
    
    
    public Not(Klausel klausel) {
        super(klausel);
    }
    
    @Override
    public String toString() {
        if (SPECIAL_PRINT_MODE) {
            if (klausel instanceof Literal)
                return "\u00AC" + klausel;
            return "\u00AC(" + klausel + ")";
        }
        return super.toString();
    }
}
