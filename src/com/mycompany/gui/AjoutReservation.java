/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.Entite.ReservSalle;
import com.mycompany.Service.ReservService;
import java.io.IOException;


/**
 *
 * @author nadaghanem
 */
public class AjoutReservation
{
    private Form f;
    private Container reserv;
    TextField tuser;
    TextField tsalle;
    TextField tnbr;
    TextField tEtat;
    TextField ID_C;
    Button btnajout,btnaff;
    
    
    
    public AjoutReservation () {  
        f = new Form("Ajouter une reservation",BoxLayout.y());
        Container date = new Container(BoxLayout.x());
        Container temps = new Container(BoxLayout.x());
        tuser = new TextField();
        tsalle = new TextField();
        tnbr=new TextField();
        tEtat=new TextField();
        btnajout = new Button("Ajouter");
        btnaff=new Button("Annuler");
        Picker dateTime1 = new Picker();
        Picker dateTime2 = new Picker();
        Picker heures1 = new Picker();
        heures1.setType(Display.PICKER_TYPE_TIME);
        Picker heures2 = new Picker();
        heures2.setType(Display.PICKER_TYPE_TIME);
        date.add(dateTime1);
        date.add(dateTime2);
        temps.add(heures1);
        temps.add(heures2);
        f.add(tuser);
        f.add(tsalle);
        f.add(tnbr);
        f.add(tEtat);
        f.add(date);
        f.add(temps);
        f.add(btnajout);
        f.add(btnaff);
        btnajout.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ReservService res = new ReservService();
                ReservSalle ressalle ;
                String  stringdate1 = new SimpleDateFormat("yyyy-MM-dd").format(dateTime1.getDate())
                        +" "+heures1.getText()+":00";
                String  stringdate2 = new SimpleDateFormat("yyyy-MM-dd").format(dateTime2.getDate())
                        +" "+heures2.getText()+":00";
                ressalle= new ReservSalle(tuser.getText(), Integer.valueOf(tsalle.getText()),
                        stringdate1,stringdate2 , Integer.valueOf(tnbr.getText()),
                        Integer.valueOf(tEtat.getText()));
                res.Create(ressalle);
                System.out.println("creation du "+ ressalle);
                affichageReservation aff = new affichageReservation();
                aff.getF().show();
            } catch (IOException ex) {
            }
                         
         }});  
        
        btnaff.addActionListener((e)->{
     
                Home home = new Home();
                home.getF().show();
        });
       Message m = new Message("Votre Réservation a été effectué");
    Display.getInstance().sendMessage(new String[] {"nada.ghannem@esprit.tn"}, "SemiColon | Notification", m);
    }
    
     public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
}
