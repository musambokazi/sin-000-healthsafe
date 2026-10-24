package co.wethinkcode.healthsafe;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class WardCsvReader {

    public List<String[]> readLineByLine(Reader reader) {
        try (CSVReader csvReader = new CSVReader(reader)){
            csvReader.skip(1);
            return csvReader.readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Could not read CSV",e);
        }

    }
}
