package r11.orderify.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.*;

/**
 * Työntekijäluokka
 * @author Samuli Juppi
 * @author Markus Nousiainen
 * @author Kiril Tsereh
 */
@Entity
@Table(name="tyontekija")
@PrimaryKeyJoinColumn(name="id")
public class Tyontekija extends User implements Serializable{

    @GeneratedValue
    @Id
    @Column(name="id")
    private int id;

    @Column(name="hoidetut_laitteet_lkm")
    private int hoidetutLaitteet;

    @Column(name="hoidetut_tilaukset_lkm")
    private int hoidetutTilaukset;

    @Column(name="tyohon_kaytetty_aika")
    private int tyoMinuutit;
    
    @Column(name="vaiheessa_tilaukset")
    private int vaiheessaTilaukset;
    
    @OneToMany(mappedBy = "tyontekija", orphanRemoval = false, cascade = CascadeType.PERSIST)
    private List<Tilaus> tilaukset = new ArrayList<Tilaus>();
    
    @Transient  //Muuttuja jota ei ole tietokannassa
    private String fullName;
    
    @Transient
    private String tyoaika;
    
    @Transient
    private double timeOrderAvg;
    
    @Transient
    private double timeItemAvg;

    public Tyontekija() {
            super();
    }

    /**
     * Työntekijän luonti
     * @param etunimi Työntekijän etunimi
     * @param sukunimi Työntekijän sukunimi
     * @param tunnus Työntekijän käyttäjätunnus
     * @param pw Työntekijän salasana
     */
    public Tyontekija(String etunimi, String sukunimi, String tunnus, String pw) {
            super(etunimi, sukunimi, tunnus, pw, 0);
    }
    
    public void setFullName() {
        fullName = getEtunimi() + " " + getSukunimi();
    }
    
    public String getFullName() {
        return fullName;
    }

    public int getHoidetutLaitteet() {
        return hoidetutLaitteet;
    }

    public void setHoidetutLaitteet(int hoidetutLaitteet) {
        this.hoidetutLaitteet = hoidetutLaitteet;
    }

    public int getHoidetutTilaukset() {
        return hoidetutTilaukset;
    }

    public void setHoidetutTilaukset(int hoidetutTilaukset) {
        this.hoidetutTilaukset = hoidetutTilaukset;
    }

    public int getTyoMinuutit() {
        return tyoMinuutit;
    }

    public void setTyoMinuutit(int tyoMinuutit) {
        this.tyoMinuutit = tyoMinuutit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setVaiheessaTilaukset(int vaiheessaTilaukset) {
        this.vaiheessaTilaukset = vaiheessaTilaukset;
    }

    public int getVaiheessaTilaukset() {
        return vaiheessaTilaukset;
    }

    public String getTyoaika() {
        return tyoaika;
    }

    public void setTyoaika() {
        int tyoaikaH = tyoMinuutit/60;
        int tyoaikaMin = tyoMinuutit;
        tyoaikaMin -= (tyoaikaH*60);
        if(tyoaikaMin<10) {
            tyoaika = tyoaikaH + ":0" + tyoaikaMin;
        } else {
            tyoaika = tyoaikaH + ":" + tyoaikaMin;
        }
    }
    
    public void setTimeOrderAvg() {
        double tunnit = tyoMinuutit/60;
        timeOrderAvg = hoidetutTilaukset/tunnit;
    }
    
    public double getTimeOrderAvg() {
        return timeOrderAvg;
    }
    
    public void setTimeItemAvg() {
        double tunnit = tyoMinuutit/60;
        timeItemAvg = hoidetutLaitteet/tunnit;
    }
    
    public double getTimeItemAvg() {
        return timeItemAvg;
    }
    
    public List<Tilaus> getTilaukset() {
        return tilaukset;
    }
    
    public ObservableList<Tilaus> getObservableTilaukset() {
        ObservableList<Tilaus> observableTilaukset = FXCollections.observableArrayList();
        for(Tilaus tilaus : tilaukset) {
            tilaus.setPriorityString();
            tilaus.setLaiteMaara();
            tilaus.setKulutettuAika();
            observableTilaukset.add(tilaus);
        }
        return observableTilaukset;
    }
    
    

}
