package com.alieksandrova.mysql.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.CREATE_TABLE_USERS;
import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.CREATE_TABLE_ROLES;

public class TableUtil {
    public static void createTableUser(Table table) {
        try (Connection connection = DbUtil.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            switch (table) {
                case ROLES -> statement.executeUpdate(CREATE_TABLE_ROLES);
                case USERS -> statement.executeUpdate(CREATE_TABLE_USERS);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

