package co.wethinkcode.healthsafe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StaffingRulesTest {
    @Test
    void levelZeroNeedsTwoDoctors() {
        assertEquals(2, StaffingRules.doctorsOnCall(0));
    }

    @Test
    void levelThreeNeedsFiveDoctors() {
        assertEquals(5, StaffingRules.doctorsOnCall(3));
    }

    @Test
    void levelEightNeedsTenDoctors() {
        assertEquals(10, StaffingRules.doctorsOnCall(8));
    }

    @Test
    void levelMinusOneIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> StaffingRules.doctorsOnCall(-1));
    }

    @Test
    void levelNineIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> StaffingRules.doctorsOnCall(9));
    }
}
