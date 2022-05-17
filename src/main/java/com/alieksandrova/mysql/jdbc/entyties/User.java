package com.alieksandrova.mysql.jdbc.entyties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthday;
    private int role;

    public User(String email, String password, String firstName, String lastName, Date birthday, int role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.role = role;
    }
}
