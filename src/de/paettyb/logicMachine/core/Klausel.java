package de.paettyb.logicMachine.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Klausel {
    
    public static HashMap<Klausel, Integer> positions = new HashMap<>();
    
    public abstract boolean eval(HashMap<String, Boolean> besetzung);
    
    
    public String[] getLiterals() {
        ArrayList<String> list = new ArrayList();
        this.getLiteralsRecursive(list);
        List<String> sortedList = list.stream().distinct().sorted()
                .filter(s -> (!s.equals("1") && !s.equals("0"))).collect(Collectors.toList());
        return sortedList.toArray(new String[sortedList.size()]);
    }
    
    private void getLiteralsRecursive(ArrayList<String> list) {
        if (this instanceof Literal) {
            list.add(((Literal) this).getName());
            return;
        }
        if (this instanceof UnOp) {
            ((UnOp) this).klausel.getLiteralsRecursive(list);
            return;
        }
        if (this instanceof BinOp) {
            ((BinOp) this).k1.getLiteralsRecursive(list);
            ((BinOp) this).k2.getLiteralsRecursive(list);
            return;
        }
        throw new RuntimeException("Encountered unknown token");
    }
    
    public LinkedList<Operator> getOperators() {
        LinkedList list = new LinkedList();
        getOperatorsRecursive(list, 0);
        return list;
    }
    
    public void getOperatorsRecursive(LinkedList<Operator> list, int index) {
        if (!(this instanceof Operator)) return;
        if (this instanceof UnOp) {
            list.add(index, (UnOp) this);
            ((UnOp) this).klausel.getOperatorsRecursive(list, index + 1);
            return;
        }
        if (this instanceof BinOp) {
            BinOp self = (BinOp) this;
            list.add(index, self);
            self.k2.getOperatorsRecursive(list, index + 1);
            self.k1.getOperatorsRecursive(list, index);
        }
        
    }
    
    public HashMap<Integer, Operator> getOperatorIndices() {
        HashMap<Integer, Operator> map = new HashMap<>();
        String s = toString();
        LinkedList<Operator> operators = getOperators();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\u2227' || c == '\u27F6' || c == '\u00AC' || c == '\u2228' || c == '\u2295' || c == '\u2194') {
                map.put(i, operators.pollFirst());
            }
        }
        return map;
    }
    
}
