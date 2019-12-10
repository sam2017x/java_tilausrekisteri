/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r11.orderify.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import r11.orderify.model.Laite;
import r11.orderify.model.Laitetyyppi;
import r11.orderify.model.Tilaus;
import r11.orderify.view.MainApp;

/**
 *
 * @author Kiril Tsereh
 */
public class GeneralUserController implements Initializable {
    
    @FXML
    private TableView<Tilaus> tilausTW;
    @FXML
    private TableColumn orderIdColumn;
    @FXML
    private TableColumn customerColumn;
    @FXML
    private TableColumn statusColumn;
    @FXML
    private TextField customerField;
    @FXML
    private TextArea orderCommentField;
    @FXML
    private TableView<Laite> itemTW;
    @FXML
    private TableColumn itemIdColumn;
    @FXML
    private TableColumn itemModelColumn;
    @FXML
    private TextArea itemCommentField;
    @FXML
    private ComboBox itemComboBox;
    @FXML
    private TextField itemCountField;
    
    //for l10n
    @FXML
    private Tab tabCurrOrders;
    @FXML
    private Label lblYourName;
    @FXML
    private Label lblGComm;
    @FXML
    private Button btnLogIn;
    @FXML
    private Button btnGCreateOrder;
    @FXML
    private Button btnAddItems;
    

    private ObservableList<Tilaus> tilausList;
    private ObservableList<Laite> itemList = FXCollections.observableArrayList();
    private ArrayList<Laitetyyppi> laitetyypit;
    private ObservableList<String> laitetyyppiMalliList = MainApp.dao.readLaitetyypimallit();
    private Laite valittuLaite = null;
    
    
    

    /**
     * Avaa kirjautumisikkunan
     * @param event
     * @throws Exception 
     */
    @FXML
    public void openLogin(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(MainApp.local.r.getString("sign"));
        stage.show();
    }
    
    /**
     * Lisää tuotteen tilaukseen
     * @param event
     * @throws Exception 
     */
    @FXML
    public void addItem(ActionEvent event) throws Exception {
        String selectedLaitetyyppi = (String) itemComboBox.getValue();
        int itemMaara = Integer.parseInt(itemCountField.getText());
        ObservableList<Laite> tilausLaitteet = FXCollections.observableArrayList();
        for(int i=0; i<itemMaara; i++) {
            Laite item = new Laite();
            item.setLaitetyyppi(findLaitetyyppiByMalli(selectedLaitetyyppi));
            item.setLaitetyyppimalliString();
            itemList.add(item);
        }
        itemTW.setItems(itemList);
        itemCountField.setText("1");
        itemComboBox.setValue(null);
    }
    
    /**
     * Luo uuden tilauksen
     * @param event
     * @throws Exception 
     */
    @FXML
    public void createOrder(ActionEvent event) throws Exception {
        if(valittuLaite!=null) {
            valittuLaite.setOhjelmistot(itemCommentField.getText());
        }
        String customer = customerField.getText();
        String comment = orderCommentField.getText();
        
        Tilaus tilaus = new Tilaus(customer, comment, 2);
        MainApp.dao.addTilaus(tilaus);
        
        for(Laite laite : itemList) {
            laite.setTilaus(tilaus);
            tilaus.addToLaitteet(laite);
            MainApp.dao.addLaite(laite);
        }
        
        nollaaKentat();
        tilausTWPaivitys();
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tilausTWPaivitys();
        
        //l10n
        itemIdColumn.setText(MainApp.local.r.getString("ID"));
        statusColumn.setText(MainApp.local.r.getString("status"));
        customerColumn.setText(MainApp.local.r.getString("customer"));
        tabCurrOrders.setText(MainApp.local.r.getString("currentOrder"));
        btnAddItems.setText(MainApp.local.r.getString("additems"));
        btnGCreateOrder.setText(MainApp.local.r.getString("createOrder"));
        btnLogIn.setText(MainApp.local.r.getString("login"));
        lblYourName.setText(MainApp.local.r.getString("yourname"));
        lblGComm.setText(MainApp.local.r.getString("comment"));
        customerField.setPromptText(MainApp.local.r.getString("customer"));
        orderCommentField.setPromptText(MainApp.local.r.getString("comment"));
        itemCommentField.setPromptText(MainApp.local.r.getString("comment"));
        itemIdColumn.setText(MainApp.local.r.getString("ID"));
        itemModelColumn.setText(MainApp.local.r.getString("model"));
        itemComboBox.setPromptText(MainApp.local.r.getString("selectitem"));
        
        itemComboBox.setItems(laitetyyppiMalliList);
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<Laite, Integer>("id"));
        itemModelColumn.setCellValueFactory(new PropertyValueFactory<Laite, String>("laitetyyppimalliString"));
        
        // force the field to be numeric only
        itemCountField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    itemCountField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        itemTW.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if(valittuLaite!=null) {
                    valittuLaite.setOhjelmistot(itemCommentField.getText());
                }
                
                valittuLaite = itemTW.getSelectionModel().getSelectedItem();
                itemCommentField.setText(valittuLaite.getOhjelmistot());
            }
        });
    }
    
    /**
     * Lataa tai päivittää tilauslistan
     */
    private void tilausTWPaivitys() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, Integer>("id"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("tilaaja"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("statusString"));
        tilausTW.setItems(tilausList = MainApp.dao.readUndoneTilaukset());
        tilausTW.getSortOrder().add(statusColumn);
    }
    
    /**
     * Löytää laitetyypin annetulla mallilla
     * @param malli
     * @return löydetty laitetyyppi
     */
    private Laitetyyppi findLaitetyyppiByMalli(String malli) {
        Laitetyyppi loydettyLaitetyyppi = null;
        if(laitetyypit==null) {
            laitetyypit = MainApp.dao.readLaitetyypit();
        }
        for(Laitetyyppi l : laitetyypit) {
            if (malli.equals(l.getMalli())) {
                loydettyLaitetyyppi = l;
            }
        }
        return loydettyLaitetyyppi;
    }
    
    /**
     * Nollaa kaikki tilauksen kentät tilaus välilehdessä
     */
    private void nollaaKentat() {
        customerField.setText("");
        orderCommentField.setText("");
        itemTW.setItems(null);
        itemList.clear();
        itemCommentField.setText(null);
        itemCountField.setText("1");
        itemComboBox.getSelectionModel().select(null);
    }
    
}
