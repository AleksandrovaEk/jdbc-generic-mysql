package com.alieksandrova.mysql.jdbc.dao.implementation;

import com.alieksandrova.mysql.jdbc.dao.Dao;

import com.alieksandrova.mysql.jdbc.util.DbUtil;
import com.alieksandrova.mysql.jdbc.util.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class GenericJdbcDao<T> implements Dao<T> {

    private static final Logger LOG = LogManager.getLogger(GenericJdbcDao.class);

    abstract void prepareStatement(Operation operation, PreparedStatement statement, T object) throws SQLException;

    abstract List<T> parseResultSet(ResultSet resultSet) throws SQLException;

    abstract String getCreateQuery();

    abstract String getUpdateQuery();

    abstract String getDeleteQuery();

    abstract String getSelectQueryById();

    abstract String getSelectQueryFindAll();


    @Override
    public void create(T entity) {
        try (Connection connection = DbUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getCreateQuery())) {
            prepareStatement(Operation.CREATE, preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Creating statement isn't executed. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T entity) {
        try (Connection connection = DbUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getUpdateQuery())) {
            prepareStatement(Operation.UPDATE, preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Updating statement isn't executed. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(T entity) {
        try (Connection connection = DbUtil.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getDeleteQuery())) {
            prepareStatement(Operation.DELETE, preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Deleting statement isn't executed. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public T findById(int id) {
        try (Connection connection = DbUtil.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getSelectQueryById())) {
            if (resultSet.next()) {
                return parseResultSet(resultSet).listIterator().next();
            } else throw new NoSuchElementException("Such an element isn't found. ");
        } catch (SQLException e) {
            LOG.error("Selecting statement isn't executed. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> findAll() {
        try (Connection connection = DbUtil.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getSelectQueryFindAll())) {
            if (resultSet.next()) {
                return parseResultSet(resultSet);
            } else throw new RuntimeException("No elements found. ");
        } catch (SQLException e) {
            LOG.error("Selecting statement isn't executed. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
