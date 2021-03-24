package de.paettyb.logicMachine.core;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Klausel {
    
    public static HashMap<Klausel, Integer> positions = new HashMap<>();
    
    public abstract boolean eval(HashMap<String, Boolean> besetzung);
    
    public String[] getLiterals(){
        ArrayList<String> list = new ArrayList();
        this.getLiteralsRecursive(list);
        List<String> sortedList = list.stream().distinct().sorted().collect(Collectors.toList());
        return sortedList.toArray(new String[sortedList.size()]);
    }
    
    private void getLiteralsRecursive(ArrayList<String> list){
        if(this instanceof Literal){
            list.add(((Literal) this).getName());
            return;
        }
        if(this instanceof UnOp){
            ((UnOp) this).klausel.getLiteralsRecursive(list);
            return;
        }
        if(this instanceof BinOp){
            ((BinOp) this).k1.getLiteralsRecursive(list);
            ((BinOp) this).k2.getLiteralsRecursive(list);
            return;
        }
        throw new RuntimeException("Encountered unknown token");
    }

}
