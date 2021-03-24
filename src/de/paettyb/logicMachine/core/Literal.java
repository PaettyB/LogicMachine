package de.paettyb.logicMachine.core;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class Literal  extends Klausel{
    
    private String name;
    
    public Literal(String name){
        this.name = name.toUpperCase();
    }
    
    public boolean eval(HashMap<String, Boolean> besetzung){
        if(!besetzung.containsKey(name)){
            if(name.equals("TRUE")) return true;
            else if(name.equals("FALSE")) return false;
            System.err.println("Literal name " + name + " not specified");
            throw new NoSuchElementException();
        }
        return besetzung.get(name);
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public String getName(){
        return name;
    }
    
    public boolean equals(Object other){
        if( !(other instanceof Literal)) return false;
        return ((Literal) other).getName().equals(name);
    }
}
