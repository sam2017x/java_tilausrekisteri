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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import r11.orderify.model.Laite;
import r11.orderify.model.Laitetyyppi;
import r11.orderify.model.Priority;
import r11.orderify.model.Tilaus;
import r11.orderify.model.Tyontekija;
import r11.orderify.view.MainApp;

/**
 * Esimiesikkunan kontrolleri
 * @author Samuli Juppi
 * @author Markus Nousiainen
 * @author Kiril Tsereh
 */
public class EsimiesController implements Initializable {
    // Tilaustabi
    @FXML
    private TextField customerField = new TextField();
    @FXML
    private TextArea commentField = new TextArea();
    @FXML
    private TableView<Tilaus> tilausTW;
    @FXML
    private TableColumn orderIdColumn;
    @FXML
    private TableColumn customerColumn;
    @FXML
    private TableColumn statusColumn;
    @FXML
    private TableColumn workedByColumn;
    @FXML
    private TableColumn priorityColumn;
    @FXML
    private TableColumn timeColumn;
    @FXML
    private Button createBtn;

    private ObservableList<Tilaus> tilausList;
    
    private Tilaus valittuTilaus = null;
    private Laite valittuLaite = null;
    private ObservableList<String> priorities = Priority.getObservablePriorities();
    
    @FXML
    private ComboBox itemSelect;
    private ObservableList<String> laitetyyppiMalliList = MainApp.dao.readLaitetyypimallit();
    
    private ArrayList<Laitetyyppi> laitetyypit;
   
    
    @FXML
    private TextField itemCountField;
    
    @FXML
    private TextArea itemCommentField;
    
    @FXML
    private TableView<Laite> itemTW;
    @FXML
    private TableColumn itemModelColumn;
    @FXML
    private TableColumn itemIdColumn;
    private ObservableList<Laite> itemList = FXCollections.observableArrayList();
    @FXML
    private ComboBox prioritySelect;
    
    
    // Tyontekijatabi
    @FXML
    private TableView<Tyontekija> ttTW;
    @FXML
    private TableColumn ttIdColumn;
    @FXML
    private TableColumn ttNameColumn;
    @FXML
    private TableColumn ttPerformanceColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label ordersInProcessLabel;
    @FXML
    private Label ordersCompletedLabel;
    @FXML
    private Label orderTimeLabel;
    @FXML
    private Label timeOrderAvgLabel;
    @FXML
    private Label itemsCompletedLabel;
    @FXML
    private Label timeItemAvgLabel;
    
    @FXML
    private TableView<Tilaus> ttTilausTW;
    @FXML
    private TableColumn ttTIdColumn;
    @FXML
    private TableColumn ttTCustomerColumn;
    @FXML
    private TableColumn ttTPriorityColumn;
    @FXML
    private TableColumn ttTItemsColumn;
    @FXML
    private TableColumn ttTStatusColumn;
    @FXML
    private TableColumn ttTTimeColumn;
    
    private ObservableList<Tyontekija> ttList;
    private Tyontekija valittuTyontekija = null;
    private ObservableList<Tilaus> ttTilausList;
    
    //for l10n
    @FXML
    private Button btnNewOrder;
    @FXML
    private Button btnRemoveOrder;
    @FXML
    private Button btnEsiRemove;
    @FXML
    private Label esiCust;
    @FXML
    private Label lblEsiComm;
    @FXML
    private Label lblEsiComm2;
    @FXML
    private Label lblEsiPrior;
    @FXML
    private Button btnClear;
    @FXML
    private Tab tabOrders;
    @FXML
    private Tab tabEmployees;
    @FXML
    private Label lblTtOrders;
    @FXML
    private Label lblFName;
    @FXML
    private Label lblLName;
    @FXML
    private Label lblInProg;
    @FXML
    private Label lblCompleted;
    @FXML
    private Label lblTimeSpent;
    @FXML
    private Label lblTOavg;
    @FXML
    private Label lblItemOrder;
    @FXML
    private Label lblTIavg;
    @FXML
    private Button btnAdd;
    
    
    
    
    
