/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaconvertidor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.GridBagLayout;
import java.awt.List;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.JSONException;
/**
 *
 * @author hp
 */
public class ConvertidorJFrame extends javax.swing.JFrame {
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
                JOptionPane.showMessageDialog(null, comboBox, menu_Seleccionado,
                JOptionPane.QUESTION_MESSAGE);
                panel.add(comboBox);
            }
        }
        
   }
    //optiene una lista de las monedas disponibles para convertir
    public void getMonedasAPI()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        JComboBox comboBox = new JComboBox(); 
        Map<String, String> map = new HashMap<>();
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
            ComboBoxModel model = new DefaultComboBoxModel(keys1);
            comboBox.setModel(model);
            comboBox.addItemListener((ItemEvent event) -> {
                if(event.getID() == ItemEvent.ITEM_STATE_CHANGED)
                {
                    if(event.getStateChange() == ItemEvent.SELECTED)
                    {
                        System.out.println(map.get(event.getItem().toString()));
                    }
                }
            });
            JOptionPane.showMessageDialog(null, comboBox, "MONEDA",
            JOptionPane.QUESTION_MESSAGE);
            panel.add(comboBox);
        }
        else
        {
            JOptionPane.showMessageDialog(null, req_result, "MONEDA",
        JOptionPane.QUESTION_MESSAGE);
        }
        
        } catch (JSONException | IOException ex) {
            Logger.getLogger(ConvertidorJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean equalString(String sb1, String sb2) {
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
        jButton_MONEDA = new javax.swing.JButton();
        jButton_LONGITUD = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
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

        jButton_MONEDA.setText("MONEDA");
        jButton_MONEDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_MONEDAActionPerformed(evt);
            }
        });

        jButton_LONGITUD.setText("LONGITUD");
        jButton_LONGITUD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LONGITUDActionPerformed(evt);
            }
        });

        jButton3.setText("MASA");

        jButton4.setText("CAPACIDAD");

        jButton5.setText("SUPERFICIE");

        jButton6.setText("VOLUMEN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton_MONEDA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_LONGITUD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_MONEDA)
                    .addComponent(jButton_LONGITUD)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap(228, Short.MAX_VALUE))
        );

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
                        .addGap(0, 110, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
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
    // End of variables declaration//GEN-END:variables
}
