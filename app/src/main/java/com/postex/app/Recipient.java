package com.postex.app;

import java.io.Serializable;

public class Recipient implements Serializable {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String token;

    public Recipient(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
}
