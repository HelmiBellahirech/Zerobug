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
public class courss 
{
    private int id;
    private int id_prof ;
    private String module ; 
    private String matiere ;
    private Date date_pub ;
    private String fichier ;

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_prof() {
        return id_prof;
    }

    public void setId_prof(int id_prof) {
        this.id_prof = id_prof;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Date getDate_pub() {
        return date_pub;
    }

    public void setDate_pub(Date date_pub) {
        this.date_pub = date_pub;
    }

    public courss() {
    }

    public courss(int id, int id_prof, String module, String matiere, Date date_pub) {
        this.id = id;
        this.id_prof = id_prof;
        this.module = module;
        this.matiere = matiere;
        this.date_pub = date_pub;
    }

    public courss(int id_prof, String module, String matiere, Date date_pub) {
        this.id_prof = id_prof;
        this.module = module;
        this.matiere = matiere;
        this.date_pub = date_pub;
    }

    @Override
    public String toString() {
        return "courss{" + "id=" + id + ", id_prof=" + id_prof + ", module=" + module + ", matiere=" + matiere + ", date_pub=" + date_pub + '}';
    }
   
    
}
