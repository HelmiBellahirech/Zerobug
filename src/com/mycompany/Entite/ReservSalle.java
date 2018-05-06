/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Entite;


import java.util.Date;

/**
 *
 * @author nadaghanem
 */
public class ReservSalle 
{
    private int id;
    private String user ; 
    private int salle;
    private Date dateTime1;
    private Date dateTime2;
    private int  nbPersonnes;
    private int etat;
    private int id_connecte;
    private String stringdate1;
    private String stringdate2 ;

    public ReservSalle(int id,String user, int tsalle, String stringdate1, String stringdate2, int tnbr , int etat)
    {
        this.user = user;
        this.salle = tsalle;
        this.stringdate1 = stringdate1;
        this.stringdate2 = stringdate2;
        this.etat=etat;
        this.nbPersonnes=tnbr;
        this.id=id;
       
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getSalle() {
        return salle;
    }

    public void setSalle(int salle) {
        this.salle = salle;
    }

    public Date getDateTime1() {
        return dateTime1;
    }

    public void setDateTime1(Date dateTime1) {
        this.dateTime1 = dateTime1;
    }

    public Date getDateTime2() {
        return dateTime2;
    }

    public void setDateTime2(Date dateTime2) {
        this.dateTime2 = dateTime2;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public void setNbPersonnes(int nbPersonnes) {
        this.nbPersonnes = nbPersonnes;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getId_connecte() {
        return id_connecte;
    }

    public void setId_connecte(int id_connecte) {
        this.id_connecte = id_connecte;
    }

    public ReservSalle(Integer id, String user, int salle, Date dateTime1, Date dateTime2, int nbPersonnes, int etat, int id_connecte) {
        this.id = id;
        this.user = user;
        this.salle = salle;
        this.dateTime1 = dateTime1;
        this.dateTime2 = dateTime2;
        this.nbPersonnes = nbPersonnes;
        this.etat = etat;
        this.id_connecte = id_connecte;
    }

    public ReservSalle(String user, int salle, Date dateTime1, Date dateTime2, int nbPersonnes, int etat, int id_connecte) {
        this.user = user;
        this.salle = salle;
        this.dateTime1 = dateTime1;
        this.dateTime2 = dateTime2;
        this.nbPersonnes = nbPersonnes;
        this.etat = etat;
        this.id_connecte = id_connecte;
    }

   public ReservSalle(String user,int tsalle, Date stringdate1, Date stringdate2, int tnbr,int tEtat) 
    {
      this.user = user;
        this.salle = tsalle;
        this.dateTime1 = stringdate1;
        this.dateTime2 = stringdate2;
        this.nbPersonnes = tnbr;
        this.etat = tEtat;  
    }

    public String getStringdate1() {
        return stringdate1;
    }

    public String getStringdate2() {
        return stringdate2;
    }

    public void setStringdate1(String stringdate1) {
        this.stringdate1 = stringdate1;
    }

    public void setStringdate2(String stringdate2) {
        this.stringdate2 = stringdate2;
    }
   

    public ReservSalle()
    {
    }
    
   

    @Override
    public String toString() {
        return "ReservSalle{" + "id=" + id + ", user=" + user + ", salle=" + salle + ", dateTime1=" + dateTime1 + ", dateTime2=" + dateTime2 + ", nbPersonnes=" + nbPersonnes + ", etat=" + etat + ", id_connecte=" + id_connecte + '}';
    }
    
}
