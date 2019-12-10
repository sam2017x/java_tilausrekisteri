 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package r11.orderify.model;

import static java.lang.Math.toIntExact;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import r11.orderify.controller.SceneController;

/**
 * AccessObject tietokantayhteyttä varten
 * @author Samuli Juppi
 * @author Markus Nousiainen
 * @author Kiril Tsereh
 */
public class AccessObject {

    SessionFactory factory;

    private AccessObject(){
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        factory = cfg.buildSessionFactory();
        //Session session = factory.openSession();
        //Transaction t = session.beginTransaction();
    }
    
    private static AccessObject INSTANCE = null;
    
    public static AccessObject getInstance() {
        if(INSTANCE==null) {
            INSTANCE = new AccessObject();
        }
        return INSTANCE;
    }

    /**
     * Vie uuden käyttäjän tietokantaan
     * @param tt Vietävä käyttäjä
     * @return Tietokantaan viemisen onnistumisesta kertova boolean
     */
    public boolean addUser(User tt) {
        Session session = factory.openSession();
        Transaction transaction = null;
        boolean success = true;

        try {
            transaction = session.beginTransaction();
            session.persist(tt);
            transaction.commit();
        }
        catch (Exception e){
            if (transaction!=null) transaction.rollback();
            success = false;
        }
        finally {
            session.close();
        }

        return success;
    }
    
    /**
     * Vie uuden laitteen/itemin tietokantaan
     * @param laite Vietävä laite
     * @return Tietokantaan viemisen onnistumisesta kertova boolean
     */
    public boolean addLaite(Laite laite) {
        Session session = factory.openSession();
        Transaction transaction = null;
        boolean success = true;

        try {
            transaction = session.beginTransaction();
            session.persist(laite);
            transaction.commit();
        }
        catch (Exception e){
            if (transaction!=null) transaction.rollback();
            success = false;
        }
        finally {
            session.close();
        }

        return success;
    }

    /**
     * Vie uuden tilauksen tietokantaan
     * @param tilaus Vietävä tilaus
     * @return Tietokantaan viemisen onnistumisesta kertova boolean
     */
    public boolean addTilaus(Tilaus tilaus) {
        Session session = factory.openSession();
        Transaction transaction = null;
        boolean success = true;

        try {
                transaction = session.beginTransaction();
                session.persist(tilaus);
                transaction.commit();
        }
        catch (Exception e){
                if (transaction!=null) transaction.rollback();
                success = false;
        }
        finally {
                session.close();
        }

        return success;
    }
    
