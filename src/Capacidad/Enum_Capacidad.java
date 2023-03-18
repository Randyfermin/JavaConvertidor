/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Capacidad;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;
/**
 *
 * @author hp
 */
public enum Enum_Capacidad {

    //Capacidad
    kl("kilolitro", 1, 1000),
    hl("hectolitro", 2, 100),
    dal("decalitro", 3, 10),
    l("litro", 4, 1),
    dl("decilitro", 5, 0.1),
    cl("centilitro", 6, 0.01),
    ml("mililitro", 7, 0.001);
    
    //almacenamos los tipos de datos en un objeto Map para poder acceder a ellos segun el dato requerido
    //el objeto map es una coleccion de datos para llenar los combobox
    private static final Map<String, Enum_Capacidad> BY_LABEL = new HashMap<>();
    private static final Map<Integer, Enum_Capacidad> BY_ATOMIC_NUMBER = new HashMap<>();
    private static final Map<Double, Enum_Capacidad> BY_ATOMIC_WEIGHT = new HashMap<>();
    private final static Map<String, String> map = new TreeMap<>();
    
    static {
        for (Enum_Capacidad e : values()) {
            BY_LABEL.put(e.label, e);
            BY_ATOMIC_NUMBER.put(e.atomicNumber, e);
            BY_ATOMIC_WEIGHT.put(e.atomicWeight, e);
            map.put(e.label, e.name());
        }
    }

    public final String label;
    public final int atomicNumber;
    public final double atomicWeight;

    private Enum_Capacidad(String label, int atomicNumber, double atomicWeight) {
        this.label = label;
        this.atomicNumber = atomicNumber;
        this.atomicWeight = atomicWeight;
    }

    public static Enum_Capacidad valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static Enum_Capacidad valueOfAtomicNumber(int number) {
        return BY_ATOMIC_NUMBER.get(number);
    }

    public static Enum_Capacidad valueOfAtomicWeight(double weight) {
        return BY_ATOMIC_WEIGHT.get(weight);
    }
    public static Map comboBoxValues(){
        return map;
    }
    
    public static void ConvertirCapacidad(String medidaBase, double vTotal, String medidaTarget){
            DecimalFormat twoPlaces = new DecimalFormat("0.00");
            double valorBase, valorTarget;
            Enum_Capacidad enumValorBase = Enum_Capacidad.valueOf(Enum_Capacidad.class, comboBoxValues().get(medidaBase).toString());
            valorBase = enumValorBase.atomicWeight;
            
            Enum_Capacidad enumValorTarget = Enum_Capacidad.valueOf(Enum_Capacidad.class, comboBoxValues().get(medidaTarget).toString());
            valorTarget = enumValorTarget.atomicWeight;
            
            JOptionPane.showMessageDialog(null, "Total :" + twoPlaces.format(vTotal * valorBase * (1/valorTarget)) + " " + medidaTarget, "CAPACIDAD",
        JOptionPane.INFORMATION_MESSAGE);
    }
    
}
