/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Superficie;

import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 *
 * @author hp
 */
public enum Enum_Superficie {
    
    //Superficie
    KM2("kilometro cuadrado", 1, 1000000),
    HM2("hectometro cuadrado", 2, 10000),
    DAM2("decametro cuadrado", 3, 100),
    M2("metro cuadrado", 4, 1),
    DM2("decimetro cuadrado", 5, 0.01),
    CM2("centimetro cuadrado", 6, 0.0001),
    MM2("milimetro cuadrado", 7, 0.000001);
    
    private static final Map<String, Enum_Superficie> BY_LABEL = new HashMap<>();
    private static final Map<Integer, Enum_Superficie> BY_ATOMIC_NUMBER = new HashMap<>();
    private static final Map<Double, Enum_Superficie> BY_ATOMIC_WEIGHT = new HashMap<>();
    
    private final static Map<String, String> map = new TreeMap<>();
    
    static {
        for (Enum_Superficie e : values()) {
            BY_LABEL.put(e.label, e);
            BY_ATOMIC_NUMBER.put(e.atomicNumber, e);
            BY_ATOMIC_WEIGHT.put(e.atomicWeight, e);
            map.put(e.label, e.name());
        }
    }

    public final String label;
    public final int atomicNumber;
    public final double atomicWeight;

    private Enum_Superficie(String label, int atomicNumber, double atomicWeight) {
        this.label = label;
        this.atomicNumber = atomicNumber;
        this.atomicWeight = atomicWeight;
    }

    public static Enum_Superficie valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static Enum_Superficie valueOfAtomicNumber(int number) {
        return BY_ATOMIC_NUMBER.get(number);
    }

    public static Enum_Superficie valueOfAtomicWeight(double weight) {
        return BY_ATOMIC_WEIGHT.get(weight);
    }
    public static Map comboBoxValues(){
        return map;
    }
    
    public static void ConvertirSuperficie(String medidaBase, double vTotal, String medidaTarget){
                      
            JPanel panel = new JPanel(new GridLayout(3, 2));
            Font mainFont = new Font("Arial", Font.BOLD, 18);
            //JLabel tasaCambioTitle = new JLabel("TASA DE CAMBIO ACTUAL: 1.00");
            JLabel comboBoxBaseTitle = new JLabel();
            JLabel comboBoxTargetTitle = new JLabel();
            JLabel customTypeFieldTitle = new JLabel();
        
            //font y Size set  
            comboBoxBaseTitle.setFont(mainFont);
            comboBoxTargetTitle.setFont(mainFont);
            customTypeFieldTitle.setFont(mainFont);
            
            double valorBase, valorTarget;
            Enum_Superficie enumValorBase = Enum_Superficie.valueOf(Enum_Superficie.class, comboBoxValues().get(medidaBase).toString());
            valorBase = enumValorBase.atomicWeight;
            
            Enum_Superficie enumValorTarget = Enum_Superficie.valueOf(Enum_Superficie.class, comboBoxValues().get(medidaTarget).toString());
            valorTarget = enumValorTarget.atomicWeight;
            
            
                customTypeFieldTitle.setText((vTotal ) + " " + comboBoxValues().get(medidaBase).toString().toUpperCase() + "(" + medidaBase.toUpperCase() + ")" );
            panel.add(customTypeFieldTitle);
                comboBoxBaseTitle.setText("IGUAL A: ");
            panel.add(comboBoxBaseTitle);
                comboBoxTargetTitle.setText((vTotal * valorBase * (1/valorTarget)) + " " + comboBoxValues().get(medidaTarget).toString().toUpperCase() + "(" + medidaTarget.toUpperCase() + ")" );
            panel.add(comboBoxTargetTitle);
            
                        JOptionPane.showMessageDialog(null, panel, "SUPERFICIE: RESULTADO OBTENIDO",
        JOptionPane.INFORMATION_MESSAGE);
    }
}
