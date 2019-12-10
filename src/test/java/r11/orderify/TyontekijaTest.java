package r11.orderify;

import r11.orderify.model.Tyontekija;
import r11.orderify.model.User;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TyontekijaTest {

	private static Tyontekija tt = new Tyontekija("test", "test", "test", "test");
	private static User haha = new Tyontekija();

        /**
         * Työntekijän id muutujan testi
         */
	@Test
	public void testId(){
		tt.setId(9);
		int tulos = tt.getId();
		assertEquals("set/get ID ", 9, tulos);
	}
        /**
         * Työntekijän etunimi muuttujan testi
         */
	@Test
	public void testEtunimi(){
		tt.setEtunimi("test");
		String tulos = tt.getEtunimi();
		assertEquals("set/get Etunimi ", "test", tulos);
	}
        /**
         * Työntekijän sukunimi muutujan testi
         */
	@Test
	public void testSukunimi(){
		tt.setSukunimi("test");
		String tulos = tt.getSukunimi();
		assertEquals("set/get Sukunimi ", "test", tulos);
	}
        /**
         * Työntekijän tunnus muutujan testi
         */
	@Test
	public void testTunnus(){
		tt.setTunnus("test");
		String tulos = tt.getTunnus();
		assertEquals("set/get Tunnus ", "test", tulos);
	}
        /**
         * Työntekijän salasana muutujan testi
         */
	@Test
	public void testPw(){
		tt.setSalasana("test");
		String tulos = tt.getSalasana();
		assertEquals("set/get pw ", "test", tulos);
	}
        /**
         * Työntekijän status muutujan testi
         */
	@Test
	public void testStatus(){
		int tulos = tt.getStatus();
		assertEquals("set/get status ", 0, tulos);
	}
        /**
         * Työntekijän laite muutujan testi
         */
	@Test
	public void testLaitteet(){
		tt.setHoidetutLaitteet(1);
		int tulos = tt.getHoidetutLaitteet();
		assertEquals("set/get laitteet ", 1, tulos);
	}
        /**
         * Työntekijän hoidettujen tilausten muutujan testi
         */
	@Test
	public void testTilaukset(){
		tt.setHoidetutTilaukset(1);
		int tulos = tt.getHoidetutTilaukset();
		assertEquals("set/get tilaukset ", 1, tulos);
	}
        /**
         * Työntekijän työminuutti muutujan testi
         */
	@Test
	public void testMinuutit(){
		tt.setTyoMinuutit(1);
		int tulos = tt.getTyoMinuutit();
		assertEquals("set/get minsat ", 1, tulos);
	}


}
