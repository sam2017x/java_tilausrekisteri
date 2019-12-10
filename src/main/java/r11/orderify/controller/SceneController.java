package r11.orderify.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import r11.orderify.model.Tyontekija;
import r11.orderify.model.User;
import r11.orderify.view.MainApp;

/**
 * Kirjautumisikkunan kontrolleri
 * @author Samuli Juppi
 * @author Markus Nousiainen
 * @author Kiril Tsereh
 */
public class SceneController implements Initializable {


    @FXML
    private Text act;
    @FXML
    private PasswordField passwordField = new PasswordField();
    @FXML
    private TextField usernameField = new TextField();
    
    public static User logUser;
    public static Tyontekija logTt;
    
    //l10n
    @FXML
    private Text lblWelcome;
    @FXML
    private Label lblUserName;
    @FXML
    private Label lblPW;
    @FXML
    private Button btnSign;
    @FXML
    private Button btnGenViev;
    

    /**
     * Tarkistaa syötetyt käyttäjätiedot, jos tiedot täsmäävät, kirjautuu sisään syötetyillä tiedoilla ja avaa kyseiselle käyttäjälle kuuluvan näkymän.
     * Muuten näyttää virheeseen liittyvän virheilmoituksen.
     * @param event kirjautumisnapin klikkaus
     * @throws Exception 
     */
    @FXML
    private void handleSubmitButtonAction(ActionEvent event) throws Exception {
    	String username = usernameField.getText();
    	String password = passwordField.getText();
    	if (username.equals("")) {
	        act.setText("Username can't be blank");
    	} else if (password.equals("")) {
    		act.setText("Password can't be blank");
    	} else if (username.length() == 8 && password.length() >= 8 && password.length() <= 32){
    		logUser = checkUser(username, password);
                
    		if (logUser != null) {
                    if (logUser.getStatus() == 0) {			// työntekijä
                        // screen
                        logTt = MainApp.dao.readTt(logUser.getTunnus());
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/TyontekijaFXML.fxml"));/* Exception */
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle(username+ " - orderify");
                        stage.show();
                    } else if (logUser.getStatus() == 1) {	// esimies
                        //act.setText("Signing in...");
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/EsimiesFXML.fxml"));/* Exception */
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle(username+ " - orderify");
                        stage.show();
                    }
    		}

    	} else {
    		act.setText("Invalid username or password");
    	}
    }
    
    /**
     * Avaa yleiskäyttäjän ikkunan
     * @param event Yleiskäyttäjäikkunan avausnapin klikkaus
     * @throws Exception 
     */
    @FXML
    private void openGeneralView(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/GeneralUserFXML.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(MainApp.local.r.getString("customTitle"));
        stage.show();
    }

    /**
     * Sulkee ohjelman
     * @param close Sulkemisnapin klikkaus
     */
    @FXML
    private void handleButtonAss2(ActionEvent close){
        Node source = (Node) close.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    public void initialize(URL url, ResourceBundle rb) {
        lblWelcome.setText(MainApp.local.r.getString("welcome"));
        lblUserName.setText(MainApp.local.r.getString("username"));
        lblPW.setText(MainApp.local.r.getString("password"));
        btnSign.setText(MainApp.local.r.getString("signing"));
        btnGenViev.setText(MainApp.local.r.getString("GenViev"));

    }

//	public boolean checkPassword(String tunnus, String pw) {
//
//	}

    /**
     * Etsii tietokannasta käyttäjän syötetyllä käyttäjätunnuksella, jonka jälkeen tarkastaa syötetyn salasanan oikeellisuuden.
     * @param tunnus Käyttäjätunnuskenttään syötetty arvo, jolla toivotaan löytävän käyttäjä jolla koitetaan kirjautua
     * @param pw Salasanakenttään syötetty arvo
     * @return Kirjautuvan käyttäjän, jos kentät täytetty oikein
     */
    public User checkUser(String tunnus, String pw) {
        User tarkistettavaUser = null;

        try {
            tarkistettavaUser = MainApp.dao.readUser(tunnus);
            if (tarkistettavaUser != null) {
                if (tarkistettavaUser.getSalasana().equals(pw)) {
                    System.out.println(tarkistettavaUser.getEtunimi());	// test
                    System.out.println(tarkistettavaUser.getSalasana());
                    return tarkistettavaUser;
                }
            }
        } catch (Exception e) {

        }

        return null;
    }

}
