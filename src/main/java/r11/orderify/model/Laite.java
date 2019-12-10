/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r11.orderify.model;

import javax.persistence.*;

/**
 * Laiteluokka
 * @author Samuli Juppi
 * @author Markus Nousiainen
 * @author Kiril Tsereh
 */
@Entity
@Table(name="laite")
public class Laite {
    
    @GeneratedValue
    @Id
    @Column(name="id")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "tilaus_id")
    private Tilaus tilaus;
    
    @ManyToOne
    private Laitetyyppi laitetyyppi;
    
    @Column(name="asennetut_ohjelmistot")
    private String ohjelmistot;
    
    @Transient  //Muuttuja jota ei ole tietokannassa
    private String laitetyyppimalliString;
    
    
    
    public Laite() {
        
    }
    
    /**
     * Luodaan uusi laite
     * @param tilaus Tilaus johon laite kuuluu
     * @param ohjelmistot Laitteeseen asennettavat ohjelmistot
     * @param laitetyyppi Laitteen laitetyyppi
     */
    public Laite(Tilaus tilaus, String ohjelmistot, Laitetyyppi laitetyyppi) {
        this.tilaus = tilaus;
        this.ohjelmistot = ohjelmistot;
        this.laitetyyppi = laitetyyppi;
    }
    
    public void setLaitetyyppi(Laitetyyppi laitetyyppi) {
        this.laitetyyppi = laitetyyppi;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTilaus(Tilaus tilaus) {
        this.tilaus = tilaus;
    }

    public void setOhjelmistot(String ohjelmistot) {
        this.ohjelmistot = ohjelmistot;
    }
    
    public Laitetyyppi getLaitetyyppi() {
        return laitetyyppi;
    }

    public int getId() {
        return id;
    }

    public Tilaus getTilaus() {
        return tilaus;
    }

    public String getOhjelmistot() {
        return ohjelmistot;
    }

    public String getLaitetyyppimalliString() {
        return laitetyyppimalliString;
    }

    public void setLaitetyyppimalliString() {
        this.laitetyyppimalliString = this.laitetyyppi.getMalli();
    }
    
    
}
