package org.example;

import java.io.*;
import java.util.*;

public class CSVreader {
    public static List<User> loadUsersFromCSV(String filePath) throws IOException {
        List<User> users = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean isFirst = true;

        while ((line = reader.readLine()) != null) {
            if (isFirst) {
                isFirst = false; // Skip header
                continue;
            }
            String[] parts = line.split(",");
            if (parts.length != 4) continue;

            String name = parts[0];
            String subDate = parts[1];
            String expDate = parts[2];
            boolean isActive = Boolean.parseBoolean(parts[3]);

            users.add(new User(name, subDate, expDate, isActive));
        }
        reader.close();
        return users;
    }
}

