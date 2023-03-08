/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package javaconvertidor;

/**
 *
 * @author hp
 */
public enum NewEnum_Test {
    
    MONEDA("MONEDA", 1),
    LONGITUD("LONGITUD", 2),
    MASA("MASA", 3),
    CAPACIDAD("CAPACIDAD", 4),
    SUPERFICIE("SUPERFICIE", 5),
    VOLUMEN("VOLUMEN", 6);
    
    private final String text;
    private final int index;

    public String getText() {
      return text;
    }
    
    public String getNameByText(String text) {
        for (NewEnum_Test e : values()) {
            if (e.text.matches(text))
            {
                return this.name();
            }
        }
      return null;
    }
    
    public int getIndex() {
      return index;
    }
    
    private NewEnum_Test(String text, int index) {
      this.text = text;
      this.index = index;
    }

    @Override
    public String toString() {
      return text;
    }
    
}
