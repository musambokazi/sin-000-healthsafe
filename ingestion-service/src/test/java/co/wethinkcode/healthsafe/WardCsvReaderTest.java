package co.wethinkcode.healthsafe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WardCsvReaderTest {
    private WardCsvReader reader;

    @BeforeEach
    void setUp(){
        reader = new WardCsvReader();
    }

    @Test
    void skipHeaderAndReadsOneRowPerLine(){
        String line1 = "ward_id, Wing ,department,beds_available";
        String line2 = "W-01, East Wing ,Cardiology,3";
        String line3 = "W-04,North Wing,Oncology,-1";
        String fake = String.join("\n", line1, line2, line3);
        List<String[]> rows = reader.readLineByLine(new StringReader(fake));
        assertEquals(2, rows.size());
        assertEquals("W-01", rows.get(0)[0]);
    }

    @Test
    void emptyFileGivesEmptyRows(){
        String fake = "";
        List<String[]> row = reader.readLineByLine(new StringReader(fake));
        assertEquals(0, row.size());
    }


}
