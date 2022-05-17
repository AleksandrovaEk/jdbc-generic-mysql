package com.alieksandrova.mysql.jdbc.dao;


import com.alieksandrova.mysql.jdbc.entyties.User;

public interface UserDao<User> extends Dao<User> {

    User findByEmail(String email);

}