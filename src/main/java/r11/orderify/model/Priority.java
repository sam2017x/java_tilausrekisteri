/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r11.orderify.model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import r11.orderify.view.MainApp;

/**
 * Prioriteettejen käsittelyyn.
 * Prioriteetit tallennettuna tietokantaan int muodossa:
 * 1 = High
 * 2 = Medium
 * 3 = Low
 * @author Samuli Juppi
 * @author Markus Nousiainen
 * @author Kiril Tsereh
 */
public class Priority {
    
    static private String high = MainApp.local.r.getString("high");
    static private String medium = MainApp.local.r.getString("medium");
    static private String low = MainApp.local.r.getString("low");
    
    static private ObservableList<String> priorities;
    
    /**
     * Luo prioriteetit sisältävän ObservableListin
     * @return Kaikki prioriteetit sisältävä ObservableListi
     */
    static public ObservableList<String> getObservablePriorities() {
        if(priorities==null) {
            priorities = FXCollections.observableArrayList();
            priorities.add(high);
            priorities.add(medium);
            priorities.add(low);
        }
        return priorities;
    }
    
    /**
     * Muuntaa string muodossa olevan prioriteetin int muotoon
     * @param stringPriority prioriteetti string muodossa
     * @return prioriteetti int muodossa
     */
    static public int getIntPriority(String stringPriority) {
        return priorities.indexOf(stringPriority)+1;
    }
    
    /**
     * Muuntaa int muotoisen prioriteetin string muotoiseksi
     * @param priorityInt prioriteetti int muodossa
     * @return prioriteetti string muodossa
     */
    static public String getStringPriority(int priorityInt) {
        String priorityString = null;
        switch(priorityInt){
            case 1:
                priorityString = high;
                break;
            case 2:
                priorityString = medium;
                break;
            case 3:
                priorityString = low;
                break;
        }
        return priorityString;
    }
    
}
