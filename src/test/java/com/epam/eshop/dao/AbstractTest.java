package com.epam.eshop.dao;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;


/**
 * Created by artch on 13.10.2020.
 */
public class AbstractTest {

    private static final String JDBC_DRIVER = "org.h2.Driver";

    private static final String URL_CONNECTION = "jdbc:h2:mem:eshop;user=youruser;password=yourpassword;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false";

    protected static Connection connection;


    @BeforeClass
    public static void setUp() throws Exception {
        Class.forName(JDBC_DRIVER);

        connection = DriverManager.getConnection(URL_CONNECTION);

        try (Statement statement = connection.createStatement()) {
            statement.execute(getSQL("/sql/CreateDBforH2.sql"));
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute(getSQL("/sql/eshop_data.sql"));
        }

    }

    private static String getSQL(String fileName) {
        StringBuilder createTableQuery = new StringBuilder();

        try (Scanner scanner = new Scanner(AbstractTest.class.getResourceAsStream(fileName))) {

            while (scanner.hasNextLine()) {
                createTableQuery.append(scanner.nextLine())
                        .append(" ");
            }
        }

        return createTableQuery.toString();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if (!connection.isClosed()){
            try (Statement statement = connection.createStatement()) {
                statement.execute("drop table users, user_statuses, user_roles, " +
                        "order_statuses, orders, products, orders_has_products, monitor_products, " +
                        "keyboard_products, user_settings, cart;");
            }
        }
    }
}
