package r11.orderify;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import r11.orderify.model.Laite;
import r11.orderify.model.Laitetyyppi;
import r11.orderify.model.Tilaus;
import r11.orderify.model.Tyontekija;
import r11.orderify.model.User;

public class LaiteTest {


	private static Tyontekija tt = new Tyontekija("test", "test", "test", "test");
	private static Tilaus joo = new Tilaus();
	private static Tilaus tilaus = new Tilaus("test", "test", 2);
	private static Laitetyyppi tyyppi = new Laitetyyppi("test", "test", "test");
	private static Laite laite = new Laite(tilaus, "test", tyyppi);
	private static Laitetyyppi yy = new Laitetyyppi();

        /**
         * Tilauksen id muuttujan testi
         */
	@Test
	public void testTilausId(){
		tilaus.setId(9);
		int tulos = tilaus.getId();
		assertEquals("set/get ID ", 9, tulos);
	}
        /**
         * Tilauksen status muuttujan testi
         */
	@Test
	public void testTilausStatus(){
		String aha = tilaus.getStatusString();
		tilaus.setStatus(0);
		tilaus.setStatusString();
		aha = tilaus.getStatusString();
		tilaus.setStatus(1);
		tilaus.setStatusString();
		aha = tilaus.getStatusString();
		tilaus.setStatus(2);
		tilaus.setStatusString();
		aha = tilaus.getStatusString();
		int tulos = tilaus.getStatus();
		String tilaaja = tilaus.getTilaaja();
		assertEquals("set/get Status ", 2, tulos);
	}
        
	@Test
	public void testWhatEver(){
		tilaus.setTilaaja("test");
		tilaus.setHuomautusTyontekijalle("test");
		tilaus.setTyontekijanKommentti("test");
		String jou = tilaus.getHuomautusTyontekijalle();
		jou = tilaus.getTyontekijanKommentti();
		jou = tilaus.getTilaaja();
		tilaus.setTyontekija(tt);
		assertEquals("wow ", "test", jou);
	}

        /**
         * Laitteen ohjelmisto muutujan testi
         */
	@Test
	public void testLaiteT(){
		laite.setId(1);
		laite.setLaitetyyppi(tyyppi);
		laite.setOhjelmistot("test");
		laite.setTilaus(tilaus);
		Laitetyyppi oki = laite.getLaitetyyppi();
		Tilaus tipa = laite.getTilaus();
		int hh = laite.getId();
		String juu = laite.getOhjelmistot();
		assertEquals("Vähän kaikkea Laite/Laitetyyppi ", "test", juu);
	}

        /**
         * Laitetyyppi testi
         */
	@Test
	public void testLaitetyyppi(){
		tyyppi.setId(1);
		tyyppi.setMalli("test");
		tyyppi.setMerkki("test");
		tyyppi.setOminaisuudet("test");
		String jop = tyyppi.getMalli();
		jop = tyyppi.getMerkki();
		jop = tyyppi.getOminaisuudet();
		int jopp = tyyppi.getId();
		assertEquals("tyyppi testi ", 1, jopp);
	}

}
