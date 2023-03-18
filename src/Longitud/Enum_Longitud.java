/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Longitud;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public enum Enum_Longitud {
    //Metro
    KM("kilometro", 1, 1000),
    HM("hectometro", 2, 100),
    DAM("decametro", 3, 10),
    M("metro", 4, 1),
    DM("decimetro", 5, 0.1),
    CM("centimetro", 6, 0.01),
    MM("milimetro", 7, 0.001);
    
    private static final Map<String, Enum_Longitud> BY_LABEL = new HashMap<>();
    private static final Map<Integer, Enum_Longitud> BY_ATOMIC_NUMBER = new HashMap<>();
    private static final Map<Double, Enum_Longitud> BY_ATOMIC_WEIGHT = new HashMap<>();
    
    private final static Map<String, String> map = new TreeMap<>();
    
    static {
        for (Enum_Longitud e : values()) {
            BY_LABEL.put(e.label, e);
            BY_ATOMIC_NUMBER.put(e.atomicNumber, e);
            BY_ATOMIC_WEIGHT.put(e.atomicWeight, e);
            map.put(e.label, e.name());
        }
    }

    public final String label;
    public final int atomicNumber;
    public final double atomicWeight;

    private Enum_Longitud(String label, int atomicNumber, double atomicWeight) {
        this.label = label;
        this.atomicNumber = atomicNumber;
        this.atomicWeight = atomicWeight;
    }

    public static Enum_Longitud valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static Enum_Longitud valueOfAtomicNumber(int number) {
        return BY_ATOMIC_NUMBER.get(number);
    }

    public static Enum_Longitud valueOfAtomicWeight(double weight) {
        return BY_ATOMIC_WEIGHT.get(weight);
    }
    public static Map comboBoxValues(){
        return map;
    }
    
    public static void ConvertirLongitud(String medidaBase, double vTotal, String medidaTarget){
            DecimalFormat twoPlaces = new DecimalFormat("0.00");
            double valorBase, valorTarget;
            Enum_Longitud enumValorBase = Enum_Longitud.valueOf(Enum_Longitud.class, comboBoxValues().get(medidaBase).toString());
            valorBase = enumValorBase.atomicWeight;
            
            Enum_Longitud enumValorTarget = Enum_Longitud.valueOf(Enum_Longitud.class, comboBoxValues().get(medidaTarget).toString());
            valorTarget = enumValorTarget.atomicWeight;
            
            JOptionPane.showMessageDialog(null, "Total :" + twoPlaces.format(vTotal * valorBase * (1/valorTarget)) + " " + medidaTarget, "LONGITUD",
        JOptionPane.INFORMATION_MESSAGE);
    }
}
