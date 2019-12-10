package r11.orderify.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import r11.orderify.model.Laite;
import r11.orderify.model.Tilaus;
import r11.orderify.view.MainApp;

/**
 * Työntekijäikkunan kontrolleri
 * @author Samuli Juppi
 * @author Markus Nousiainen
 * @author Kiril Tsereh
 */
public class TyontekijaController implements Initializable {

    // Kaikki tilaukset välilehti
    @FXML
    private TableView<Tilaus> tilausTW;
    @FXML
    private TableColumn orderIdColumn;
    @FXML
    private TableColumn customerColumn;
    @FXML
    private TableColumn statusColumn;
    @FXML
    private TableColumn priorityColumn;
    
    private Tilaus valittuTilaus = null;
    private Laite valittuLaite = null;
    private ObservableList<Laite> itemList = FXCollections.observableArrayList();
    
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label orderCommentFieldLabel;
    @FXML
    private Label itemCommentFieldLabel;
    @FXML
    private TableView<Laite> itemTW;
    @FXML
    private TableColumn itemModelColumn;
    @FXML
    private TableColumn itemIdColumn;
    
    
    
    
    // Omat tilaukset välilehti
    @FXML
    private TableView<Tilaus> mwTilausTW;
    @FXML
    private TableColumn mwOrderIdColumn;
    @FXML
    private TableColumn mwCustomerColumn;
    @FXML
    private TableColumn mwStatusColumn;
    @FXML
    private Label mwCustomerNameLabel;
    @FXML
    private TextArea mwOrderCommentField;
    @FXML
    private TableView<Laite> mwItemTW;
    @FXML
    private TableColumn mwItemModelColumn;
    @FXML
    private TableColumn mwTimeColumn;
    @FXML
    private TableColumn mwItemIdColumn;
    @FXML
    private TableColumn mwPriorityColumn;
    @FXML
    private TextArea mwItemCommentField;
    
    //for l10n
    @FXML 
    private Label lblTtItemComm;
    @FXML 
    private Label lblTtOrderComm;
    @FXML 
    private Button btnTtAccOrder;
    @FXML
    private Tab ttTabOrders;
    @FXML
    private Tab ttTabMyWork;
    @FXML
    private Button btnMwUpdateOrder;
    @FXML
    private Button btnMwOrderDone;
    @FXML 
    private Label lblMwOrderComm;
    @FXML 
    private Label lblMwItemComm;
    

    private ObservableList<Tilaus> tilausList;
    private Tilaus mwValittuTilaus = null;
    private Laite mwValittuLaite = null;
    private ObservableList<Laite> mwItemList = FXCollections.observableArrayList();
    
    /**
     * Liittää valittuun tilaukseen kirjautuneena olevan käyttäjän, vaihtaa tilauksen tilaa "in progress"-tilaksi, siirtää tilauksen työntekijän työn alla olevien tilausten listaan.
     * @param event Tilauksen hyväksymisnapin klikkaus
     * @throws Exception 
     */
    @FXML
    private void acceptOrder(ActionEvent event) throws Exception {
        if (tilausTW.getSelectionModel().getSelectedItem() != null) {
            Tilaus selectedTilaus = tilausTW.getSelectionModel().getSelectedItem();
            selectedTilaus = MainApp.dao.setTilausTt(selectedTilaus);
            
            tilausTWPaivitys();
            mwTilausTWPaivitys();
            
            customerNameLabel.setText(null);
            orderCommentFieldLabel.setText(null);
            itemTW.setItems(null);
            itemCommentFieldLabel.setText(null);
            
            
        }
    }
    
    /**
     * Vaihtaa valitun tilauksen tilaa "done"-tilaksi
     * @param event Tilauksen valmisnapin klikkaus
     * @throws Exception 
     */
    @FXML
    private void doneOrder(ActionEvent event) throws Exception {
        if (mwTilausTW.getSelectionModel().getSelectedItem() != null) {
            Tilaus selectedTilaus = mwTilausTW.getSelectionModel().getSelectedItem();
            selectedTilaus = MainApp.dao.setTilausDone(selectedTilaus);
            
            tilausTWPaivitys();
            mwTilausTWPaivitys();
        }
    }
    
    /**
     * Tallentaa tilaukseen tehdyt muutokset
     * @param event Päivitysnapin klikkaus
     * @throws Exception 
     */
    @FXML
    private void updateOrder(ActionEvent event) throws Exception {
        String comment = mwOrderCommentField.getText();
        mwValittuTilaus.setKommentti(comment);
        MainApp.dao.updateTilaus(mwValittuTilaus);
        for (Laite laite : mwItemList) {
            mwValittuTilaus.addOrUpdateLaite(laite);
        }
    }

