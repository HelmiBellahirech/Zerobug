/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.mycompany.Entite.Utilisateur;
import com.mycompany.Entite.Session ; 
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Ennouri Radhouene
 */
public class ModifForm {

    ModifForm(Resources res) {
     
    
    ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/selectUser.php?id=" + Session.getId());
        System.out.println("http://localhost/pidev2017/selectUser.php?id=" + Session.getId());

        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                String response = new String(con.getResponseData());
                String response2 = response.substring(1);
                response = response2.substring(0, response2.length() - 1);
                System.out.println("json : " + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    Utilisateur u = new Utilisateur();
                    u.setUsername(obj.getString("username"));
                    u.setEmail(obj.getString("email"));
                    u.setNom(obj.getString("nom"));
                    u.setPrenom(obj.getString("prenom"));
                     //u.setUserna(obj.getString("username"));

                    TextField username = new TextField(u.getUsername());
                    username.setUIID("TextFieldBlack");
                    //addStringValue("Username", username);
                    username.setEditable(false);

                    TextField email = new TextField(u.getEmail());
                    email.setUIID("TextFieldBlack");
                    //addStringValue("E-Mail", email);
                    email.setEditable(false);

                    TextField nom = new TextField(u.getNom());
                    nom.setUIID("TextFieldBlack");
                    //addStringValue("Nom", nom);
                    nom.setEditable(false);

                    TextField prenom = new TextField(u.getPrenom());
                    prenom.setUIID("TextFieldBlack");
                    //addStringValue("Prenom", prenom);
                    prenom.setEditable(false);

                } catch (JSONException ex) {
                    System.out.println("Exeption Please Try again");
                }

            }
        });
    
    
    }

   /*private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    */
    
}
