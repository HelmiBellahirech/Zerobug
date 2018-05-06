/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.Service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entite.Covoiturage;
import com.mycompany.Entite.Session;

/**
 *
 * @author OrbitG
 */
public class CovoiturageService {
    
     public void modifeir(Covoiturage l) {
      ConnectionRequest req = new ConnectionRequest();
         System.out.println("http://localhost/pidev2017/ModifierCovoiturage.php?id="+ l.getID()+"&depart="+ l.getDepart() + "&arrive=" + l.getArrive() + "&prix=" + l.getPrix() + "&date=" + l.getDate()+ "&datesys=" + l.getDate_sys() + "&heure=" + l.getHeure() + "&nbplace=" + l.getNbrPlaces() + "&comfort=" + l.getComfort() + "&fumeur=" + l.getFumeur() + "&iduser=" + Session.getId()+"");
            req.setUrl("http://localhost/pidev2017/ModifierCovoiturage.php?id="+ l.getID()+"&depart="+ l.getDepart() + "&arrive=" + l.getArrive() + "&prix=" + l.getPrix() + "&date=" + l.getDate()+ "&datesys=" + l.getDate_sys() + "&heure=" + l.getHeure() + "&nbplace=" + l.getNbrPlaces() + "&comfort=" + l.getComfort() + "&fumeur=" + l.getFumeur() + "&iduser=" + Session.getId());
           
            NetworkManager.getInstance().addToQueue(req);
    }
     
      public void supprimer(int id) {
       ConnectionRequest req = new ConnectionRequest();
            req.setUrl("http://localhost/pidev2017/SupprimerCovoiturage.php?id="+ id);
            NetworkManager.getInstance().addToQueue(req);
    }
      
      
      
    
}
