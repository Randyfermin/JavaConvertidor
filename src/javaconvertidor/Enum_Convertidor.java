/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package javaconvertidor;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hp
 */
public enum Enum_Convertidor {
        
    MON("MONEDA", 1),
    LONG("LONGITUD", 2),
    MASA("MASA", 3),
    CAP("CAPACIDAD", 4),
    SUP("SUPERFICIE", 5),
    VOL("VOLUMEN", 6);
    
    private static final Map<String, Enum_Convertidor> BY_LABEL = new HashMap<>();
    private static final Map<Integer, Enum_Convertidor> BY_INDEX_NUMBER = new HashMap<>();
    
    static {
        for (Enum_Convertidor e : values()) {
            BY_LABEL.put(e.label, e);
            BY_INDEX_NUMBER.put(e.indexNumber, e);
        }
    }

    public final String label;
    public final int indexNumber;

    private Enum_Convertidor(String label, int indexNumber) {
        this.label = label;
        this.indexNumber = indexNumber;
    }

    public static Enum_Convertidor valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static Enum_Convertidor valueOfIndexNumber(int number) {
        return BY_INDEX_NUMBER.get(number);
    }
    
}
