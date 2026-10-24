package co.wethinkcode.healthsafe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        String clean = cleaner.toLowerCase(dirty);
        assertEquals("w-01", clean);
    }

    @Test
    void cleanBedValueKeepsValidNumbers(){
        String dirtyBed = "0";
        String cleanBed = cleaner.cleanBedValue(dirtyBed);
        assertEquals("0",cleanBed);
    }

    @Test
    void keepValidNumbers(){
        String dirty = "3";
        String clean = cleaner.cleanBedValue(dirty);
        assertEquals("3", clean);
    }

    @Test
    void negativeBedValueIsRejected(){
        String dirty = "-1";
        String clean = cleaner.cleanBedValue(dirty);
        assertNull(clean);
    }

    @Test
    void wordInsteadOfNumberIsRejected(){
        String badBedValue = "five";
        String bedCleanValue = cleaner.cleanBedValue(badBedValue);
        assertNull(bedCleanValue);
    }

    @Test
    void naBedValueIsRejected(){
        String dirtyBed = "N/A";
        String cleanBed = cleaner.cleanBedValue(dirtyBed);
        assertNull(cleanBed);
    }

    @Test
    void emptyBedValueIsRejected(){
        String dirty = "";
        String clean = cleaner.cleanBedValue(dirty);
        assertNull(clean);
    }

    @Test
    void removesDoubleSpacesInside(){
        String dirty = "South  Wing";
        String clean = cleaner.removeExtraSpaces(dirty);
        assertEquals("South Wing", clean);
    }

    @Test
    void titleCaseAllLowercaseWords(){
        assertEquals("East Wing", cleaner.toTitleCase("east wing"));
    }

    @Test
    void titleCaseAllCapitalsWord(){
        String clean = cleaner.toTitleCase("PAEDIATRICS");
        assertEquals("Paediatrics", clean);
    }

    @Test
    void titleCaseOfEmptyTextIsEmpty(){
        assertEquals("", cleaner.toTitleCase(""));
    }
}
