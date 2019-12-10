package r11.orderify.view;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import r11.orderify.model.AccessObject;
import r11.orderify.model.I18n;


/**
 * Ohjelman näkymä
 * @author Samuli Juppi
 * @author Markus Nousiainen
 * @author Kiril Tsereh
 */
public class MainApp extends Application {

    public static AccessObject dao = AccessObject.getInstance();
    
    public static I18n local = new I18n("en", "US");

    public MainApp() {
        /*
        User tt = new Tyontekija("Pekka", "Patka", "jusajusa", "jusajusa"); // Pari testi useria databaseen, voi poistaa myöhemmin tms.
        User bb = new Esimies("Simo", "Simppa", "simosimo", "simosimo");	// username 8 char, password 8-32
        dao.addUser(tt);
        dao.addUser(bb);
        */
    }

    /**
     * The main entry point for all JavaFX applications.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        primaryStage.setMinWidth(390);
        primaryStage.setMinHeight(300);


        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        primaryStage.setTitle(MainApp.local.r.getString("sign"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
