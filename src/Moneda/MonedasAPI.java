/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Moneda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaconvertidor.ConvertidorJFrame;
import javaconvertidor.Utilidades;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author hp
 */
public class MonedasAPI {
    
    
    private static final Map<String, String> map = new TreeMap<>(); 
    
    //
    public static Map getTiposMonedas()
    {
        map.put("Canadian Dollar (CAD)", "CAD");
        map.put("Swiss Franc (CHF)", "CHF");
        map.put("Dominican Peso (DOP)", "DOP");
        map.put("Euro (EUR)", "EUR");
        map.put("Japanese Yen (JPY)", "JPY");
        map.put("United States Dollar (USD)", "USD"); 
    return map;
    }
    
    public static void convertirMoneda(String vMonedaBase, String vMonto, String vMonedaTarget)
    {
        //double money = Double.parseDouble(vMonto);
        DecimalFormat twoPlaces = new DecimalFormat("0.00");
        JPanel panel = new JPanel(new GridLayout(3, 2));
        Font mainFont = new Font("Arial", Font.BOLD, 18);
        
        JLabel baselabel = new JLabel();
        JLabel resultadolabel = new JLabel();
        resultadolabel.setFont(mainFont);
        baselabel.setFont(mainFont);
        try {
            
            // Setting URL
            String url_str = "https://v6.exchangerate-api.com/v6/"+getAPI_KEY ()+"/pair/"+map.get(vMonedaBase)+"/" + map.get(vMonedaTarget) + "/" + vMonto;
            
            // Making Request
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            int responseCode = request.getResponseCode();
 
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Convert to JSON
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                JsonObject jsonobj = root.getAsJsonObject();

                // Accessing object
                String req_result = jsonobj.get("result").getAsString();
                if(Utilidades.equalString(req_result, "success"))
                {
                    baselabel.setText(map.get(vMonedaBase) + " " + twoPlaces.format(Double.parseDouble(vMonto)) + "("+vMonedaBase+")");
                    resultadolabel.setText("IGUAL A: " +twoPlaces.format(jsonobj.get("conversion_result").getAsDouble())+ " " + vMonedaTarget);
                    
                    panel.add(baselabel);
                    panel.add(resultadolabel);
                    JOptionPane.showMessageDialog(null, panel, "MONEDA",
                    JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    String result_error_type = jsonobj.get("error-type").getAsString();
                    JOptionPane.showMessageDialog(null, "RESULTADO NO ESPERADO: " + req_result +" : " + result_error_type, "MONEDA",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "NO SE PUDO HACER CONEXION CON EL SERVIDOR!: ", "ERROR DE SERVIDOR #:" + responseCode,
                JOptionPane.ERROR_MESSAGE);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConvertidorJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConvertidorJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static double getTasaCambio(String vMonedaBase, String vMonedaTarget)
    {
        double tasa = 0.0;
        
        try {
            
            // Setting URL
            String url_str = "https://v6.exchangerate-api.com/v6/"+getAPI_KEY()+"/pair/"+map.get(vMonedaBase)+"/" + map.get(vMonedaTarget);
            
            // Making Request
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            
            int responseCode = request.getResponseCode();
 
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Convert to JSON
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                JsonObject jsonobj = root.getAsJsonObject();

                // Accessing object
                String req_result = jsonobj.get("result").getAsString();
                if(Utilidades.equalString(req_result, "success"))
                {
                    tasa = jsonobj.get("conversion_rate").getAsDouble();
                }
                else
                {
                    String result_error_type = jsonobj.get("error-type").getAsString();
                    JOptionPane.showMessageDialog(null, "RESULTADO NO ESPERADO: " + req_result +" : " + result_error_type, "MONEDA",
                    JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "NO SE PUDO HACER CONEXION CON EL SERVIDOR!: ", "ERROR DE SERVIDOR #:" + responseCode,
                JOptionPane.ERROR_MESSAGE);
            }            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConvertidorJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConvertidorJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tasa;
    }
    
    public static String getAPI_KEY (){
        InputStream input = null;
        Properties API_Prop = new Properties();
        String API_KEY = null;
        try {
            
            input = new FileInputStream(System.getProperty("user.dir")+"\\src\\Moneda\\API_KEY.properties");
            // load a properties file
            API_Prop.load(input);
            // get the property value and print it out
            API_KEY = API_Prop.getProperty("api_key");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MonedasAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MonedasAPI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (input!=null)
                {
                    input.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(MonedasAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return API_KEY;
    }
    
    public static void SetAPI_KEYPropertyValues(String vValue, String Property) {
       
        InputStream in = null;
        try {
            File file = new File(System.getProperty("user.dir")+"\\src\\Moneda\\API_KEY.properties");
            
            Properties prop = new Properties();
            in = new FileInputStream(file);
            if (in == null) {
                throw new FileNotFoundException();
            }
            prop.load(in);
            prop.setProperty(Property, vValue);
            OutputStream out = new FileOutputStream(file);
            prop.store(out, "Key Salvada!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MonedasAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MonedasAPI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (in!=null)
                {
                    in.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(MonedasAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    } 
    public static boolean testAPI_KEY(String API_KEY)
    {
        //43e5c949c02f4420c7f77f9e
        try {
            
            // Setting URL
            String url_str = "https://v6.exchangerate-api.com/v6/"+ API_KEY + "/latest/USD/";
            
            // Making Request
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            
            int responseCode = request.getResponseCode();
 
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Convert to JSON
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                JsonObject jsonobj = root.getAsJsonObject();

                // Accessing object
                String req_result = jsonobj.get("result").getAsString();
                return Utilidades.equalString(req_result, "success");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "NO SE PUDO HACER CONEXION CON EL SERVIDOR!: ", "ERROR DE SERVIDOR #:" + responseCode,
                JOptionPane.ERROR_MESSAGE);
            }           
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConvertidorJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConvertidorJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
