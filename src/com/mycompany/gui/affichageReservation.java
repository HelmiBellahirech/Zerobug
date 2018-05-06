/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.Entite.ReservSalle;
import com.mycompany.Service.ReservService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.IOException;



/**
 *
 * @author nadaghanem
 */
public class affichageReservation {
 
    
    
    private Form f;
    private Container reserv;

    public void ajoutRes(ReservSalle r) throws IOException {
        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container c2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        EncodedImage image = EncodedImage.create("/giphy.gif");
        Image img1 = URLImage.createToStorage(image,"user-06.jpg" ,
                "http://localhost//Entraide2.0//web//assets//img//user-06.jpg").scaled(50, 50);
        ImageViewer imgv = new ImageViewer(img1);  
        SpanLabel label = new SpanLabel("Reservation par Mr: " + r.getUser());
        SpanLabel label2 = new SpanLabel("Numero de salle est : " + String.valueOf(r.getSalle()) + "");

        Button details = new Button("Details");

        details.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Form f1 = new Form("Details reservation");

                Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                Container c2 = new Container(new BoxLayout(BoxLayout.X_AXIS));

                SpanLabel label = new SpanLabel("Reservation par Mr: " + r.getUser());
                SpanLabel label2 = new SpanLabel("Numero de salle est : " + String.valueOf(r.getSalle()) + "");
                SpanLabel label3 = new SpanLabel("nombre de personne est : " + String.valueOf(r.getNbPersonnes()));

                SimpleDateFormat time = new SimpleDateFormat("HH:mm");
                SimpleDateFormat day = new SimpleDateFormat("dd-MM-yyyy");
                String j = day.format(r.getDateTime1());
                String dd = time.format(r.getDateTime1());
                String df = time.format(r.getDateTime2());
                SpanLabel jour = new SpanLabel("Le " + j);
                Label date = new Label("Du " + dd + " jusqu'à " + df);
                Button modifier = new Button("Modifier");
                                        System.out.println(r.getId());

                Button supprimer = new Button("supprimer");
                f1.getToolbar().addCommandToRightBar("Retour", null, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        f.show();
                    }
                });
                //Supression Reservation
                supprimer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        
                        ReservSalle re=new ReservSalle();
                        ReservService rs=new ReservService();
                        re.setId(r.getId());
                        if(Dialog.show("suppression","voulez vous supprimer", "oui","non"))
                        {   try {
                            rs.Delete(re);
                            affichageReservation aff = new affichageReservation();
                            aff.getF().show();
                            } catch (IOException ex) {}
                        }
                    }
                });

                //modifier Reservation
                modifier.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Form f1= new Form("Modification");
                        Container c1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                        Container c2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
                        Container c3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
                        Button btnajout,btnannulle;
                        TextField tuser = new TextField();
                        TextField tsalle = new TextField();
                        TextField tnbr=new TextField();
                        TextField tEtat=new TextField();
                        btnajout = new Button("Modifier");
                        btnannulle=new Button("Annuler");
                        Picker dateTime1 = new Picker();
                        Picker dateTime2 = new Picker();
                        Picker heures1 = new Picker();
                        heures1.setType(Display.PICKER_TYPE_TIME);
                        Picker heures2 = new Picker();
                        heures2.setType(Display.PICKER_TYPE_TIME);
                        tuser.setText(r.getUser());
                        tnbr.setText(String.valueOf(r.getNbPersonnes()));
                        tsalle.setText(String.valueOf(r.getSalle()));
                        tEtat.setText(String.valueOf(r.getEtat()));
                        tuser.setText(r.getUser());
//                        Message msg = Message.creator(new PhoneNumber("98516822"),new PhoneNumber("24288872"), "TON SMS").create();
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
                        c1.add(tEtat);
                        c1.add(c2);
                        c1.add(c3);
                        c1.add(btnajout);
                        c1.add(btnannulle);
                        f1.add(c1);
                        f1.show();
                    btnajout.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            try {
                                ReservService res = new ReservService();
                                String  stringdate1 = new SimpleDateFormat("yyyy-MM-dd").format(dateTime1.getDate())
                                        +" "+heures1.getText()+":00";
                                String  stringdate2 = new SimpleDateFormat("yyyy-MM-dd").format(dateTime2.getDate())
                                        +" "+heures2.getText()+":00";
                                ReservSalle ressalle= new ReservSalle();
                                System.out.println("salleest :"+tsalle.getText());
                                int nbr = Integer.parseInt(tnbr.getText());
                                int etat = Integer.parseInt(tEtat.getText());
                                int salle = Integer.parseInt(tsalle.getText());
                                ressalle.setId(r.getId());
                                ressalle.setUser(tuser.getText());
                                ressalle.setStringdate1(stringdate1);
                                ressalle.setStringdate2(stringdate2);
                                ressalle.setSalle(salle);
                                ressalle.setNbPersonnes(nbr);
                                ressalle.setEtat(Integer.valueOf(etat));
                                res.Update(ressalle);
                                affichageReservation aff = new affichageReservation();
                                aff.getF().show();
                            } catch (IOException ex) {}
                        }
                    });
                   btnannulle.addActionListener((e)->{
                            try {
                                affichageReservation aff = new affichageReservation();
                                aff.getF().show();
                            } catch (IOException ex) {}
                   });
                }
            });
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
    c2.add(imgv);
    c1.add(label);
    c1.add(label2);
    c1.add(details);
    c2.add(c1);
    f.add(c2);
    }

    public affichageReservation() throws IOException {
        f = new Form(BoxLayout.y());
        f.setScrollableY(true);
        f.setTitle("Reservation");
        ReservService Res = new ReservService();
        for (ReservSalle rs : Res.getList()) {
            ajoutRes(rs);
        }
        f.getToolbar().addCommandToRightBar("Retour", null, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Home home = new Home();
                home.getF().show();
            }
        });
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.addActionListener((e) ->   {          
            AjoutReservation ar = new AjoutReservation();
            ar.getF().show();
        });
        fab.bindFabToContainer(f.getContentPane()); 

        f.show();

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

}
