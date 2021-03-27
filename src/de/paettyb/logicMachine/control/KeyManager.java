package de.paettyb.logicMachine.control;

import de.paettyb.logicMachine.Main;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
    
    private Main main;
    
    public KeyManager(Main main){
        this.main = main;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        JTextField textField = main.getDisplay().getTextField();
        if(e.getKeyChar() == '('){
            int pos = textField.getCaretPosition();
            textField.setText(textField.getText()+")");
            textField.setCaretPosition(pos);
        }
        main.recalculateValues();
    }
}
