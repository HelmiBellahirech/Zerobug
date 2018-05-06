/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.Service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;

/**
 *
 * @author OrbitG
 */
public class ReservationCovService {
    public void ajoutReservation(int nbplace,int idAnnonce,int idChauffeur,int idReserve) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://localhost/PIDEV/Entraide2.0/web/app_dev.php/mobile/insertreservation/"    + nbplace+"/" 
                                                                            +idAnnonce+ "/" 
                                                                            + idChauffeur+ "/" 
                                                                            + idReserve; 
                                                                            
        con.setUrl(Url);
        System.out.println(Url);
        System.out.println("tt");

        con.addResponseListener(e -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
}
