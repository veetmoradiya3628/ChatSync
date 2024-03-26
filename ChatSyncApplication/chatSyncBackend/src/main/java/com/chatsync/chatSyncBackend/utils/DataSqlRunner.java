package com.chatsync.chatSyncBackend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class DataSqlRunner implements CommandLineRunner {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        executeDataSql();
    }

    private void executeDataSql() {
        try {
            // Read data.sql file from classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.sql");
            if (inputStream != null) {
                // Execute SQL statements from data.sql
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) { // Skip empty lines
                        jdbcTemplate.execute(line);
                    }
                }
                reader.close();
                System.out.println("data.sql executed successfully");
            } else {
                System.out.println("data.sql not found");
            }
        } catch (IOException e) {
            System.out.println("Error executing data.sql: " + e.getMessage());
        }
    }
}
