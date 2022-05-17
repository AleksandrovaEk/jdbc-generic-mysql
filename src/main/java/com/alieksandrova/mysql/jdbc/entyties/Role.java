package com.alieksandrova.mysql.jdbc.entyties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private int id;
    private String role;

    public Role(String role) {
        this.role = role;
    }
}
