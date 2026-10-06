package co.wethinkcode.healthsafe;

import io.javalin.Javalin;

import java.io.*;
import java.util.Scanner;

public class IngestionServiceApp {

    public static void main(String[] args) throws IllegalAccessException, IOException {
        Javalin app = Javalin.create().start(7030);

        app.get("/health", ctx -> ctx.result("OK"));

        // TODO: read and clean src/main/resources/wards-outdated.csv (wards, wings, specialist departments data —
        // trim whitespace, fix casing, normalize dates/booleans) and expose the
        // cleaned records here for the other services to consume.

        InputStream inputStream = IngestionServiceApp.class.getClassLoader().getResourceAsStream("wards-outdated.csv");
        if (inputStream == null){
            throw new IllegalAccessException("File not found");
        }
        Scanner scanner = new Scanner(inputStream);


    }
}
