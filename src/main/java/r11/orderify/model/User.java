package r11.orderify.model;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Käyttäjäluokka
 * @author Samuli Juppi
 * @author Markus Nousiainen
 * @author Kiril Tsereh
 */
@Entity
@Table(name="user")
@Inheritance(strategy=InheritanceType.JOINED)
public class User implements Serializable {

	@GeneratedValue
	@Id
	@Column(name="id")
	private int id;

	@Column(name="etunimi")
	private String etunimi;

	@Column(name="sukunimi")
	private String sukunimi;

	@Column(name="tunnus")
	private String tunnus;

	@Column(name="salasana")
	private String salasana;

	@Column(name="esimies_status")
	private int esimies_status;

	public User() {
	}

        /**
         * Käyttäjän luonti
         * @param etunimi Käyttäjän etunimi
         * @param sukunimi Käyttäjän sukunimi
         * @param tunnus Käyttäjän käyttääjätunnus
         * @param pw Käyttäjän salasana
         * @param status Käyttäjän asema: 0 = työntekijä, 1 = esimies.
         */
	public User(String etunimi, String sukunimi, String tunnus, String pw, int status) {
            //super();
//		this.tyontekijaId = id;		// GeneratedValue
            this.etunimi = etunimi;
            this.sukunimi = sukunimi;
            this.tunnus = tunnus;
            this.salasana = pw;
            this.esimies_status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int tyontekijaId) {
		this.id = tyontekijaId;
	}

	public String getEtunimi() {
		return etunimi;
	}

	public void setEtunimi(String etunimi) {
		this.etunimi = etunimi;
	}

	public String getSukunimi() {
		return sukunimi;
	}

	public void setSukunimi(String sukunimi) {
		this.sukunimi = sukunimi;
	}

	public String getTunnus() {
		return tunnus;
	}

	public void setTunnus(String tunnus) {
		this.tunnus = tunnus;
	}

	public String getSalasana() {
		return salasana;
	}

	public void setSalasana(String salasana) {
		this.salasana = salasana;
	}

	public int getStatus() {
		return this.esimies_status;
	}




}
