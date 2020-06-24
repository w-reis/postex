package com.postex.app;

public class Recipient {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;

    public Recipient(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }
}
