/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Entite;

/**
 *
 * @author Ennouri Radhouene
 */
public class Session {
     
    private static String id;
    private static Session ses ;
    public static String getId() {
        return id;
    }
    public static void setId(String id) {
        Session.id = id;
    }
    public static Session GetInstance(){
        return ses;
    }
    public Session(){}
}
