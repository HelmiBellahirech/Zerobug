/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.Layout;
import com.mycompany.Entite.courss;
import com.mycompany.Service.courssService;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author nadaghanem
 */
public class affichageCours 
{
    private Form f;
   // private Container MesCours;

    public affichageCours() throws IOException {
        f = new Form("Les cours",BoxLayout.y());
        f.setScrollable(true);
       // MesCours = new Container(new BoxLayout(BoxLayout.Y_AXIS)) ;
        courssService SE=new courssService();
        ArrayList<courss> listecr =SE.getList();
         
        f.getToolbar().addCommandToRightBar("Retour", null, (ev)->{
            Home EM=new Home();
            EM.getF().show();
        });
         
        for(courss rs : listecr)
        {         
            SpanLabel label = new SpanLabel(rs.getModule());
            SpanLabel label2 = new SpanLabel(rs.getMatiere());
            EncodedImage image = EncodedImage.create("/giphy.gif");
            Image img1 = URLImage.createToStorage(image,"books.jpg" ,
                "http://localhost//Entraide2.0//web//assets//img//books.jpg").scaled(300, 150);
            ImageViewer imgv = new ImageViewer(img1); 
            //Container cx = new Container(BoxLayout.x());
            Container cy =new Container(BoxLayout.y());
            Button Details = new Button("Details");
            cy.add(imgv);
            cy.add(label);
            cy.add(label2);
            //cx.add(cy);
            cy.add(Details);
            Details.addActionListener((e)->{
                Form f1 = new Form(BoxLayout.y());
                    f1.setTitle(rs.getMatiere());
                    f1.setScrollableY(true);
                    Container cc = new Container(BoxLayout.y());
                    SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy ");
                    String date = formater.format(rs.getDate_pub());
                    SpanLabel l1 = new SpanLabel(date);
                    SpanLabel l2 = new SpanLabel(rs.getMatiere());
                    SpanLabel l3 = new SpanLabel(rs.getModule());
                    SpanLabel l4 = new SpanLabel(rs.getFichier());
                    cc.add(l3);
                    cc.add(l2);
                    cc.add(l4);
                    cc.add(l1);
                    f1.add(cc);

                    f1.getToolbar().addCommandToRightBar("Retour", null, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            f.show();
                        }
                    });
                    f1.show();
            });
//            
           // MesCours.add(cy);   
           
           f.add(cy);
        }
       // f.add(MesCours);
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    
}
