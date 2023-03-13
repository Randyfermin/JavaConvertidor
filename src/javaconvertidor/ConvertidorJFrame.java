/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package javaconvertidor;

import Capacidad.Enum_Capacidad;
import Longitud.Enum_Longitud;
import Masa.Enum_Masa;
import static Moneda.MonedasAPI.convertirMoneda;
import static Moneda.MonedasAPI.getTasaCambio;
import static Moneda.MonedasAPI.getTiposMonedas;
import Superficie.Enum_Superficie;
import Volumen.Enum_Volumen;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 *
 * @author hp
 */
public class ConvertidorJFrame extends javax.swing.JFrame {
    private Map<String, String> map = new TreeMap<>();
    Font mainFont = new Font("Arial", Font.BOLD, 18);
    /**
     * Creates new form ConvertidorJFrame
     */
    public ConvertidorJFrame() {
        initComponents();
        //Esta propiedad forza a centrar la pantalla inicial 
        setLocationRelativeTo(null);
        
    }
    //optiene una lista de las monedas disponibles para convertir
    private void buildUI(String menu_Seleccionado)
    {
        JPanel panel = new JPanel(new GridLayout(7, 2));
        JComboBox comboBoxBase = new JComboBox(); 
        JComboBox comboBoxTarget = new JComboBox(); 
        JTextField customTypeField = new JTextField("", 15);
        JLabel tasaCambioTitle = new JLabel("TASA DE CAMBIO ACTUAL: 1.00");
        JLabel comboBoxBaseTitle = new JLabel();
        JLabel comboBoxTargetTitle = new JLabel();
        JLabel customTypeFieldTitle = new JLabel();
        
        //font y Size set
        comboBoxBase.setFont(mainFont);
        comboBoxTarget.setFont(mainFont);        
        customTypeField.setFont(mainFont);   
        comboBoxBaseTitle.setFont(mainFont);
        comboBoxTargetTitle.setFont(mainFont);
        customTypeFieldTitle.setFont(mainFont);
        tasaCambioTitle.setFont(mainFont);
        
        Object[] keys1 = null;
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
        
        Enum_Menu enumValue = Enum_Menu.valueOf(Enum_Menu.class, menu_Seleccionado);
        //System.err.println("Index: " + enumValue.getIndex());
        
        switch (enumValue.getIndex())
        {
            case 1 ->  {
                map = getTiposMonedas();
                if(map == null)
                {
                    JLabel label = new JLabel("NO SE PUDO ACCEDER AL SERVICIO\n FAVOR INTENTE DE NUEVO");
                    label.setFont(mainFont);
                    JOptionPane.showMessageDialog(null, label, menu_Seleccionado,
                JOptionPane.ERROR_MESSAGE);
                }else
                {
                    keys1 = map.keySet().toArray();
                }
            }
            case 2 ->  {
                
                keys1 = Enum_Longitud.comboBoxValues().keySet().toArray();
            }
            case 3 ->  {
                
                keys1 = Enum_Masa.comboBoxValues().keySet().toArray();
            }
            case 4 ->  {
                
                keys1 = Enum_Capacidad.comboBoxValues().keySet().toArray();
            }
            case 5 ->  {
                
                keys1 = Enum_Superficie.comboBoxValues().keySet().toArray();
            }
            case 6 ->  {
                
                keys1 = Enum_Volumen.comboBoxValues().keySet().toArray();
            }
        }
        
        ComboBoxModel modelBase = new DefaultComboBoxModel(keys1);
        ComboBoxModel modelTarget = new DefaultComboBoxModel(keys1);
        comboBoxBase.setModel(modelBase);
        comboBoxTarget.setModel(modelTarget);
        
        comboBoxBase.addItemListener((ItemEvent event) -> {
            if(event.getID() == ItemEvent.ITEM_STATE_CHANGED)
            {
                if(event.getStateChange() == ItemEvent.SELECTED)
                {
                    if( enumValue.getIndex() == 1 )
                    {
                        tasaCambioTitle.setText("TASA DE CAMBIO ACTUAL: " + getTasaCambio(comboBoxBase.getSelectedItem().toString(), comboBoxTarget.getSelectedItem().toString()));
                    }
                }
            }
        });
        
        comboBoxTarget.addItemListener((ItemEvent event) -> {
            if(event.getID() == ItemEvent.ITEM_STATE_CHANGED)
            {
                if(event.getStateChange() == ItemEvent.SELECTED)
                {
                    if( enumValue.getIndex() == 1 )
                    {
                        tasaCambioTitle.setText("TASA DE CAMBIO ACTUAL: " + getTasaCambio(comboBoxBase.getSelectedItem().toString(), comboBoxTarget.getSelectedItem().toString()));
                    }
                }
            }
        });
        if( enumValue.getIndex() == 1 )
            {
                panel.add(tasaCambioTitle);        
            }
        
            comboBoxBaseTitle.setText("ELIGE LA "+ menu_Seleccionado +" QUE DESEAS CONVERTIR");
        panel.add(comboBoxBaseTitle);
        panel.add(comboBoxBase);
            customTypeFieldTitle.setText("TOTAL A CONVERTIR");
        panel.add(customTypeFieldTitle);
        panel.add(customTypeField);
            comboBoxTargetTitle.setText("ELIGE LA " + menu_Seleccionado +" A LA QUE DESEAS CONVERTIR ");
        panel.add(comboBoxTargetTitle);
        panel.add(comboBoxTarget);
        
        JOptionPane.showMessageDialog(null, panel, menu_Seleccionado,
        JOptionPane.QUESTION_MESSAGE);
        
        if (customTypeField.getText().isBlank())
        {
            customTypeField.setText("1.00");
        }

        convertirValores(enumValue.getIndex(), comboBoxBase.getSelectedItem().toString(), customTypeField.getText(),
                comboBoxTarget.getSelectedItem().toString());
    }
    
