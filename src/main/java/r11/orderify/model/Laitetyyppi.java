/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r11.orderify.model;

import javax.persistence.*;

/**
 * Laitetyyppiluokka
 * @author Samuli Juppi
 * @author Markus Nousiainen
 * @author Kiril Tsereh
 */
@Entity
@Table(name="laitetyyppi")
public class Laitetyyppi {
    
    @GeneratedValue
    @Id
    @Column(name="id")
    private int id;
    
    @Column(name="merkki")
    private String merkki;
    
    @Column(name="malli")
    private String malli;
    
    @Column(name="ominaisuudet")
    private String ominaisuudet;
    
    
    public Laitetyyppi() {
        
    }
    
    /**
     * Luodaan uusi laitetyyppi
     * @param merkki Laitetyypin merkki
     * @param malli Laitetyypin malli
     * @param ominaisuudet Laitetyypin ominaisuudet
     */
    public Laitetyyppi(String merkki, String malli, String ominaisuudet) {
        this.merkki = merkki;
        this.malli = malli;
        this.ominaisuudet = ominaisuudet;
    }

    public int getId() {
        return id;
    }

    public String getMerkki() {
        return merkki;
    }

    public String getMalli() {
        return malli;
    }

    public String getOminaisuudet() {
        return ominaisuudet;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMerkki(String merkki) {
        this.merkki = merkki;
    }

    public void setMalli(String malli) {
        this.malli = malli;
    }

    public void setOminaisuudet(String ominaisuudet) {
        this.ominaisuudet = ominaisuudet;
    }
    
    
    
    
}
