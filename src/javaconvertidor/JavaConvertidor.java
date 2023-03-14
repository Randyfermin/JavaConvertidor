/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaconvertidor;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author hp
 */
public class JavaConvertidor {
    public static String vLookFeel;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            vLookFeel = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
            UIManager.setLookAndFeel(vLookFeel);
            ConvertidorJFrame w = new ConvertidorJFrame();
            SwingUtilities.updateComponentTreeUI(w);
            w.pack();
            w.setVisible(true); 
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JavaConvertidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
