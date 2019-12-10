package r11.orderify;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import r11.orderify.model.Esimies;
import r11.orderify.model.Tyontekija;
import r11.orderify.model.User;

public class EsimiesTest {

	private static User esimies = new Esimies("test", "test", "test", "test");
	private static User hmm = new Esimies();



        /**
         * Esimiehen id muutujan testi
         */
	@Test
	public void testId(){
		esimies.setId(9);
		int tulos = esimies.getId();
		assertEquals("set/get ID ", 9, tulos);
	}
        /**
         * Esimiehen etunimi muuttujan testi
         */
	@Test
	public void testEtunimi(){
		esimies.setEtunimi("test");
		String tulos = esimies.getEtunimi();
		assertEquals("set/get Etunimi ", "test", tulos);
	}
        /**
         * Esimiehen sukunimi muuttujan testi
         */
	@Test
	public void testSukunimi(){
		esimies.setSukunimi("test");
		String tulos = esimies.getSukunimi();
		assertEquals("set/get Sukunimi ", "test", tulos);
	}
        /**
         * Esimiehen tunnus muutujan testi
         */
	@Test
	public void testTunnus(){
		esimies.setTunnus("test");
		String tulos = esimies.getTunnus();
		assertEquals("set/get Tunnus ", "test", tulos);
	}
        /**
         * Esimiehen salasana muutujan testi
         */
	@Test
	public void testPw(){
		esimies.setSalasana("test");
		String tulos = esimies.getSalasana();
		assertEquals("set/get ID ", "test", tulos);
	}
        /**
         * Esimiehen status muutujan testi
         */
	@Test
	public void testStatus(){
		int tulos = esimies.getStatus();
		assertEquals("set/get ID ", 1, tulos);
	}

}
