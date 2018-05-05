/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;
import com.mycompany.Entite.ReservSalle;
import com.mycompany.Service.ReservService;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author nadaghanem
 */
public class affichageReservation
{
    private Form f;
    private Container reserv;
    
    public void ajoutRes(ReservSalle r)
    {
        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container c2= new Container(new BoxLayout(BoxLayout.X_AXIS));
        
        SpanLabel label = new SpanLabel("Reservation par Mr: "+r.getUser());
        SpanLabel label2 = new SpanLabel("Numero de salle est : "+String.valueOf(r.getSalle())+"");
        
        Button details =new Button("Details");
        
        details.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                Form f1 = new Form("Details reservation");
                
                 Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container c2= new Container(new BoxLayout(BoxLayout.X_AXIS));
        
        SpanLabel label = new SpanLabel("Reservation par Mr: "+r.getUser());
        SpanLabel label2 = new SpanLabel("Numero de salle est : "+String.valueOf(r.getSalle())+"");
         SpanLabel label3 = new SpanLabel("nombre de personne est : "+String.valueOf(r.getNbPersonnes()));
     
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");
        SimpleDateFormat day = new SimpleDateFormat("dd-MM-yyyy");
        String j = day.format(r.getDateTime1());
        String dd = time.format(r.getDateTime1());
        String df = time.format(r.getDateTime2());
        SpanLabel jour = new SpanLabel("Le "+ j);
        Label date = new Label("Du "+dd+ " jusqu'à "+df); 
        Button modifier =new Button("Modifier");
                Button supprimer =new Button("supprimer");

                 c1.add(label);
        c1.add(label2);
        c1.add(label3);
        c1.add(jour);
        c1.add(date);
        c1.add(modifier);
      
        c1.add(supprimer);
        
        
        f1.add(c1);
        f1.show();
               
            }
        });
        
       
        c1.add(label);
        c1.add(label2);
        c1.add(details);
        
        f.add(c1);
    }
    
    public affichageReservation() throws IOException {
        f = new Form(BoxLayout.y());
        f.setScrollableY(true);
        f.setTitle("Reservation");
        ReservService Res = new ReservService();
        for(ReservSalle rs : Res.getList()){
            ajoutRes(rs); 
        }
        f.getToolbar().addCommandToRightBar("Retour",null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Home home = new Home();
                home.getF().show();
            }
        });
        
        f.getToolbar().addCommandToLeftBar("Ajouter", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                AjoutReservation ar = new AjoutReservation();
                ar.getF().show();
            }
        });
        f.show();
         
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
}
