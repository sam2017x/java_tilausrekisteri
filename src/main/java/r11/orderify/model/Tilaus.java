/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r11.orderify.model;

/**
 * Tilausluokka
 * @author Samuli Juppi
 * @author Markus Nousiainen
 * @author Kiril Tsereh
 */
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import r11.orderify.view.MainApp;

@Entity
@Table(name="tilaus")
public class Tilaus {

    @GeneratedValue
    @Id
    @Column(name="id")
    private int id;

    @ManyToOne
    private Tyontekija tyontekija;
    
    @OneToMany(mappedBy = "tilaus", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Laite> laitteet = new ArrayList<Laite>();

    @Column(name="tilaaja")
    private String tilaaja;
    
    @Column(name="kommentti")
    private String kommentti;

    @Column
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @Column(name="valmistumis_aika")
    private LocalDateTime valmistumisAika;

    @Column(name="tyonalleotto_aika")
    private LocalDateTime tyonalleottoAika;

    @Column(name="huomautus_tyontekijalle")
    private String huomautusTyontekijalle;

    @Column(name="tyontekijan_kommentti")
    private String tyontekijanKommentti;

    @Column(name="status")
    private int status;
    
    @Column(name="priority")
    private int priority;
    
    @Transient  //Muuttuja jota ei ole tietokannassa
    private String statusString;
    @Transient
    private String priorityString;
    @Transient  //Muuttuja jota ei ole tietokannassa
    private String ttNimi;
    @Transient
    private long kulutettuAikaH;
    @Transient
    private long kulutettuAikaMin;
    @Transient
    private String kulutettuAikaString;
    @Transient
    private int laiteMaara;

    public Tilaus() {

    }

    /**
     * Luodaan uusi tilaus
     * @param tyontekija Työntekijä jolle tilaus kuuluu
     * @param tilaaja Tilauksen tilaaja
     * @param valmistumisAika Tilauksen valmistumisaika
     * @param tyonalleottoAika Aika jolloin työntekijä otti tilauksen työnalle
     * @param huomautusTyontekijalle Esimiehen kommentti työntekijälle
     * @param tyontekijanKommentti Työntekijän kommentti
     * @param status Tilauksen tila: 0 = "Waiting", 1 = "In progress", 2 = "Done".
     */
    public Tilaus(Tyontekija tyontekija, String tilaaja, LocalDateTime valmistumisAika, LocalDateTime tyonalleottoAika, String huomautusTyontekijalle, String tyontekijanKommentti, int status) {
        this.tilaaja = tilaaja;
        this.valmistumisAika = valmistumisAika;
        this.tyonalleottoAika = tyonalleottoAika;
        this.huomautusTyontekijalle = huomautusTyontekijalle;
        this.tyontekijanKommentti = tyontekijanKommentti;
        this.status = status;
        this.tyontekija = tyontekija;
    }
    
    public Tilaus(String tilaaja, String kommentti, int priority) {
        this.tilaaja = tilaaja;
        this.kommentti = kommentti;
        this.status = 0;
        this.priority = priority;
    }
    
    /**
     * Hakee tilauksen statuksen ja muuttaa sen luettavaan muotoon
     * @return Tilauksen status luettavassa muodossa
     */
    public String getStatusString() {
        String temp = null;
        switch (status) {
            case 0:
                temp = MainApp.local.r.getString("waiting");
                break;
            case 1:
                temp = MainApp.local.r.getString("inProgress");
                break;
            case 2:
                temp = MainApp.local.r.getString("done");
                break;
            default:
                break;
        }
        return temp;
    }
    
    /**
     * Asettaa väliakasen muuttujan arvoksi tilauksen tilan luettavassa muodossa.
     * Tarvitaan esim PropertyValueFactoryn toimintaan.
     */
    public void setStatusString() {
        switch (status) {
            case 0:
                statusString = "waiting";
                break;
            case 1:
                statusString = "inProgress";
                break;
            case 2:
                statusString = "done";
                break;
            default:
                break;
        }
    }
    
    /**
     * Asettaa väliaikaisen muuttujan arvoksi työntekijän nimen jolle tilaus kuuluu.
     * Tarvitaan esim PropertyValueFactoryn toimintaan.
     */
    public void setTtNimi() {
        if(this.getTyontekija()!=null) {
            ttNimi = this.getTyontekija().getEtunimi()+ " " + this.getTyontekija().getSukunimi();
        }
    }

    public int getId() {
        return id;
    }

    public String getTilaaja() {
        return tilaaja;
    }

    public String getKommentti() {
        return kommentti;
    }
    
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public LocalDateTime getValmistumisAika() {
        return valmistumisAika;
    }

    public LocalDateTime getTyonalleottoAika() {
        return tyonalleottoAika;
    }

    public String getHuomautusTyontekijalle() {
        return huomautusTyontekijalle;
    }

    public String getTyontekijanKommentti() {
        return tyontekijanKommentti;
    }

    public Tyontekija getTyontekija() {
        return tyontekija;
    }

    public int getStatus() {
        return status;
    }
    
    public String getTtNimi() {
        return ttNimi;
    }
    
    public int getPriority() {
        return priority;
    }
    
    
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTilaaja(String tilaaja) {
        this.tilaaja = tilaaja;
    }

    public void setValmistumisAika(LocalDateTime valmistumisAika) {
        this.valmistumisAika = valmistumisAika;
    }

    public void setTyonalleottoAika(LocalDateTime tyonalleottoAika) {
        this.tyonalleottoAika = tyonalleottoAika;
    }

    public void setHuomautusTyontekijalle(String huomautusTyontekijalle) {
        this.huomautusTyontekijalle = huomautusTyontekijalle;
    }

    public void setTyontekijanKommentti(String tyontekijanKommentti) {
        this.tyontekijanKommentti = tyontekijanKommentti;
    }

    public void setStatus(int status) {
        // 0 = waiting
        // 1 = in progress
        // 2 = done
        this.status = status;
    }
    
    public void setTyontekija(Tyontekija tt) {
        this.tyontekija = tt;
    }

    public void setKommentti(String kommentti) {
        this.kommentti = kommentti;
    }
    
    public List<Laite> getLaitteet() {
        return laitteet;
    }
    
    public void addToLaitteet(Laite laite) {
        laitteet.add(laite);
    }
    
    public ObservableList<Laite> getObservableLaitteet() {
        ObservableList<Laite> observableLaitteet = FXCollections.observableArrayList();
        for(Laite l : laitteet) {
            l.setLaitetyyppimalliString();
            observableLaitteet.add(l);
        }
        return observableLaitteet;
    }
    
    /**
     * Kun tilausta päivitetään tarkistetaan kaikki tilauksen laitteet ja päivitetään jos laite on jo olemassa ja sitä on muokattu, tai lisätään uusi laite tilaukseen
     * @param laite Tarkistettava laite
     */
    public void addOrUpdateLaite(Laite laite) {
        boolean exists = false;
        for(Laite l : laitteet) {
            if(l.getId()==laite.getId()) {
                exists = true;
                MainApp.dao.updateLaite(laite);
            }
        }
        if(!exists) {
            laite.setTilaus(this);
            MainApp.dao.addLaite(laite);
            laitteet.add(laite);
        }
    }
    
    public void setPriorityString() {
        priorityString = Priority.getStringPriority(this.priority);
    }
    
    public String getPriorityString() {
        return priorityString;
    }
    
    public void setKulutettuAika() {
        if(status>0) {
            if(status==1) {
                kulutettuAikaMin = Duration.between(tyonalleottoAika, LocalDateTime.now()).toMinutes();
            } else {
                kulutettuAikaMin = Duration.between(tyonalleottoAika, valmistumisAika).toMinutes();
            }
            kulutettuAikaH = kulutettuAikaMin/60;
            kulutettuAikaMin -= (kulutettuAikaH*60);
            String kulutettuAikaMinString = "0";
            if(kulutettuAikaMin<10) {
                kulutettuAikaMinString = kulutettuAikaMinString + kulutettuAikaMin;
            } else {
                kulutettuAikaMinString = "" + kulutettuAikaMin;
            }
            kulutettuAikaString = kulutettuAikaH + ":" + kulutettuAikaMinString;
            if(status==1) {
                kulutettuAikaString = kulutettuAikaString + "...";
            }
        }
    }
    
    public long getKulutettuAikaMin() {
        return kulutettuAikaMin;
    }
    
    public String getKulutettuAikaString() {
        return kulutettuAikaString;
    }

    public int getLaiteMaara() {
        return laiteMaara;
    }
    
    public void setLaiteMaara() {
        for(Laite laite : laitteet) {
            laiteMaara++;
        }
    }
    
    
}
