package com.example.jesus.readregister;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Conta {
    @Id private long id;
    private String username;
    private String password;
    public ToMany<Livro> livroToMany;

    public Conta(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Conta() {
    }

    public Conta(long id, String username, String password, ToMany<Livro> livroToMany) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.livroToMany = livroToMany;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
