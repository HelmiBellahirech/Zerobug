/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.io.IOException;


/**
 *
 * @author nadaghanem
 */
public class Home {
    
    private Form f;
        
    public Home() {
        f = new Form("Home",new BoxLayout(BoxLayout.Y_AXIS));
        Style s= UIManager.getInstance().getComponentStyle("Title");
        FontImage icon =FontImage.createMaterial(FontImage.MATERIAL_BOOK, s);
        f.getToolbar().addCommandToSideMenu("Reservation", icon, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    affichageReservation affres  = new affichageReservation();
                    affres.getF().show();
                } catch (IOException ex) {}
            }
        });
        f.getToolbar().addCommandToSideMenu("Cour",icon,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    affichageCours affcour = new affichageCours();
                    affcour.getF().show();
                } catch (IOException ex) {}
            }
        });
//        
//        Button Reservation = new Button("affichage reservation");
//         Button Cou = new Button("affichage cours");
//             Button ajout = new Button("ajout");
//        Container affichage = new Container();
//        
//        affichage.add(Reservation);
//        affichage.add(Cou);
//        affichage.add(ajout);
//        
//       
//        f.add(affichage);
//       
//        Reservation.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                affichageReservation add;
//                try {
//                    add = new affichageReservation();
//                    add.getF().show();
//                } catch (IOException ex) {
//                    
//                }
//                
//           
//            }
//        });
//       Cou.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                affichageCours add;
//                try {
//                    add = new affichageCours();
//                    add.getF().show();
//                } catch (IOException ex) {
//                    
//                }
//                
//           
//            }
//        }); 
//        
//        ajout.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//              
//                AjoutReservation aj=new AjoutReservation();
//                aj.getF().show();
//                
//           
//            }
//        });
        f.show();
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

    
}
