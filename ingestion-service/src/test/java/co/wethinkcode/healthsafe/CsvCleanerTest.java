package co.wethinkcode.healthsafe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void valuesWithCapitalLettersTurnToLowerCases(){
        String dirty = "W-01";
        String clean = cleaner.handleCases(dirty);
        assertEquals("w-01", clean);
    }
}
