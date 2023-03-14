/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Volumen;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;
/**
 *
 * @author hp
 */
public enum Enum_Volumen {
    
    //Volumen
    KM3("kilometro cúbico", 1, 1000000000),
    HM3("hectometro cúbico", 2, 1000000),
    DAM3("decametro cúbico", 3, 1000),
    M3("metro cúbico", 4, 1),
    DM3("decimetro cúbico", 5, 0.001),
    CM3("centimetro cúbico", 6, 0.000001),
    MM3("milimetro cúbico", 7, 0.000000001);
    
    private static final Map<String, Enum_Volumen> BY_LABEL = new HashMap<>();
    private static final Map<Integer, Enum_Volumen> BY_ATOMIC_NUMBER = new HashMap<>();
    private static final Map<Double, Enum_Volumen> BY_ATOMIC_WEIGHT = new HashMap<>();
    
    private final static Map<String, String> map = new TreeMap<>();
    
    static {
        for (Enum_Volumen e : values()) {
            BY_LABEL.put(e.label, e);
            BY_ATOMIC_NUMBER.put(e.atomicNumber, e);
            BY_ATOMIC_WEIGHT.put(e.atomicWeight, e);
            map.put(e.label, e.name());
        }
    }

    public final String label;
    public final int atomicNumber;
    public final double atomicWeight;

    private Enum_Volumen(String label, int atomicNumber, double atomicWeight) {
        this.label = label;
        this.atomicNumber = atomicNumber;
        this.atomicWeight = atomicWeight;
    }

    public static Enum_Volumen valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static Enum_Volumen valueOfAtomicNumber(int number) {
        return BY_ATOMIC_NUMBER.get(number);
    }

    public static Enum_Volumen valueOfAtomicWeight(double weight) {
        return BY_ATOMIC_WEIGHT.get(weight);
    }
    public static Map comboBoxValues(){
        return map;
    }
    
    public static void ConvertirVolumen(String medidaBase, double vTotal, String medidaTarget){
            DecimalFormat twoPlaces = new DecimalFormat("0.00");
            double valorBase, valorTarget;
            Enum_Volumen enumValorBase = Enum_Volumen.valueOf(Enum_Volumen.class, comboBoxValues().get(medidaBase).toString());
            valorBase = enumValorBase.atomicWeight;
            
            Enum_Volumen enumValorTarget = Enum_Volumen.valueOf(Enum_Volumen.class, comboBoxValues().get(medidaTarget).toString());
            valorTarget = enumValorTarget.atomicWeight;
            
            JOptionPane.showMessageDialog(null, "Total :" + twoPlaces.format(vTotal * valorBase * (1/valorTarget)) + " " + medidaTarget, "MASA",
        JOptionPane.INFORMATION_MESSAGE);
    }
}