    /**
     * Täyttää tableview listat tiedolla tietokannasta
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL url, ResourceBundle rb) {
        tilausTWPaivitys();
        mwTilausTWPaivitys();
        
        //l10n
        itemModelColumn.setText(MainApp.local.r.getString("model"));
        itemIdColumn.setText(MainApp.local.r.getString("ID"));
        statusColumn.setText(MainApp.local.r.getString("status"));
        priorityColumn.setText(MainApp.local.r.getString("priority"));
        customerColumn.setText(MainApp.local.r.getString("customer"));
        orderIdColumn.setText(MainApp.local.r.getString("ID"));
        lblTtItemComm.setText(MainApp.local.r.getString("comment"));
        customerNameLabel.setText(MainApp.local.r.getString("customName"));
        lblTtOrderComm.setText(MainApp.local.r.getString("comment"));
        btnTtAccOrder.setText(MainApp.local.r.getString("accOrder"));
        ttTabOrders.setText(MainApp.local.r.getString("orders"));
        ttTabMyWork.setText(MainApp.local.r.getString("mywork"));
        mwItemModelColumn.setText(MainApp.local.r.getString("model"));
        mwItemIdColumn.setText(MainApp.local.r.getString("ID"));
        mwStatusColumn.setText(MainApp.local.r.getString("status"));
        mwPriorityColumn.setText(MainApp.local.r.getString("priority"));
        mwTimeColumn.setText(MainApp.local.r.getString("time"));
        mwCustomerColumn.setText(MainApp.local.r.getString("customer"));
        mwOrderIdColumn.setText(MainApp.local.r.getString("ID"));
        mwItemCommentField.setPromptText(MainApp.local.r.getString("comment"));
        mwOrderCommentField.setPromptText(MainApp.local.r.getString("comment"));
        btnMwUpdateOrder.setText(MainApp.local.r.getString("updateOrder"));
        btnMwOrderDone.setText(MainApp.local.r.getString("orderDone"));
        lblMwOrderComm.setText(MainApp.local.r.getString("comment"));
        lblMwItemComm.setText(MainApp.local.r.getString("comment"));
        mwCustomerNameLabel.setText(MainApp.local.r.getString("customName"));
        
        
        
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<Laite, Integer>("id"));
        itemModelColumn.setCellValueFactory(new PropertyValueFactory<Laite, String>("laitetyyppimalliString"));
        
        mwItemIdColumn.setCellValueFactory(new PropertyValueFactory<Laite, Integer>("id"));
        mwItemModelColumn.setCellValueFactory(new PropertyValueFactory<Laite, String>("laitetyyppimalliString"));
        
        // Täyttää kentät valitun tilauksen tiedoilla
        tilausTW.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                valittuTilaus = tilausTW.getSelectionModel().getSelectedItem();
                customerNameLabel.setText(valittuTilaus.getTilaaja());
                orderCommentFieldLabel.setText(valittuTilaus.getKommentti());
                itemList = valittuTilaus.getObservableLaitteet();
                itemTW.setItems(itemList);
                mwOrderCommentField.setText(null);
            }
        });
        itemTW.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if(valittuLaite!=null) {
                    valittuLaite.setOhjelmistot(itemCommentFieldLabel.getText());
                }
                
                valittuLaite = itemTW.getSelectionModel().getSelectedItem();
                itemCommentFieldLabel.setText(valittuLaite.getOhjelmistot());
            }
        });
        mwTilausTW.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mwValittuTilaus = mwTilausTW.getSelectionModel().getSelectedItem();
                mwCustomerNameLabel.setText(mwValittuTilaus.getTilaaja());
                mwOrderCommentField.setText(mwValittuTilaus.getKommentti());
                mwItemList = mwValittuTilaus.getObservableLaitteet();
                mwItemTW.setItems(mwItemList);
                mwItemCommentField.setText(null);
            }
        });
        mwItemTW.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if(mwValittuLaite!=null) {
                    mwValittuLaite.setOhjelmistot(mwItemCommentField.getText());
                }
                
                mwValittuLaite = mwItemTW.getSelectionModel().getSelectedItem();
                mwItemCommentField.setText(mwValittuLaite.getOhjelmistot());
            }
        });
    }
    
    private void tilausTWPaivitys() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, Integer>("id"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("tilaaja"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("statusString"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("priorityString"));
        tilausTW.setItems(tilausList = MainApp.dao.readVapaatTilaukset());
    }
    
    private void mwTilausTWPaivitys() {
        mwOrderIdColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, Integer>("id"));
        mwCustomerColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("tilaaja"));
        mwStatusColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("statusString"));
        mwPriorityColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("priorityString"));
        mwTimeColumn.setCellValueFactory(new PropertyValueFactory<Tilaus, String>("kulutettuAikaString"));
        mwTilausTW.setItems(MainApp.dao.readTyontekTilaukset(SceneController.logUser.getId()));
    }

}
