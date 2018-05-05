/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.codename1.uikit.cleanmodern;

import com.codename1.components.ScaleImageLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.Entite.Session;
import com.mycompany.Entite.Utilisateur;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class ProfileForm extends BaseForm {

    public ProfileForm(Resources res) {
         super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profile");
        getContentPane().setScrollVisible(true);

        super.addSideMenu(res);

        tb.addSearchCommand(e -> {
        });

        Image img = res.getImage("font.png");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        //Label facebook = new Label("786 followers", res.getImage("facebook-logo.png"), "BottomPad");
        //Label twitter = new Label("486 followers", res.getImage("twitter-logo.png"), "BottomPad");
        //facebook.setTextPosition(BOTTOM);
        //twitter.setTextPosition(BOTTOM);
        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                        GridLayout.encloseIn(3,
                                FlowLayout.encloseCenter(
                                        new Label(res.getImage("profile-pic.jpg"), "PictureWhiteBackgrond"))
                        )
                )
        ));
        /*Button modifier = new Button("Modifier");
        modifier.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
            tb.addMaterialCommandToSideMenu("Mail", FontImage.MATERIAL_EMAIL, e -> new ModifForm(res).show());
            }
        });*/
        
        
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/pidev2017/selectUser.php?id=" + Session.getId());
        System.out.println("http://localhost/pidev2017/selectUser.php?id=" + Session.getId());

        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                String response = new String(con.getResponseData());
                String response2 = response.substring(1);
                response = response2.substring(0, response2.length() - 1);
                System.out.println("json : " + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    Utilisateur u = new Utilisateur();
                    u.setUsername(obj.getString("username"));
                    u.setEmail(obj.getString("email"));
                    u.setNom(obj.getString("nom"));
                    u.setPrenom(obj.getString("prenom"));
                     //u.setUserna(obj.getString("username"));

                    TextField username = new TextField(u.getUsername());
                    username.setUIID("TextFieldBlack");
                    addStringValue("Username", username);
                    username.setEditable(false);

                    TextField email = new TextField(u.getEmail());
                    email.setUIID("TextFieldBlack");
                    addStringValue("E-Mail", email);
                    email.setEditable(false);

                    TextField nom = new TextField(u.getNom());
                    nom.setUIID("TextFieldBlack");
                    addStringValue("Nom", nom);
                    nom.setEditable(false);

                    TextField prenom = new TextField(u.getPrenom());
                    prenom.setUIID("TextFieldBlack");
                    addStringValue("Prenom", prenom);
                    prenom.setEditable(false);
Button supp=new Button("Supprimer Compte");
supp.setUIID("Button");
                    addButtonValue(supp);
                    supp.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent evt) {
                              ConnectionRequest request = new ConnectionRequest() {
                    @Override
                    protected void handleErrorResponseCode(int code, String message) {
                        System.out.println("Code :" + code + " Msg :" + message);
                    }

                };
                             request.setUrl("http://localhost/pidev2017/removeProfil.php?id="+Session.getId() );
                NetworkManager.getInstance().addToQueueAndWait(request);
                             Dialog.show("confirm", "profil supprimee ", "Ok", null);
                             new SignInForm(res).show();
                        }
                    });
                } catch (JSONException ex) {
                    System.out.println("Exeption Please Try again");
                }

            }
        });
        NetworkManager.getInstance().addToQueue(con);

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
    private void addButtonValue( Component v) {
        add(BorderLayout.west(new Button()).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
