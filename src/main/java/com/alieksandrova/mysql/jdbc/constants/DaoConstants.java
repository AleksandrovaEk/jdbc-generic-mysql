package com.alieksandrova.mysql.jdbc.constants;

public class DaoConstants {

    //CREATE
    public static final String CREATE_USER = "insert into USERS (email, password, firstname, lastname, birthday, role) VALUES (?, ?, ?, ?, ?, ?);";
    public static final String CREATE_ROLE = "insert into ROLES (name) values (?);";

    //UPDATE
    public static final String UPDATE_USER = "update USERS " +
            "set email = ?, password = ?, firstname = ?, lastname = ?, birthday = ?, role = ?" +
            "where id = ?;";
    public static final String UPDATE_ROLE = "update ROLES set name = ? where id =?";

    //DELETE
    public static final String DELETE_USER = "DELETE FROM USERS WHERE id = ?";
    public static final String DELETE_ROLE = "DELETE FROM ROLES WHERE id = ?";

    //SEARCHING
    public static final String FIND_BY_ID_USER = "SELECT * FROM USERS WHERE id = ?";
    public static final String FIND_BY_ID_ROLE = "SELECT * FROM ROLES WHERE id = ?";

    public static final String FIND_ALL_USERS = "SELECT * FROM USERS";
    public static final String FIND_ALL_ROLES = "SELECT * FROM ROLES";

    public static final String FIND_USER_BY_EMAIL = "select * from USERS where email = ?";
    public static final String FIND_ROLE_BY_NAME = "select * from ROLES where name = ?";

    public static final String CREATE_TABLE_USERS = " create table USERS(\n" +
            "    id int auto_increment primary key,\n" +
            "    mail varchar(50),\n" +
            "    password varchar(10),\n" +
            "    firstname varchar(20),\n" +
            "    lastname varchar(20),\n" +
            "    birthday date,\n" +
            "    role varchar(10),\n" +
            "    foreign key (id) references ROLES(id));";

    public static final String CREATE_TABLE_ROLES = "create table ROLES(\n" +
            "    id int auto_increment primary key,\n" +
            "    name varchar(50));";


}