    /**
     * Päivittää tilauksen
     * @param tilaus päivitettävä tilaus
     */
    public void updateTilaus(Tilaus tilaus) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
                transaction = session.beginTransaction();
                session.merge(tilaus);
                transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (transaction!=null) transaction.rollback();
        }
        finally {
                session.close();
        }
    }
    
    
    public void updateLaite(Laite laite) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
                transaction = session.beginTransaction();
                session.merge(laite);
                transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (transaction!=null) transaction.rollback();
        }
        finally {
                session.close();
        }
    }
    
    /**
     * Poistaa tilauksen tietokannasta
     * @param tilaus Tietokannasta poistettava tilaus
     * @return Tietokannasta poistamisen onnistumisesta kertova boolean
     */
    public boolean removeTilaus(Tilaus tilaus) {
        Session session = factory.openSession();
        Transaction transaction = null;
        boolean success = true;

        try {
            transaction = session.beginTransaction();
            Object poistettavaTilaus = session.load(Tilaus.class, tilaus.getId());
            session.delete(poistettavaTilaus);
            transaction.commit();
        }
        catch (Exception e){
            if (transaction!=null) transaction.rollback();
            success = false;
        }
        finally {
            session.close();
        }

        return success;
    }
    
    /**
     * Hakee kaikki tilaukset tietokannasta
     * @return Löytyneet tilaukset
     */
    public ObservableList<Tilaus> readTilaukset() {
        ObservableList<Tilaus> tilausList = FXCollections.observableArrayList();
        Session session = factory.openSession();
        List<Tilaus> eList = session.
                createCriteria(Tilaus.class).list();
        for (Tilaus ent : eList) {
            ent.setStatusString();
            ent.setTtNimi();
            ent.setKulutettuAika();
            ent.setPriorityString();
            tilausList.add(ent);
        }
        return tilausList;
    }

    /**
     * Hakee kaikkien laitetyyppien mallit tietokannasta
     * @return Löytyneet laitetyyppimallit
     */
    public ObservableList<String> readLaitetyypimallit() {
        ObservableList<String> laitetyyppiList = FXCollections.observableArrayList();
        Session session = factory.openSession();
        List<Laitetyyppi> eList = session.
                createCriteria(Laitetyyppi.class).list();
        for (Laitetyyppi ent : eList) {
            laitetyyppiList.add(ent.getMalli());
        }
        return laitetyyppiList;
    }
    
    public ArrayList<Laitetyyppi> readLaitetyypit() {
        ArrayList<Laitetyyppi> laitetyyppiList = new ArrayList();
        Session session = factory.openSession();
        List<Laitetyyppi> eList = session.
                createCriteria(Laitetyyppi.class).list();
        for (Laitetyyppi ent : eList) {
            laitetyyppiList.add(ent);
        }
        return laitetyyppiList;
    }
    
    /**
     * Hakee kaikki työntekijät tietokannasta
     * @return Löytyneet työntekijät
     */
    public ObservableList<Tyontekija> readTyontekijat() {
        ObservableList<Tyontekija> tyontekijaList = FXCollections.observableArrayList();
        Session session = factory.openSession();
        List<Tyontekija> eList = session.
                createCriteria(Tyontekija.class).list();
        for (Tyontekija ent : eList) {
            ent.setFullName();
            ent.setTyoaika();
            ent.setTimeOrderAvg();
            ent.setTimeItemAvg();
            tyontekijaList.add(ent);
        }
        return tyontekijaList;
    }
    
    /**
     * Liittää kirjautuneen työntekijän tilaukseen
     * @param tilaus Tilaus johon kirjautunut työntekijä liitetään
     * @return Tilaus johon kirjautunut työntekijä liitettiin
     */
    public Tilaus setTilausTt(Tilaus tilaus) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Tyontekija tt = SceneController.logTt;
            tilaus.setTyontekija(tt);
            tilaus.setStatus(1);
            tilaus.setTyonalleottoAika(LocalDateTime.now());
            session.update(tilaus);
            tt.setVaiheessaTilaukset(tt.getVaiheessaTilaukset()+1);
            session.update(tt);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
        } finally {
            session.close(); 
        }
        return tilaus;
    }
    
    /**
     * Asettaa tilauksen statukseksi "done"
     * @param tilaus Tilaus jonka statusta muutetaan
     * @return Tilaus jonka status muutettiin
     */
    public Tilaus setTilausDone(Tilaus tilaus) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction(); 
            tilaus.setStatus(2);
            tilaus.setValmistumisAika(LocalDateTime.now());
            session.update(tilaus); 
            Tyontekija tt = tilaus.getTyontekija();
            tt.setHoidetutTilaukset(tt.getHoidetutTilaukset()+1);
            tt.setVaiheessaTilaukset(tt.getVaiheessaTilaukset()-1);
            int tilauksenLaitteet = 0;
            for(Laite laite : tilaus.getLaitteet()) {
                tilauksenLaitteet++;
            }
            tt.setHoidetutLaitteet(tt.getHoidetutLaitteet()+tilauksenLaitteet);
            tt.setTyoMinuutit(tt.getTyoMinuutit()+toIntExact(Duration.between(tilaus.getTyonalleottoAika(), LocalDateTime.now()).toMinutes()));
            session.update(tt);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace(); 
        } finally {
            session.close(); 
        }
        return tilaus;
    }
    
    /**
     * Hakee kaikki työntekijäkäyttäjät tietokannasta
     * @return Löydetyt työntekijäkäyttäjät
     */
    public ObservableList<User> readTyontekijaKayttajat() {
        ObservableList<User> tilausList = FXCollections.observableArrayList();
        Session session = factory.openSession();
        List<User> eList = session.createQuery("FROM User AS tt WHERE esimies_status = 0").list();
        for (User ent : eList) {
            tilausList.add(ent);
        }
        return tilausList;
    }

    /**
     * Hakee tietokannasta käyttäjän tunnuksella
     * @param tunnus Tunnus jolla haetaan käyttäjää
     * @return Mahdollisesti löytynyt käyttäjä
     */
    public User readUser(String tunnus) {		// HAKU TUNNUKSELLA
        User tt = null;							// metodilla palautetaan kannasta User, jonka tunnus-muuttuja vastaa haettua
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hqlString = "FROM User AS tt WHERE tunnus = :muuttuja";
            List<User> lista = session.createQuery(hqlString).setParameter("muuttuja", tunnus).list();
            tt = lista.get(0);

            transaction.commit();
        }
        catch (Exception e){
            if (transaction!=null) transaction.rollback();
        }
        finally {
            session.close();
        }

        return tt;	// palauttaa null, jos ei löydy
    }

    /**
     * Hakee tietokannasta työntekijän tunnuksella
     * @param tunnus Tunnus jolla haetaan työntekijää
     * @return Mahdollisesti löytynyt työntekijä
     */
    public Tyontekija readTt(String tunnus) {		// HAKU TUNNUKSELLA
        Tyontekija tt = null;							// metodilla palautetaan kannasta User, jonka tunnus-muuttuja vastaa haettua
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hqlString = "FROM Tyontekija AS tt WHERE tunnus = :muuttuja";
            List<Tyontekija> lista = session.createQuery(hqlString).setParameter("muuttuja", tunnus).list();
            tt = lista.get(0);

            transaction.commit();
        }
        catch (Exception e){
            if (transaction!=null) transaction.rollback();
        }
        finally {
            session.close();
        }

        return tt;	// palauttaa null, jos ei löydy
    }

    /**
     * Hakee tietokannasta työntekijälle kuuluvat tilaukset
     * @param id Työntekijän id jonka tilauksia haetaan
     * @return Lista työntekijälle kuuluvia tilauksia
     */
    public ObservableList<Tilaus> readTyontekTilaukset(int id) {
        ObservableList<Tilaus> tilausList = FXCollections.observableArrayList();
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            String hqlString = "FROM Tilaus WHERE tyontekija_id = :muuttuja";
            List<Tilaus> eList = session.createQuery(hqlString).setParameter("muuttuja", id).list();

            transaction.commit();
            for (Tilaus ent : eList) {
                Hibernate.initialize(ent.getLaitteet());
                ent.setStatusString();
                ent.setKulutettuAika();
                ent.setPriorityString();
                tilausList.add(ent);
            }
        }
        catch (Exception e){
                if (transaction!=null) transaction.rollback();
        }
        finally {
                session.close();
        }
        return tilausList;
    }
    
    /**
     * Hekee tietokannasta tilaukset joihin ei ole liitetty työntekijää
     * @return Lista tilauksista joihin ei ole liitetty työntekijää
     */
    public ObservableList<Tilaus> readVapaatTilaukset() {
        ObservableList<Tilaus> tilausList = FXCollections.observableArrayList();
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            String hqlString = "FROM Tilaus WHERE status = 0";
            List<Tilaus> eList = session.createQuery(hqlString).list();

            transaction.commit();
            for (Tilaus ent : eList) {
                Hibernate.initialize(ent.getLaitteet());
                ent.setStatusString();
                ent.setPriorityString();
                tilausList.add(ent);
            }
        }
        catch (Exception e){
                if (transaction!=null) transaction.rollback();
        }
        finally {
                session.close();
        }
        return tilausList;
    }
    
    /**
     * Hakee tietokannasta tilaukset jotka eivät ole vielä valmistuneet
     * @return Lista tilauksia jotka eivät ole vielä valmistuneet
     */
    public ObservableList<Tilaus> readUndoneTilaukset() {
        ObservableList<Tilaus> tilausList = FXCollections.observableArrayList();
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            String hqlString = "FROM Tilaus WHERE status < 2";
            List<Tilaus> eList = session.createQuery(hqlString).list();

            transaction.commit();
            for (Tilaus ent : eList) {
                Hibernate.initialize(ent.getLaitteet());
                ent.setStatusString();
                ent.setPriorityString();
                tilausList.add(ent);
            }
        }
        catch (Exception e){
                if (transaction!=null) transaction.rollback();
        }
        finally {
                session.close();
        }
        return tilausList;
    }

}
