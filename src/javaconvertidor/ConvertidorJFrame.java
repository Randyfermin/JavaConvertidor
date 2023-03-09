/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaconvertidor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.json.JSONException;
/**
 *
 * @author hp
 */
public class ConvertidorJFrame extends javax.swing.JFrame {
    private final Map<String, String> map = new TreeMap<>();
    
    /**
     * Creates new form ConvertidorJFrame
     */
    public ConvertidorJFrame() {
        initComponents();
        //Esta propiedad forza a centrar la pantalla inicial 
        setLocationRelativeTo(null);
        
    }

    //Convertidor Menu
    private void SeleccionMenu(String menu_Seleccionado){
        
        JPanel panel = new JPanel(new GridBagLayout());
        //NewEnum_Test ret;
        //ret = NewEnum_Test.valueOf(Convertidor_Name); 
        JComboBox comboBox = new JComboBox(); 
        //switch (ret.getIndex())
        Enum_Menu enumValue = Enum_Menu.valueOf(Enum_Menu.class, menu_Seleccionado);
        //System.err.println("Index: " + enumValue.getIndex());
        
        switch (enumValue.getIndex())
        {
            case 1 ->             {
                getMonedasAPI();
            }
            case 2 ->             {
                Enum_Longitud[] longitudes = Enum_Longitud.values();
                for (Enum_Longitud longitud : longitudes)
                {
                    comboBox.addItem(longitud.label);
                }
                panel.add(comboBox);
                JOptionPane.showMessageDialog(null, panel, menu_Seleccionado,
                JOptionPane.QUESTION_MESSAGE);
                
            }
        }
        
   }
    
