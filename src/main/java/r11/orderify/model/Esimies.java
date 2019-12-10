package r11.orderify.model;

import javax.persistence.*;

/**
 * Esimiesluokka
 * @author Samuli Juppi
 * @author Markus Nousiainen
 * @author Kiril Tsereh
 */
@Entity
@Table(name="esimies")
@PrimaryKeyJoinColumn(name="id")
public class Esimies extends User{

    public Esimies() {
            super();
    }

    /**
     * Luodaan uusi esimies
     * @param etunimi Esimiehen etunimi
     * @param sukunimi Esimiehen sukunimi
     * @param tunnus Esimiehen käyttäjätunnus
     * @param pw Esimiehen salasana
     */
    public Esimies(String etunimi, String sukunimi, String tunnus, String pw) {
            super(etunimi, sukunimi, tunnus, pw, 1);
    }

}
