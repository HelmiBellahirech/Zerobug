/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Entite;

/**
 *
 * @author OrbitG
 */
public class Reservationcov {
    private int ID ; 
    private int ID_RESERVE;
    private int ID_CAHUFFEUR;
    private int ID_ANNONCE ;
    private int NBPLACES;

    public int getNBPLACES() {
        return NBPLACES;
    }

    public void setNBPLACES(int NBPLACES) {
        this.NBPLACES = NBPLACES;
    }

    public int getID_ANNONCE() {
        return ID_ANNONCE;
    }

    public void setID_ANNONCE(int ID_ANNONCE) {
        this.ID_ANNONCE = ID_ANNONCE;
    }
    private boolean ETAT ; 

    public Reservationcov() {
    }

    public Reservationcov(int ID, int ID_RESERVE, int ID_CAHUFFEUR,int ID_ANNONCE, boolean ETAT,int NBPLACES) {
        this.ID = ID;
        this.ID_RESERVE = ID_RESERVE;
        this.ID_CAHUFFEUR = ID_CAHUFFEUR;
        this.ID_ANNONCE= ID_ANNONCE;
        this.ETAT = ETAT;
        this.NBPLACES=NBPLACES;
    }
    
     public Reservationcov(int ID_RESERVE, int ID_CAHUFFEUR,int ID_ANNONCE, boolean ETAT,int NBPLACES) {
        
        this.ID_RESERVE = ID_RESERVE;
        this.ID_CAHUFFEUR = ID_CAHUFFEUR;
         this.ID_ANNONCE= ID_ANNONCE;
        this.ETAT = ETAT;
         this.NBPLACES=NBPLACES;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_RESERVE() {
        return ID_RESERVE;
    }

    public void setID_RESERVE(int ID_RESERVE) {
        this.ID_RESERVE = ID_RESERVE;
    }

    public int getID_CAHUFFEUR() {
        return ID_CAHUFFEUR;
    }

    public void setID_CAHUFFEUR(int ID_CAHUFFEUR) {
        this.ID_CAHUFFEUR = ID_CAHUFFEUR;
    }

    public boolean isETAT() {
        return ETAT;
    }

    public void setETAT(boolean ETAT) {
        this.ETAT = ETAT;
    }
     
    
}
