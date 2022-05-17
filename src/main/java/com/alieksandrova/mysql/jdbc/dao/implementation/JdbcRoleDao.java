package com.alieksandrova.mysql.jdbc.dao.implementation;

import com.alieksandrova.mysql.jdbc.dao.RoleDao;
import com.alieksandrova.mysql.jdbc.entyties.Role;
import com.alieksandrova.mysql.jdbc.util.DbUtil;
import com.alieksandrova.mysql.jdbc.util.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.CREATE_ROLE;
import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.DELETE_ROLE;
import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.FIND_ALL_ROLES;
import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.FIND_BY_ID_ROLE;
import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.UPDATE_ROLE;
import static com.alieksandrova.mysql.jdbc.constants.DaoConstants.FIND_ROLE_BY_NAME;


public class JdbcRoleDao extends GenericJdbcDao<Role> implements RoleDao<Role> {

    private static final Logger LOG = LogManager.getLogger(JdbcRoleDao.class);

    @Override
    public Role findByName(String name) {
        try (Connection connection = DbUtil.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ROLE_BY_NAME)) {
            if (resultSet.next()) {
                return parseResultSet(resultSet).listIterator().next();
            } else throw new NoSuchElementException("Such an element isn't found. ");

        } catch (SQLException e) {
            LOG.error("Selecting statement isn't executed. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    void prepareStatement(Operation operation, PreparedStatement statement, Role object) throws SQLException {
        switch (operation) {
            case CREATE:
                statement.setString(1, object.getRole());
                break;
            case UPDATE:
                statement.setString(1, object.getRole());
                statement.setInt(2, object.getId());
                break;
            case DELETE:
                statement.setInt(1, object.getId());
                break;
        }
    }

    @Override
    List<Role> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Role> allRoles = new ArrayList<>();
        while (resultSet.next()) {
            Role role = new Role(resultSet.getInt("id"), resultSet.getString("name"));
            allRoles.add(role);
        }
        return allRoles;
    }


    @Override
    String getCreateQuery() {
        return CREATE_ROLE;
    }

    @Override
    String getUpdateQuery() {
        return UPDATE_ROLE;
    }

    @Override
    String getDeleteQuery() {
        return DELETE_ROLE;
    }

    @Override
    String getSelectQueryById() {
        return FIND_BY_ID_ROLE;
    }

    @Override
    String getSelectQueryFindAll() {
        return FIND_ALL_ROLES;
    }
}
