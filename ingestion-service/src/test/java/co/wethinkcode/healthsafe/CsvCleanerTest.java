package co.wethinkcode.healthsafe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvCleanerTest {
    private CsvCleaner cleaner;

    @BeforeEach
    void setUp(){
        cleaner = new CsvCleaner();
    }

    @Test
    void trailingWhiteSpaceCleaned(){
        String dirty = " w-05 ";
        String clean = cleaner.stripWhiteSpaces(dirty);
        assertEquals("w-05", clean);
    }

}