    /**
     * Tallentaa uuden tilauksen tai päivittää olemassaolevan tilauksen
     * @param event lisäysnapin klikkaus
     * @throws Exception 
     */
    @FXML
    private void createOrder(ActionEvent event) throws Exception {
        Tilaus tilaus = null;
        if(valittuLaite!=null) {
            valittuLaite.setOhjelmistot(itemCommentField.getText());
        }
        String customer = customerField.getText();
        String comment = commentField.getText();
        String priorityS = (String) prioritySelect.getSelectionModel().getSelectedItem();
        int priorityInt = Priority.getIntPriority(priorityS);
        if (valittuTilaus!=null) {
            valittuTilaus.setTilaaja(customer);
            valittuTilaus.setKommentti(comment);
            valittuTilaus.setPriority(priorityInt);
            MainApp.dao.updateTilaus(valittuTilaus);
            for (Laite laite : itemList) {
                valittuTilaus.addOrUpdateLaite(laite);
            }
        } else {
            tilaus = new Tilaus(customer, comment, priorityInt);
            MainApp.dao.addTilaus(tilaus);

            for(Laite laite : itemList) {
                laite.setTilaus(tilaus);
                tilaus.addToLaitteet(laite);
                MainApp.dao.addLaite(laite);
            }
        }
        
        tilausList.add(tilaus);
        nollaaKentat();
        
        // Täytetään tilauslista tilauksilla tietokannasta
        tilausTWPaivitys();
    }
    
    /**
     * Poistaa listasta valitun tilauksen tietokannasta
     * @param event poistonapin klikkaus
     * @throws Exception 
     */
    @FXML
    private void removeOrder(ActionEvent event) throws Exception {
        Tilaus selectedTilaus = tilausTW.getSelectionModel().getSelectedItem();
        MainApp.dao.removeTilaus(selectedTilaus);
        
        // Täytetään tilauslista tilauksilla tietokannasta
        tilausTWPaivitys();
        
        // Tyhjennetään kentät
        nollaaKentat();
        
    }
    
    /**
     * Lisää tuotteen valittuun tilaukseen
     * @param event tuotteen lisäysnapin klikkaus
     * @throws Exception 
     */
    @FXML
    private void addItem(ActionEvent event) throws Exception {
        String selectedLaitetyyppi = (String) itemSelect.getValue();
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
        itemSelect.setValue(null);
    }
    
    /**
     * Tyhjentää kentät
     * @param event Tyhjennysnapin klikkaus
     * @throws Exception 
     */
    @FXML
    private void clearFields(ActionEvent event) throws Exception {
        nollaaKentat();
    }
    

