package com.Nikita.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    private final String sqlFileForParse = "creatingSQL.sql";

    Scanner scanner;

    public List<String> parse() {
        List<String> sqlMethods = new ArrayList<>();
        {
            try {
                scanner = new Scanner(new File(sqlFileForParse));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            sqlMethods.add(scanner.nextLine());
        }
        return sqlMethods;
    }
}