    //optiene una lista de las monedas disponibles para convertir
    private void getMonedasAPI()
    {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        JComboBox comboBoxBase = new JComboBox(); 
        JComboBox comboBoxTarget = new JComboBox(); 
        JTextField customTypeField = new JTextField("", 20);
        
        customTypeField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

               char vKey = e.getKeyChar();

               if(!(Character.isDigit(vKey) || (vKey == KeyEvent.VK_MINUS) || (vKey == KeyEvent.VK_PERIOD) || (vKey == KeyEvent.VK_BACK_SPACE) || (vKey == KeyEvent.VK_DELETE) || (vKey == KeyEvent.VK_ENTER)))
                {
                    e.consume();
                }
                else
                {

                    if (vKey == KeyEvent.VK_ENTER)
                        {
                            comboBoxTarget.requestFocus();
                            comboBoxTarget.grabFocus();
                        }

                    if ((vKey == KeyEvent.VK_PERIOD) && (customTypeField.getText().contains(".")))
                        {
                            e.consume();
                        }

                    int s = customTypeField.getText().indexOf(".");
                    String vS = customTypeField.getText().substring(s+1, customTypeField.getText().length());


                    if ((vS.length() >= 2) && (customTypeField.getText().contains(".")))
                        {
                               e.consume();
                        }
                            //allow the minus char if the key is pressed and not in text
                    if ((vKey == KeyEvent.VK_MINUS) && (customTypeField.getText().contains("-")) && !(customTypeField.getText().isEmpty()))
                        {
                            e.consume();
                        }
                }

            }
        });
        
        try {
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/43e5c949c02f4420c7f77f9e/codes";

        // Making Request
        URL url;
        url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Convert to JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();
        
        // Accessing object
        String req_result = jsonobj.get("result").getAsString();
        //JSONObject jsonObject = new JSONObject();
                
        if(equalString(req_result, "success"))
        {
            Set<String> keyset = jsonobj.keySet();
            Iterator<String> keys = keyset.iterator(); 
            
            while(keys.hasNext()){
                String key = keys.next();
                Object value = jsonobj.get(key);
                if (equalString(key, "supported_codes"))
                {
                    //System.out.println( key +" : " + value);
                    
                    //split string delimited by comma  
                    String[] stringarray = value.toString().replace("[", "").replace("]", "").replace("\"", "").split(",");    //we can use dot, whitespace, any character   
                    //iterate over string array  
                    
                    
                    for(int i=0; i< stringarray.length; i++)  
                    {  
                        //prints the tokens  
                        map.put(stringarray[i+1]+"(" + stringarray[i] + ")", stringarray[i]);
                        //System.out.println(stringarray[i] + " --> " + stringarray[i+1]); 
                        i++;
                        
                    }  
                }
            }

            
            Object[] keys1 = map.keySet().toArray();
            ComboBoxModel modelBase = new DefaultComboBoxModel(keys1);
            ComboBoxModel modelTarget = new DefaultComboBoxModel(keys1);
            comboBoxBase.setModel(modelBase);
            comboBoxTarget.setModel(modelTarget);
            
            comboBoxBase.addItemListener((ItemEvent event) -> {
                if(event.getID() == ItemEvent.ITEM_STATE_CHANGED)
                {
                    if(event.getStateChange() == ItemEvent.SELECTED)
                    {
                        
                        
                    }
                }
            });
            
            panel.add(new JLabel("ELIGE LA MONEDA QUE DESEAS CONVERTIR"));
            panel.add(comboBoxBase);
            panel.add(new JLabel("MONTO TOTAL A CAMBIAR"));
            panel.add(customTypeField);
            panel.add(new JLabel("ELIGE LA MONEDA A LA QUE DESEAS CONVERTIR "));
            panel.add(comboBoxTarget);
            JOptionPane.showMessageDialog(null, panel, "MONEDA",
            JOptionPane.QUESTION_MESSAGE);
            if (customTypeField.getText().isBlank())
            {
                customTypeField.setText("1.00");
                System.out.println("Text Empty");
            }
            
            convertirMoneda(comboBoxBase.getSelectedItem().toString(), customTypeField.getText(),
                    comboBoxTarget.getSelectedItem().toString());
        }
        else
        {
            JOptionPane.showMessageDialog(null, "NO SE PUDO ACCEDER AL SERVICIO\n FAVOR INTENTE DE NUEVO", "MONEDA",
        JOptionPane.ERROR_MESSAGE);
        }
        
        } catch (JSONException | IOException ex) {
            Logger.getLogger(ConvertidorJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void convertirMoneda(String vMonedaBase, String vMonto, String vMonedaTarget)
    {
        double money = Double.parseDouble(vMonto);
        DecimalFormat twoPlaces = new DecimalFormat("0.00");
        
        try {
            
            // Setting URL
            String url_str = "https://v6.exchangerate-api.com/v6/43e5c949c02f4420c7f77f9e/pair/"+map.get(vMonedaBase)+"/" + map.get(vMonedaTarget) + "/" + vMonto;
            
            // Making Request
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            
            // Convert to JSON
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();
            
            // Accessing object
            String req_result = jsonobj.get("result").getAsString();
            if(equalString(req_result, "success"))
            {
                JOptionPane.showMessageDialog(null, twoPlaces.format(money) + " " + vMonedaBase + "\n = \n " + twoPlaces.format(jsonobj.get("conversion_result").getAsDouble())+ " " + vMonedaTarget, "MONEDA",
        JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConvertidorJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConvertidorJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //esta funcion compara dos tipos de Strings y retorna una boolean
    private static boolean equalString(String sb1, String sb2) {
    boolean vReturn = true;
    int len = sb1.length();
    if (sb1.length() == sb2.length())
    {
        for (int i = 0; i < len; i++) {
         if (sb1.charAt(i) != sb2.charAt(i)) {
              vReturn = false;
            }
        }
    }
    else
    {
        vReturn = false;
    }
    return vReturn;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton_MONEDA = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton_LONGITUD = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setText("SELECCIONE UNA OPCION DE CONVERSION");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        jToolBar1.setRollover(true);

        jButton_MONEDA.setText("MONEDA");
        jButton_MONEDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_MONEDAActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_MONEDA);

        jButton4.setText("CAPACIDAD");
        jToolBar1.add(jButton4);

        jButton_LONGITUD.setText("LONGITUD");
        jButton_LONGITUD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LONGITUDActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_LONGITUD);

        jButton3.setText("MASA");
        jToolBar1.add(jButton3);

        jButton5.setText("SUPERFICIE");
        jToolBar1.add(jButton5);

        jButton6.setText("VOLUMEN");
        jToolBar1.add(jButton6);

        jMenu1.setText("Convertidor");

        jMenuItem2.setText("Moneda/Crypto");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Longitud");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Acerca de ...");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jButton_MONEDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_MONEDAActionPerformed
        // TODO add your handling code here:
        SeleccionMenu(jButton_MONEDA.getText());
    }//GEN-LAST:event_jButton_MONEDAActionPerformed

    private void jButton_LONGITUDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LONGITUDActionPerformed
        // TODO add your handling code here:
         SeleccionMenu(jButton_LONGITUD.getText());        
    }//GEN-LAST:event_jButton_LONGITUDActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConvertidorJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConvertidorJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConvertidorJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConvertidorJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ConvertidorJFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton_LONGITUD;
    private javax.swing.JButton jButton_MONEDA;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
