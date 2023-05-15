package com.example.application.data.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// @Entity
public class Client extends AbstractEntity{
    
    @NotBlank
    @NotNull
    private String login;

    @NotBlank
    @NotNull
    private String mail;

    @NotNull
    @NotBlank
    private String dateOfJoin;

    // @ManyToOne(fetch = FetchType.EAGER)
    // private Set<Purchase> clientPurchases;

    public Client() {
        this.login = "---";
        this.mail = "---";
        this.dateOfJoin = LocalDateTime.now().toString();
    }

    public Client(String login, String mail) {
        this.login = login;
        this.mail = mail;
        this.dateOfJoin = LocalDateTime.now().toString();
    }

    public Client(String login, String mail, String dateOfJoin) {
        this.login = login;
        this.mail = mail;
        this.dateOfJoin = LocalDateTime.now().toString();
    }

    public String getmail() {
        return mail;
    }

    public void setmail(String mail) {
        this.mail = mail;
    }

    public String getlogin() {
        return login;
    }

    public void setlogin(String login) {
        this.login = login;
    }

    public String getDateOfJoin() {
        return dateOfJoin;
    }

    // public Set<Purchase> getClientPurchases() {
    //     return clientPurchases;
    // }

    // public void setClientPurchases(Set<Purchase> clientPurchases) {
    //     this.clientPurchases = clientPurchases;
    // }

    @Override
    public String toString() {
        return login;
    }
}
