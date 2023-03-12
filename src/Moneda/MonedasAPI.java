/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Moneda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaconvertidor.ConvertidorJFrame;
import javaconvertidor.Utilidades;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class MonedasAPI {
    
    
    private static final Map<String, String> map = new TreeMap<>(); 
    //se conecta al servidor web y descarga las monedas soportadas por el API
    public static Map getTiposMonedas()
    {
         
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

            if(Utilidades.equalString(req_result, "success"))
            {
                Set<String> keyset = jsonobj.keySet();
                Iterator<String> keys = keyset.iterator(); 

                while(keys.hasNext()){
                    String key = keys.next();
                    Object value = jsonobj.get(key);
                    if (Utilidades.equalString(key, "supported_codes"))
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
            }else
            {
                return null;
            }
        }   catch (MalformedURLException ex) {
            Logger.getLogger(MonedasAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MonedasAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    return map;
    }
    public static void convertirMoneda(String vMonedaBase, String vMonto, String vMonedaTarget)
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
            if(Utilidades.equalString(req_result, "success"))
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
}
