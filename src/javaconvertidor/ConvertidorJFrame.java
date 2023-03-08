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
   
    //funcion que llena el menu de las opciones de conversion
    private void loadConvertidores()
    {
        
        DefaultComboBoxModel<Enum_Menu> cModel = new DefaultComboBoxModel<>(
          Enum_Menu.values());
        JComboBox<Enum_Menu> combo = new JComboBox<>(cModel);
        jComboBox_Convertidor.setModel(cModel);
        jComboBox_Convertidor.add(combo);
        
    }
    
    //Convertidor Menu
    private void SeleccionMenu(){
        JPanel panel = new JPanel(new GridBagLayout());
        //NewEnum_Test ret;
        //ret = NewEnum_Test.valueOf(Convertidor_Name); 
        JComboBox comboBox = new JComboBox(); 
        //switch (ret.getIndex())
        Enum_Menu enumValue = Enum_Menu.valueOf(Enum_Menu.class, jComboBox_Convertidor.getSelectedItem().toString());
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
                JOptionPane.showMessageDialog(null, comboBox, jComboBox_Convertidor.getSelectedItem().toString(),
                JOptionPane.QUESTION_MESSAGE);
                panel.add(comboBox);
            }
        }
        
   }
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

        jComboBox_Convertidor = new javax.swing.JComboBox();
        jComboBox_Convertidor.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if(event.getID() == ItemEvent.ITEM_STATE_CHANGED)
                {
                    if(event.getStateChange() == ItemEvent.SELECTED)
                    {
                        // do something with object
                        // The selected element is a "ComboModel" instance, just cast it to the correct type
                        //String test = jComboBox_Convertidor.getSelectedItem().toString();
                        //load_Con_Sel(test);
                        SeleccionMenu();
                    }
                }
            }
        });

        jLabel1 = new javax.swing.JLabel();
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

        jComboBox_Convertidor.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setText("SELECCIONE UNA OPCION DE CONVERSION");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox_Convertidor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(1, 1, 1)
                .addComponent(jComboBox_Convertidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(302, Short.MAX_VALUE))
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
        loadConvertidores();
    }//GEN-LAST:event_formWindowOpened

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
    private javax.swing.JComboBox jComboBox_Convertidor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    // End of variables declaration//GEN-END:variables
}
