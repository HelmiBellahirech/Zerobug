/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.Entite.ReservSalle;
import com.mycompany.Entite.courss;
import com.mycompany.Service.ReservService;
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
    private Container MesCours;

    public affichageCours() throws IOException {
         f = new Form();
         MesCours = new Container(new BoxLayout(BoxLayout.Y_AXIS)) ;
         courssService SE=new courssService();
         ArrayList<courss> listecr =SE.getList();
         
         
          f.getToolbar().addCommandToRightBar("back", null, (ev)->{Home EM=new Home();
          EM.getF().show();
          });
         
         for(courss rs : listecr)
      {
          //bloc de creation d'image
         
         
          
          SpanLabel label = new SpanLabel(rs.getModule());
          SpanLabel label2 = new SpanLabel(rs.getMatiere());
          SpanLabel label3 = new SpanLabel(rs.getFichier());
          Container c =new Container(new BoxLayout(BoxLayout.Y_AXIS));
          c.add(label);
          c.add(label2);
          c.add(label3);
         
          MesCours.add(c);          
      }
      
      f.add(MesCours);
         
    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
    
    
}
