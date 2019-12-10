package r11.orderify;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Testisarja kokoaa kaikki testit yhdeksi testisarjaksi, jolloin
// ne voidaan helposti ajaa kaikki yhdellä käynnistyksellä.

@RunWith(Suite.class)
@Suite.SuiteClasses({
    r11.orderify.AccessObjectTest.class,
    r11.orderify.EsimiesTest.class,
    r11.orderify.LaiteTest.class,
    r11.orderify.TyontekijaTest.class,
})

public class TestSuite {
}
