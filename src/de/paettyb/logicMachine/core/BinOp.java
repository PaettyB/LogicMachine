package de.paettyb.logicMachine.core;

public abstract class BinOp extends Operator {
    
    protected Klausel k1, k2;
    
    protected Klausel parent;
    
    public BinOp(Klausel k1, Klausel k2) {
        this.k1 = k1;
        this.k2 = k2;
    }
    
    public BinOp(Klausel k1, Klausel k2, Klausel parent) {
        this.parent = parent;
        this.k1 = k1;
        this.k2 = k2;
    }
    
    @Override
    public String toString() {
        return "(" + k1.toString() + " " + getClass().getSimpleName().toUpperCase() + " " + k2.toString() + ")";
    }
    
    public Klausel getK1() {
        return k1;
    }
    
    public void setK1(Klausel k1) {
        this.k1 = k1;
    }
    
    public Klausel getK2() {
        return k2;
    }
    
    public void setK2(Klausel k2) {
        this.k2 = k2;
    }
    
    public Klausel getParent() {
        return parent;
    }
    
    public void setParent(Klausel parent) {
        this.parent = parent;
    }
}
