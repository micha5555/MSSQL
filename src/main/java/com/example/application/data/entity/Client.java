package com.example.application.data.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Client extends AbstractEntity{
    
    @Id
    @NotBlank
    private int client_id;

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
        this.client_id = -1;
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

    public Client(int client_id, String login, String mail, String dateOfJoin) {
        this.client_id = client_id;
        this.login = login;
        this.mail = mail;
        this.dateOfJoin = LocalDateTime.now().toString();
    }


    public String getMail() {
        return mail;
    }

    public String getLogin() {
        return login;
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

    public void setId(int client_id) {
        this.client_id = client_id;
    }

    public int getId() {
        return client_id;
    }

    @Override
    public String toString() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setDateOfJoin(String dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }
}
