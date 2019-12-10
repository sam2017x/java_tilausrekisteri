/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r11.orderify.model;
import java.util.*;

/**
 *
 * @author marku
 */
public class I18n {
    
    public static ResourceBundle r;
    
    public I18n(String lang, String country) {
        
        
        Locale currentLocale;

        currentLocale = new Locale(lang, country);
        
        r = ResourceBundle.getBundle("\\l10n\\Bundle", currentLocale);
    }
        /**public String localisize() {
        btnNewOrder.setText(r.getString("newOrder"));
        btnRemoveOrder.setText(r.getString("removeOrder"));


    }*/
    
}
