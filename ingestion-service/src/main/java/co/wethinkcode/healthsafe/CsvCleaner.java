package co.wethinkcode.healthsafe;

import java.io.*;
import java.util.Scanner;

public class CsvCleaner {

    public String stripWhiteSpaces(String value) {
        return value.trim();
    }

    public String handleCases(String value) {
        return value.toLowerCase();
    }
}