    /**
     * Täyttää tableview listat tiedolla tietokannasta
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // Täytetään tilauslista tilauksilla tietokannasta

        orderIdColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, Integer>("id"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("tilaaja"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("statusString"));
        workedByColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("ttNimi"));
        tilausTW.setItems(tilausList = MainApp.dao.readTilaukset());
        
        //l10n
        btnAdd.setText(MainApp.local.r.getString("add"));
        esiCust.setText(MainApp.local.r.getString("customer"));
        btnEsiRemove.setText(MainApp.local.r.getString("removeOrder"));
        createBtn.setText(MainApp.local.r.getString("createOrder"));
        itemSelect.setPromptText(MainApp.local.r.getString("selectitem"));
        lblEsiComm.setText(MainApp.local.r.getString("comment"));
        lblEsiComm2.setText(MainApp.local.r.getString("comment"));
        commentField.setPromptText(MainApp.local.r.getString("comment"));
        itemCommentField.setPromptText(MainApp.local.r.getString("comment"));
        customerField.setPromptText(MainApp.local.r.getString("customer"));
        lblEsiPrior.setText(MainApp.local.r.getString("priority"));
        prioritySelect.setPromptText(MainApp.local.r.getString("priority"));
        btnClear.setText(MainApp.local.r.getString("clear"));
        itemModelColumn.setText(MainApp.local.r.getString("model"));
        itemIdColumn.setText(MainApp.local.r.getString("ID"));
        timeColumn.setText(MainApp.local.r.getString("time"));
        statusColumn.setText(MainApp.local.r.getString("status"));
        priorityColumn.setText(MainApp.local.r.getString("priority"));
        workedByColumn.setText(MainApp.local.r.getString("workedby"));
        customerColumn.setText(MainApp.local.r.getString("customer"));
        orderIdColumn.setText(MainApp.local.r.getString("ID"));
        tabOrders.setText(MainApp.local.r.getString("orders"));
        tabEmployees.setText(MainApp.local.r.getString("employees"));
        ttIdColumn.setText(MainApp.local.r.getString("ID"));
        ttNameColumn.setText(MainApp.local.r.getString("name"));
        ttPerformanceColumn.setText(MainApp.local.r.getString("efficiency"));
        lblTtOrders.setText(MainApp.local.r.getString("orders"));
        ttTIdColumn.setText(MainApp.local.r.getString("ID"));
        ttTCustomerColumn.setText(MainApp.local.r.getString("customer"));
        ttTPriorityColumn.setText(MainApp.local.r.getString("priority"));
        ttTItemsColumn.setText(MainApp.local.r.getString("items"));
        ttTStatusColumn.setText(MainApp.local.r.getString("status"));
        ttTTimeColumn.setText(MainApp.local.r.getString("time"));
        lblFName.setText(MainApp.local.r.getString("Fname"));
        lblLName.setText(MainApp.local.r.getString("Lname"));
        lblInProg.setText(MainApp.local.r.getString("inprog"));
        lblCompleted.setText(MainApp.local.r.getString("completed"));
        lblTimeSpent.setText(MainApp.local.r.getString("timespent"));
        lblTOavg.setText(MainApp.local.r.getString("avg1"));
        lblItemOrder.setText(MainApp.local.r.getString("itemorder"));
        lblTIavg.setText(MainApp.local.r.getString("avg2"));
        
        

        tilausTWPaivitys();
        
        ttTIdColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, Integer>("id"));
        ttTCustomerColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("tilaaja"));
        ttTPriorityColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("priorityString"));
        ttTItemsColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, Integer>("laiteMaara"));
        ttTStatusColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("statusString"));
        ttTTimeColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("kulutettuAikaString"));
        
        // Täytetään työntekijälista työntekijöillä tietokannasta
        ttIdColumn.setCellValueFactory(new PropertyValueFactory<Tyontekija, Integer>("id"));
        ttNameColumn.setCellValueFactory(new PropertyValueFactory<Tyontekija, String>("fullName"));
        ttPerformanceColumn.setCellValueFactory(new PropertyValueFactory<Tyontekija, Double>("timeOrderAvg"));
        ttTW.setItems(ttList = MainApp.dao.readTyontekijat());
        
        // Täytetään laitetyyppivalikko arvoilla tietokannasta
        itemSelect.setItems(laitetyyppiMalliList);
        
        // force the field to be numeric only
        itemCountField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    itemCountField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<Laite, Integer>("id"));
        itemModelColumn.setCellValueFactory(new PropertyValueFactory<Laite, String>("laitetyyppimalliString"));
        
        // Täyttää kentät valitun tilauksen tiedoilla
        tilausTW.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                valittuTilaus = tilausTW.getSelectionModel().getSelectedItem();
                customerField.setText(valittuTilaus.getTilaaja());
                commentField.setText(valittuTilaus.getKommentti());
                itemList = valittuTilaus.getObservableLaitteet();
                itemTW.setItems(itemList);
                prioritySelect.getSelectionModel().select(valittuTilaus.getPriority()-1);
                createBtn.setText(MainApp.local.r.getString("updateOrder"));
                itemCommentField.setText(null);
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
        prioritySelect.setItems(priorities);
        prioritySelect.getSelectionModel().select(1);
        
        ttTW.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                valittuTyontekija = ttTW.getSelectionModel().getSelectedItem();
                firstNameLabel.setText(valittuTyontekija.getEtunimi());
                lastNameLabel.setText(valittuTyontekija.getSukunimi());
                ordersInProcessLabel.setText(""+valittuTyontekija.getVaiheessaTilaukset());
                ordersCompletedLabel.setText(""+valittuTyontekija.getHoidetutTilaukset());
                orderTimeLabel.setText(valittuTyontekija.getTyoaika());
                timeOrderAvgLabel.setText(""+valittuTyontekija.getTimeOrderAvg());
                itemsCompletedLabel.setText(""+valittuTyontekija.getHoidetutLaitteet());
                timeItemAvgLabel.setText(""+valittuTyontekija.getTimeItemAvg());
                ttTilausList = valittuTyontekija.getObservableTilaukset();
                ttTilausTW.setItems(ttTilausList);
            }
        });
        
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
        commentField.setText("");
        itemTW.setItems(null);
        itemList.clear();
        createBtn.setText(MainApp.local.r.getString("createOrder"));
        valittuTilaus = null;
        valittuLaite = null;
        itemCommentField.setText(null);
        itemCountField.setText("1");
        prioritySelect.getSelectionModel().select(1);
        itemSelect.getSelectionModel().select(null);
    }
    
    /**
     * Lataa tai päivittää tilauslistan
     */
    private void tilausTWPaivitys() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, Integer>("id"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("tilaaja"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("statusString"));
        workedByColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("ttNimi"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("priorityString"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("kulutettuAikaString"));
        tilausTW.setItems(tilausList = MainApp.dao.readTilaukset());
    }

}
