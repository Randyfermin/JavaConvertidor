/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaconvertidor;

import java.awt.Font;

/**
 *
 * @author hp
 */
public class Utilidades {
    
    //esta funcion compara dos tipos de Strings y retorna una boolean
    public static boolean equalString(String sb1, String sb2) {
        boolean vReturn = true;
        int len = sb1.length();
        if (sb1.length() == sb2.length()) {
            for (int i = 0; i < len; i++) {
                if (sb1.charAt(i) != sb2.charAt(i)) {
                    vReturn = false;
                }
            }
        } else {
            vReturn = false;
        }
        return vReturn;
    }    
}
