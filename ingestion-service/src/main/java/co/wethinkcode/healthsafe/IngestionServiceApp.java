package co.wethinkcode.healthsafe;

import io.javalin.Javalin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class IngestionServiceApp {

    public static void main(String[] args) throws IllegalAccessException, IOException {
        Javalin app = Javalin.create().start(7030);

        app.get("/health", ctx -> ctx.result("OK"));
        List<Ward> wards = loadWards();
        app.get("/wards", ctx -> ctx.json(wards));

        // TODO: read and clean src/main/resources/wards-outdated.csv (wards, wings, specialist departments data —
        // trim whitespace, fix casing, normalize dates/booleans) and expose the
        // cleaned records here for the other services to consume.

    }

    private static List<Ward> loadWards(){
        InputStream stream = IngestionServiceApp.class
                .getResourceAsStream("/wards-outdated.csv");
        if (stream == null){
            throw new IllegalStateException("wards-outdated.csv not found in resources");
        }

        WardCsvReader reader = new WardCsvReader();
        List<String[]> rows =   reader.readLineByLine(new InputStreamReader(stream));

        CsvCleaner cleaner = new CsvCleaner();
        List<Ward> wards = new ArrayList<>();
        for (String[] row : rows){
            wards.add(cleaner.cleanRow(row));
        }
        return new WardDeduplicator().deduplicate(wards);
    }
}
