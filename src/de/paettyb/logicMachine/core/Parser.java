package de.paettyb.logicMachine.core;

import de.paettyb.logicMachine.core.operators.*;

import java.util.LinkedList;

public class Parser {
    
    private static Klausel lastBinOp = null;
    
    public static LinkedList<Operator> operators = new LinkedList<>();
    private static int currentOpId = 0;
    
    private static Klausel parseStringRekursive(String s) {
        try {
            s = s.trim();
            s = boxString(s);
            int openBrackets = 0;
            int currentoperatorIndex = 0;
            
            
            if (s.startsWith("(not") || s.startsWith("not")) {
                int notIndex = s.indexOf("not");
                String arg = s.substring(notIndex + 3, s.length() - 1);
                Klausel klausel = parseStringRekursive(arg);
                if (klausel == null) return null;
                return new Not(klausel);
            }
            
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(') openBrackets++;
                else if (c == ')') {
                    openBrackets--;
                }
                if (openBrackets == 1 && i != 0) {
                    currentoperatorIndex = i + 1;
                    break;
                }
            }
            
            if (currentoperatorIndex == 0) {
                s = s.replaceAll("\\(", "");
                s = s.replaceAll("\\)", "");
                s = s.strip();
                if (s.equals("")) return null;
                return new Literal(s);
            }
            
            String opString = s.substring(currentoperatorIndex, currentoperatorIndex + 4);
            opString.replaceAll("\\(", "");
            opString = opString.strip();
            opString = opString.replaceAll("or .", "or");
            Operator operator = getOperatorFromString(opString);
            if (operator instanceof BinOp) {
                
                String arg1 = s.substring(1, currentoperatorIndex);
                String arg2 = s.substring(currentoperatorIndex + opString.length() + 1, s.length() - 1);
                Klausel klausel1 = parseStringRekursive(arg1);
                Klausel klausel2 = parseStringRekursive(arg2);
                if (klausel1 == null || klausel2 == null) return null;
                
                
                BinOp binOp = (BinOp) operator;
                if (klausel1 instanceof BinOp) {
                    ((BinOp) klausel1).setParent(binOp);
                }
                if (klausel2 instanceof BinOp) {
                    ((BinOp) klausel2).setParent(binOp);
                }
                binOp.setK1(klausel1);
                binOp.setK2(klausel2);
                
                
                return binOp;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    
    private static String boxString(String s) {
        int openBrackets = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') openBrackets++;
            else if (c == ')') {
                openBrackets--;
            }
            if (openBrackets == 0 && i != s.length() - 1) {
                s = "(" + s + ")";
                return s;
            }
        }
        return s;
    }
    
    public static Klausel parseString(String s) {
        s = s.replaceAll(" +", " ");
        s = s.replaceAll("\\(\s", "(");
        s = s.replaceAll("\s\\)", ")");
        s = s.toLowerCase();
        s = boxString(s);
        return parseStringRekursive(s);
    }
    
    
    public static Operator getOperatorFromString(String s) {
        switch (s) {
            case "and":
                return new And(null, null);
            case "or":
                return new Or(null, null);
            case "xor":
                return new Xor(null, null);
            case "imp":
                return new Imp(null, null);
            case "gdw":
                return new Gdw(null, null);
            case "not":
                return new Not(null);
            default:
                return null;
        }
    }
    
    public static void main(String[] args) {
        String s = "B and (not A)";
        Klausel klausel = parseString(s);
        System.out.println(klausel.toString());
    }
}
