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
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
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
         Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container c2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
                Container c3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
                
                
        Container date = new Container(BoxLayout.x());
        Container temps = new Container(BoxLayout.x());
        tuser = new TextField("","Nom et prenom");
        tsalle = new TextField("","N° du salle",3,TextArea.NUMERIC);
        tnbr=new TextField("","Nombre de personne",2,TextArea.NUMERIC);
        //tEtat=new TextField("","Etat");
        btnajout = new Button("Ajouter");
        btnaff=new Button("Annuler");
        Picker dateTime1 = new Picker();
        Picker dateTime2 = new Picker();
        Picker heures1 = new Picker();
        heures1.setType(Display.PICKER_TYPE_TIME);
        Picker heures2 = new Picker();
        heures2.setType(Display.PICKER_TYPE_TIME);
        Label l=new Label("    date debut : ");
        Label l1=new Label("    date fin:        ");
        c2.add(l);
        c2.add(dateTime1);
        c2.add(heures1);
        c3.add(l1);
        c3.add(dateTime2);
       
        c3.add(heures2);
        c1.add(tuser);
        c1.add(tsalle);
        c1.add(tnbr);
        //c1.add(tEtat);
        c1.add(c2);
        c1.add(c3);
        c1.add(btnajout);
        c1.add(btnaff);
        f.add(c1);
        btnajout.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int etat = 0;
                ReservService res = new ReservService();
                ReservSalle ressalle =null;
                String  stringdate1 = new SimpleDateFormat("yyyy-MM-dd").format(dateTime1.getDate())
                        +" "+heures1.getText()+":00";
                String  stringdate2 = new SimpleDateFormat("yyyy-MM-dd").format(dateTime2.getDate())
                        +" "+heures2.getText()+":00";
                if("".equals(tuser.getText())){
                    btnajout.addActionListener((evt) -> Dialog.show("ERROR", "le champ nom utilisateur est obligatoire", "OK", null));
                }else if("".equals(tsalle.getText())){
                    btnajout.addActionListener((evt) -> Dialog.show("ERROR", "le numero du salle est obligatoire", "OK", null));
                }else if("".equals(tnbr.getText())){
                    btnajout.addActionListener((evt) -> Dialog.show("ERROR", "le nombre de personne est obligatoire", "OK", null));
                }else{
                    if(Integer.valueOf(tnbr.getText())==30){
                        etat = 1;
                    }else if(Integer.valueOf(tnbr.getText())<30){
                        etat = 0;
                    }else if(Integer.valueOf(tnbr.getText())>30){
                        btnajout.addActionListener((evt) -> Dialog.show("ERROR", "le nombre est invalide", "OK", null));
                    }
                    ressalle= new ReservSalle(0,tuser.getText(), Integer.valueOf(tsalle.getText()),
                            stringdate1,stringdate2 , Integer.valueOf(tnbr.getText()),etat);
                    res.Create(ressalle);
                    System.out.println("creation du "+ ressalle);
                    affichageReservation aff = new affichageReservation();
                    aff.getF().show();
                }
            } catch (IOException ex) {}
                         
         }});  
        
        btnaff.addActionListener((e)->{
      
            try {
                affichageReservation home = new affichageReservation();
                home.getF().show();
            } catch (IOException ex) {}
        });
           Message m = new Message("Votre Reservation a ete effectue avec succes");
    Display.getInstance().sendMessage(new String[] {"nada.ghannem@esprit.tn"}, "Hey you", m);
      
    }
    
     public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
}
