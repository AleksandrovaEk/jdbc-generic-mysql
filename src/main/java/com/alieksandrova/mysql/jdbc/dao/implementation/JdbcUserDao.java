package com.alieksandrova.mysql.jdbc.dao.implementation;

import com.alieksandrova.mysql.jdbc.dao.UserDao;
import com.alieksandrova.mysql.jdbc.entyties.User;
import com.alieksandrova.mysql.jdbc.util.DbUtil;
import com.alieksandrova.mysql.jdbc.util.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.CREATE_USER;
import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.UPDATE_USER;
import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.DELETE_USER;
import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.FIND_BY_ID_USER;
import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.FIND_ALL_USERS;
import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.FIND_USER_BY_EMAIL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class JdbcUserDao extends GenericJdbcDao<User> implements UserDao<User> {

    private static final Logger LOG = LogManager.getLogger(JdbcUserDao.class);

    @Override
    public User findByEmail(String email) {
        try (Connection connection = DbUtil.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_USER_BY_EMAIL)) {
            if (resultSet.next()) {
                return parseResultSet(resultSet).listIterator().next();
            } else throw new NoSuchElementException("Such an element isn't found. ");

        } catch (SQLException e) {
            LOG.error("Selecting statement isn't executed. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    void prepareStatement(Operation operation, PreparedStatement statement, User object) throws SQLException {
        if (operation == Operation.DELETE) {
            statement.setInt(1, object.getId());
        } else {
            statement.setString(1, object.getEmail());
            statement.setString(2, object.getPassword());
            statement.setString(3, object.getFirstName());
            statement.setString(4, object.getLastName());
            statement.setDate(5, object.getBirthday());
            statement.setInt(6, object.getRole());
            if (operation == Operation.UPDATE) {
                statement.setInt(7, object.getId());
            }
        }
    }

    @Override
    List<User> parseResultSet(ResultSet resultSet) throws SQLException {
        List<User> allUsers = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User(resultSet.getInt("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getDate("birthday"),
                    resultSet.getInt("role"));
            allUsers.add(user);
        }
        return allUsers;
    }

    @Override
    String getCreateQuery() {
        return CREATE_USER;
    }

    @Override
    String getUpdateQuery() {
        return UPDATE_USER;
    }

    @Override
    String getDeleteQuery() {
        return DELETE_USER;
    }

    @Override
    String getSelectQueryById() {
        return FIND_BY_ID_USER;
    }

    @Override
    String getSelectQueryFindAll() {
        return FIND_ALL_USERS;
    }
}
