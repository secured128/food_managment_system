package com.elalex.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    protected User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, name='%s', password='%s']", id, name, password);
    }
}