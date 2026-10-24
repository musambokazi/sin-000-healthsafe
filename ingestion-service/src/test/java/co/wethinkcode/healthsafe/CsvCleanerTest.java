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
    void zeroBedsIsValid() {
        Integer clean = cleaner.cleanBedValue("0");
        assertEquals(0, clean);
    }

    @Test
    void positiveBedsIsValid() {
        Integer clean = cleaner.cleanBedValue("3");
        assertEquals(3, clean);
    }

    @Test
    void negativeBedValueIsRejected(){
        String dirty = "-1";
        Integer clean = cleaner.cleanBedValue(dirty);
        assertNull(clean);
    }

    @Test
    void wordInsteadOfNumberIsRejected(){
        Integer clean = cleaner.cleanBedValue("five");
        assertNull(clean);
    }

    @Test
    void naBedValueIsRejected(){
        String dirtyBed = "N/A";
        Integer cleanBed = cleaner.cleanBedValue(dirtyBed);
        assertNull(cleanBed);
    }

    @Test
    void emptyBedValueIsRejected(){
        String dirty = "";
        Integer clean = cleaner.cleanBedValue(dirty);
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

    @Test
    void cleansOneMessyRowIntoOneWard() {
        String[] row = {"w-05", "east wing", "PAEDIATRICS", "five"};
        Ward ward = cleaner.cleanRow(row);
        assertEquals("w-05", ward.id());
        assertEquals("East Wing", ward.wing());
        assertEquals("Paediatrics", ward.department());
        assertNull(ward.bedsAvailable());
    }

    @Test
    void cleansMessyIdInRow() {
        String[] row = {" W-03 ", "east wing", "CARDIOLOGY", "0"};
        Ward ward = cleaner.cleanRow(row);
        assertEquals("w-03", ward.id());
        assertEquals("Cardiology", ward.department());
        assertEquals(0, ward.bedsAvailable());
    }
}
