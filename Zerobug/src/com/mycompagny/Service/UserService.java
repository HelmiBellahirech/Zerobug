/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompagny.Service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import com.mycompany.Entite.Utilisateur;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author OrbitG
 */
public class UserService {
     public ArrayList<Utilisateur> getListUtilisateur(String json) {
        ArrayList<Utilisateur> listUtilisateurs = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> utilisateurs = j.parseJSON(new CharArrayReader(json.toCharArray()));

            System.out.println();
            List<Map<String, Object>> list = (List<Map<String, Object>>) utilisateurs.get("root");

            for (Map<String, Object> obj : list) {
                Utilisateur u = new Utilisateur();
                u.setUsername((String) obj.get("username"));
                u.setEmail((String) obj.get("email"));
                u.setPassword((String) obj.get("password"));
                listUtilisateurs.add(u);

            }

        } catch (IOException ex) {
        }
        return listUtilisateurs;

    }
}
