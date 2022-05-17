package com.alieksandrova.mysql.jdbc.dao;

public interface RoleDao<Role> extends Dao<Role>{
    Role findByName(String name);
}