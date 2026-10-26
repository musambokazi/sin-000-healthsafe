package co.wethinkcode.healthsafe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AlertParserTest {
    private final AlertParser parser = new AlertParser();

    @Test
    void goodMessageBecomesAlert() {
        EquipmentAlert alert =
                parser.parse("{\"wardId\":\"w-05\",\"equipment\":\"ventilator\"}");
        assertEquals("w-05", alert.wardId());
        assertEquals("ventilator", alert.equipment());
    }

    @Test
    void textThatIsNotJsonIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("ventilator broke"));
    }

    @Test
    void messageWithoutWardIdIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("{\"equipment\":\"ventilator\"}"));
    }

    @Test
    void messageWithoutEquipmentIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("{\"wardId\":\"w-05\"}"));
    }

    @Test
    void blankWardIdIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("{\"wardId\":\"\",\"equipment\":\"ventilator\"}"));
    }

    @Test
    void blankEquipmentIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("{\"wardId\":\"w-05\",\"equipment\":\"   \"}"));
    }
}