    public static void convertirValores(int vIndex, String vValorBase, String vTotal, String vValorTarget){
        switch (vIndex)
        {
            case 1 ->  {
               convertirMoneda(vValorBase, vTotal,vValorTarget);
            }
            case 2 ->  {
                Enum_Longitud.ConvertirLongitud(vValorBase, Double.parseDouble(vTotal), vValorTarget);
            }
            case 3 ->  {
                Enum_Masa.ConvertirMasa(vValorBase, Double.parseDouble(vTotal), vValorTarget);
            }
            case 4 ->  {
                Enum_Capacidad.ConvertirCapacidad(vValorBase, Double.parseDouble(vTotal), vValorTarget);
            }
            case 5 ->  {
                Enum_Superficie.ConvertirSuperficie(vValorBase, Double.parseDouble(vTotal), vValorTarget);
            }
            case 6 ->  {
                Enum_Volumen.ConvertirVolumen(vValorBase, Double.parseDouble(vTotal), vValorTarget);
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton_MONEDA = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton_LONGITUD = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton_MASA = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton_CAPACIDAD = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton_SUPERFICIE = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton_VOLUMEN = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ORACLE + ALURA CHALLENGES CONVERTIDOR");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/challengeImage.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("SELECCIONE UNA OPCION DE CONVERSION"));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton_MONEDA.setText("MONEDA");
        jButton_MONEDA.setMaximumSize(new java.awt.Dimension(100, 50));
        jButton_MONEDA.setMinimumSize(new java.awt.Dimension(100, 50));
        jButton_MONEDA.setPreferredSize(new java.awt.Dimension(100, 50));
        jButton_MONEDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_MONEDAActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_MONEDA);
        jToolBar1.add(jSeparator1);

        jButton_LONGITUD.setText("LONGITUD");
        jButton_LONGITUD.setMaximumSize(new java.awt.Dimension(100, 50));
        jButton_LONGITUD.setMinimumSize(new java.awt.Dimension(100, 50));
        jButton_LONGITUD.setPreferredSize(new java.awt.Dimension(100, 50));
        jButton_LONGITUD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LONGITUDActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_LONGITUD);
        jToolBar1.add(jSeparator2);

        jButton_MASA.setText("MASA");
        jButton_MASA.setMaximumSize(new java.awt.Dimension(100, 50));
        jButton_MASA.setMinimumSize(new java.awt.Dimension(100, 50));
        jButton_MASA.setPreferredSize(new java.awt.Dimension(100, 50));
        jButton_MASA.setRequestFocusEnabled(false);
        jButton_MASA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_MASAActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_MASA);
        jToolBar1.add(jSeparator3);

