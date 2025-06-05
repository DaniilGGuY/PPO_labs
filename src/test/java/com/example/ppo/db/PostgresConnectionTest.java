package com.example.ppo.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.sql.DataSource;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostgresConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testPostgresConnection() throws SQLException {
        try (var connection = dataSource.getConnection()) {
            assertTrue(connection.isValid(2));
            assertEquals("PostgreSQL", connection.getMetaData().getDatabaseProductName());
        }
    }
}