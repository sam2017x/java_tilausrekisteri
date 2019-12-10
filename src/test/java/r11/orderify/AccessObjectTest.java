package r11.orderify;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.collections.ObservableList;
import r11.orderify.model.AccessObject;
import r11.orderify.model.Esimies;
import r11.orderify.model.Tilaus;
import r11.orderify.model.User;

public class AccessObjectTest {

    private static AccessObject dao = new AccessObject();

    /**
     * Käyttäjän lisäämisen testi
     */
    @Test
    public void testAddUser(){
        User ab = new Esimies("test", "test", "test", "test");
        boolean temp = dao.addUser(ab);
        Tilaus hg = new Tilaus("test", "test", 2);
        boolean hehe = dao.addTilaus(hg);
        ObservableList<Tilaus> hhg = dao.readTilaukset();
        User hae = dao.readUser("test");
        ObservableList<Tilaus> hhgf = dao.readTyontekTilaukset(2);
        ObservableList<Tilaus> hhgh = dao.readVapaatTilaukset();
        assertEquals("Testing ", temp, true);

    }


}
