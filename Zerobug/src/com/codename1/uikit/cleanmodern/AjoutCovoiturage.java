/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.mycompany.Entite.Covoiturage;
import com.codename1.components.ScaleImageLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.Entite.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author OrbitG
 */
public class AjoutCovoiturage extends BaseForm {
TextField depart; 
TextField arrive; 
    public AjoutCovoiturage(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
//        setTitle("Profile");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        tb.addSearchCommand(e -> {
        });

        Image img = res.getImage("cov3.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label("Facebook", res.getImage("facebook-logo.png"), "BottomPad");
        Label twitter = new Label("Tweeter", res.getImage("twitter-logo.png"), "BottomPad");
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);

        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                        GridLayout.encloseIn(3,
                                facebook,
                                FlowLayout.encloseCenter(
                                        new ScaleImageLabel(res.getImage("logotna_opt.jpg"))),
                                twitter
                        )
                )
        ));
        Covoiturage l = new Covoiturage();
        TextField etape1 = new TextField("Commencez par l'essentiel !");
        etape1.setUIID("SideCommand");
        etape1.setEditable(false);
        add(etape1);

         depart = new TextField("");
        depart.setUIID("TextFieldBlack");

      arrive = new TextField("");
        arrive.setUIID("TextFieldBlack");

        TextField prix = new TextField("");
        prix.setUIID("TextFieldBlack");

       Picker heure = new Picker();
        heure.setType(Display.PICKER_TYPE_TIME);
        heure.setUIID("TextFieldBlack");

        ComboBox nbr =new ComboBox();
        String[] lestypes={"1","2","3","4"};
        for (int i=0 ;i<4;i++){
        nbr.addItem(lestypes[i]);
        }
        
        Picker date = new Picker();
        date.setType(Display.PICKER_TYPE_DATE);
        date.setUIID("TextFieldBlack");

        Picker p = new Picker();
        p.setUIID("TextFieldBlack");
        p.setStrings(characters);
        p.setSelectedString("cliquez !");
        addStringValue("Comfort", p);

        Picker p1 = new Picker();
        p1.setUIID("TextFieldBlack");
        p1.setStrings(characters2);
        p1.setSelectedString("cliquez !");
        addStringValue("Fumeur", p1);

        addStringValueImage(res.getImage("tick.png"), "Depart", FlowLayout.encloseRightMiddle(depart));
        addStringValueImage(res.getImage("tick.png"), "Arrivé", FlowLayout.encloseRightMiddle(arrive));
        addStringValueImage(res.getImage("dollar.png"), "Prix", FlowLayout.encloseRightMiddle(prix));
        addStringValueImage(res.getImage("family.png"), "Nombre de places", FlowLayout.encloseRightMiddle(nbr));
        addStringValueImage(res.getImage("tick.png"), "date", FlowLayout.encloseRightMiddle(date));

        addStringValueImage(res.getImage("tick.png"), "heure", FlowLayout.encloseRightMiddle(heure));

        Button bn = new Button("Ajouter", res.getImage("next.png"));

        add(bn);
        bn.addActionListener(e -> {
            if ((depart.getText().equals(""))||(arrive.getText().equals(""))||(prix.getText().equals(""))||(p.getText()==null)||(p1.getText()==null)){
                Dialog.show("Erreur", "Tous les champs sont obligatoire .", "Ok", null);
            } 
           else if(nbnuit(date.getDate(), new Date())<=0 )
            {
                 Dialog.show("Erreur", "Date invalid .", "Ok", null);
            }
           
           else if ((isNumber(prix.getText())==0)||(Float.parseFloat(prix.getText())<0)){
                Dialog.show("Erreur", "verifier le prix", "Ok", null);
            
            }else{
            l.setDepart(depart.getText());
            l.setArrive(arrive.getText());
            l.setPrix(Float.parseFloat(prix.getText()));
            l.setNbrPlaces(Integer.parseInt(nbr.getSelectedItem().toString()));
            //l.setNbrSalleBain(Integer.parseInt(SDB.getText()));
            l.setComfort(p.getSelectedString());
            l.setFumeur(p1.getSelectedString());
            String s= date.getText();
            String sdate="20"+s.charAt(6)+s.charAt(7)+"-"+s.charAt(3)+s.charAt(4)+"-"+s.charAt(0)+s.charAt(1);
            l.setDate(sdate);
            l.setHeure(heure.getText());
            System.out.println(heure.getText());
            String dateString = null;
            SimpleDateFormat sdfr = new SimpleDateFormat("yyyy-MM-dd");
            dateString = sdfr.format(sdate) ; 
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date datesys = new Date();
	 //2016/11/16 12:08:43
         
         {
            
            ConnectionRequest req = new ConnectionRequest();
            req.setUrl("http://localhost/pidev2017/AjoutCovoiturage.php?depart=" + l.getDepart() + "&arrive=" + l.getArrive() + "&prix=" + l.getPrix() + "&date=" + dateString + "&datesys=" + dateFormat.format(datesys) + "&heure=" + l.getHeure() + "&nbplace=" + l.getNbrPlaces() + "&comfort=" + l.getComfort() + "&fumeur=" + l.getFumeur() + "&iduser=" + Session.getId()+"");
            NetworkManager.getInstance().addToQueue(req);
          Dialog.show("Confirmation", "Ajout avec succés", "Ok", null);
            new AffichageCovoiturage(res).show();
         }
            }});

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    private void addStringValueImage(Image img, String s, Component v) {
        add(BorderLayout.west(new Label(s, img, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    public ArrayList<Covoiturage> getListLogement(String json) {
        ArrayList<Covoiturage> listLogements = new ArrayList<>();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> offres = j.parseJSON(new CharArrayReader(json.toCharArray()));

            System.out.println();
            List<Map<String, Object>> list = (List<Map<String, Object>>) offres.get("root");

            for (Map<String, Object> obj : list) {
                Covoiturage l = new Covoiturage();
                l.setID(Integer.parseInt(obj.get("MAX(id)").toString()));

                listLogements.add(l);
            }

        } catch (IOException ex) {
        }
        return listLogements;

    }
    private static String[] characters = {"Comfortable", "Basique", "Luxe", "Normale"};
    private static String[] characters2 = {"Oui", "Non"};
     public static float isNumber(String ch) {
       float i=0;
            try {
                i = Float.parseFloat(ch);
            } catch (NumberFormatException e) {
                return i;
            }
        
        return i;
    }
      public static int nbnuit(Date date1, Date date2) {
        int diffInDays = (int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24));
        return diffInDays;
    }
}
