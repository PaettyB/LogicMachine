package de.paettyb.logicMachine.core;

import java.util.HashMap;

public class UnOp extends Operator{
    
    protected Klausel klausel;
    
    public UnOp(Klausel klausel){
        this.klausel = klausel;
    }
    
    public Klausel getKlausel() {
        return klausel;
    }
    
    public void setKlausel(Klausel klausel) {
        this.klausel = klausel;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName().toUpperCase() + "("+ klausel +")";
    }
    
    @Override
    public boolean eval(HashMap<String, Boolean> besetzung) {
        return !klausel.eval(besetzung);
    }
}
