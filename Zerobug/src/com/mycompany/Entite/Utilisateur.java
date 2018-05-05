/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Entite;

/**
 *
 * @author Boubaker
 */
public class Utilisateur {
    
     public static Utilisateur connected;

    public static Utilisateur getConnected() {
        return connected;
    }

    public static void setConnected(Utilisateur connected) {
        Utilisateur.connected = connected;
    }
    int id;
    String nom;
    String prenom;
    String email;
    String password;
    String username;

    public Utilisateur() {
    }

    public Utilisateur(int id, String nom, String prenom, String email, String password, String username) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

  
    
}
