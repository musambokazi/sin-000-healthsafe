package co.wethinkcode.healthsafe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AlertLevelTest {

    @Test
    void zeroIsValid() {
        assertTrue(AlertLevel.isValidLevel(0));
    }

    @Test
    void eightIsValid() {
        assertTrue(AlertLevel.isValidLevel(8));
    }

    @Test
    void minusOneIsInvalid() {
        assertFalse(AlertLevel.isValidLevel(-1));
    }

    @Test
    void nineIsInvalid() {
        assertFalse(AlertLevel.isValidLevel(9));
    }
}