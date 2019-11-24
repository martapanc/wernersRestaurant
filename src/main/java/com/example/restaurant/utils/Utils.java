package com.example.restaurant.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {

    public static String readInput(String inputFile) {

        BufferedReader reader;
        StringBuilder content = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            while (line != null) {
                content.append(line);
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }
}