        jButton_CAPACIDAD.setText("CAPACIDAD");
        jButton_CAPACIDAD.setMaximumSize(new java.awt.Dimension(100, 50));
        jButton_CAPACIDAD.setMinimumSize(new java.awt.Dimension(100, 50));
        jButton_CAPACIDAD.setPreferredSize(new java.awt.Dimension(100, 50));
        jButton_CAPACIDAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CAPACIDADActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_CAPACIDAD);
        jToolBar1.add(jSeparator4);

        jButton_SUPERFICIE.setText("SUPERFICIE");
        jButton_SUPERFICIE.setMaximumSize(new java.awt.Dimension(100, 50));
        jButton_SUPERFICIE.setMinimumSize(new java.awt.Dimension(100, 50));
        jButton_SUPERFICIE.setPreferredSize(new java.awt.Dimension(100, 50));
        jButton_SUPERFICIE.setRequestFocusEnabled(false);
        jButton_SUPERFICIE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SUPERFICIEActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_SUPERFICIE);
        jToolBar1.add(jSeparator5);

        jButton_VOLUMEN.setText("VOLUMEN");
        jButton_VOLUMEN.setMaximumSize(new java.awt.Dimension(100, 50));
        jButton_VOLUMEN.setMinimumSize(new java.awt.Dimension(100, 50));
        jButton_VOLUMEN.setPreferredSize(new java.awt.Dimension(100, 50));
        jButton_VOLUMEN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VOLUMENActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton_VOLUMEN);
        jToolBar1.add(jSeparator6);

        jButton1.setText("CERRAR");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setMaximumSize(new java.awt.Dimension(100, 50));
        jButton1.setMinimumSize(new java.awt.Dimension(100, 50));
        jButton1.setPreferredSize(new java.awt.Dimension(100, 50));
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jButton_MONEDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_MONEDAActionPerformed
        // TODO add your handling code here:
        buildUI(jButton_MONEDA.getText());
    }//GEN-LAST:event_jButton_MONEDAActionPerformed

    private void jButton_LONGITUDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LONGITUDActionPerformed
        // TODO add your handling code here:
         buildUI(jButton_LONGITUD.getText());        
    }//GEN-LAST:event_jButton_LONGITUDActionPerformed

    private void jButton_MASAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_MASAActionPerformed
        // TODO add your handling code here:
        buildUI(jButton_MASA.getText());        
    }//GEN-LAST:event_jButton_MASAActionPerformed

    private void jButton_CAPACIDADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CAPACIDADActionPerformed
        // TODO add your handling code here:
        buildUI(jButton_CAPACIDAD.getText());        
    }//GEN-LAST:event_jButton_CAPACIDADActionPerformed

    private void jButton_SUPERFICIEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SUPERFICIEActionPerformed
        // TODO add your handling code here:
        buildUI(jButton_SUPERFICIE.getText());     
    }//GEN-LAST:event_jButton_SUPERFICIEActionPerformed

    private void jButton_VOLUMENActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VOLUMENActionPerformed
        // TODO add your handling code here:
        buildUI(jButton_VOLUMEN.getText());
    }//GEN-LAST:event_jButton_VOLUMENActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_CAPACIDAD;
    private javax.swing.JButton jButton_LONGITUD;
    private javax.swing.JButton jButton_MASA;
    private javax.swing.JButton jButton_MONEDA;
    private javax.swing.JButton jButton_SUPERFICIE;
    private javax.swing.JButton jButton_VOLUMEN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
