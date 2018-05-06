/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.mycompany.Entite.Covoiturage;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompagny.Service.ReservationCovService;
import com.mycompany.Entite.Session;
import com.mycompany.Entite.Utilisateur;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author OrbitG
 */
public class DetailsCovoiturage extends BaseForm {

    ConnectionRequest connectionRequest;

    DetailsCovoiturage(Resources res, Covoiturage r) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("Activate");

        add(BorderLayout.NORTH,
                BoxLayout.encloseY(
                        new Label(res.getImage("smily.png"), "LogoLabel"),
                        new Label(r.getDepart()+" --> "+r.getArrive(), "LogoLabel")
                )
        );
ComboBox Combo =new ComboBox();
        String[] lestypes={"1","2","3","4"};
        for (int i=0 ;i<4;i++){
        Combo.addItem(lestypes[i]);
        }
        Button reserve =new Button("Reserver");
        
        Button signUp = new Button("Sign Up");
        Button resend = new Button("Resend");
        resend.setUIID("CenterLink");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("CenterLink");
        Container content;
        if(Session.getId().equals(String.valueOf(r.getID_USER()))){
            content = BoxLayout.encloseY(
                createLineSeparator(),
                new Label("Plus de details", "LogoLabel"),
                new Label("Prix: " + r.getPrix()),
                new Label("Nombre de places disponible : " + r.getNbrPlaces()),
                new Label("Comfort : " + r.getComfort()),
                new Label("Fumeur : " + r.getFumeur()),
                new Label("Le : " + r.getDate()),
                new Label("à : " + r.getHeure()),
                new Label("Annonce publié le  : " + r.getDate_sys())
        );
        }
        else{
        content = BoxLayout.encloseY(
                createLineSeparator(),
                new Label("Plus de details", "LogoLabel"),
                new Label("Prix: " + r.getPrix()),
                new Label("Nombre de places disponible : " + r.getNbrPlaces()),
                new Label("Comfort : " + r.getComfort()),
                new Label("Fumeur : " + r.getFumeur()),
                new Label("Le : " + r.getDate()),
                new Label("à : " + r.getHeure()),
                new Label("Annonce publié le  : " + r.getDate_sys()),
                   BoxLayout.encloseX(Combo,reserve)
        );
        }
        //*************
      reserve.addActionListener(l->{
          if(Integer.valueOf(Combo.getSelectedItem().toString())<=r.getNbrPlaces())
          {
          ReservationCovService rc=new ReservationCovService();
         rc.ajoutReservation(Integer.valueOf(Combo.getSelectedItem().toString()),r.getID(),r.getID_USER(), Integer.valueOf(Session.getId()));
         ToastBar.showMessage("Votre Reservation est en cours de traitement", FontImage.MATERIAL_INFO);
          }
          else
          {
               Dialog.show("Erreur", "Nombre de places invalide", "Ok", null);
          }
      });
        
        content.setScrollableY(true);

        add(BorderLayout.CENTER,content);
        signUp.requestFocus();
        signUp.addActionListener(e -> new NewsfeedForm(res).show());

    }

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
