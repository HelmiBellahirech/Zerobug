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

import com.codename1.components.FloatingHint;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.Entite.Session;
import org.json.JSONObject;

/**
 * Sign in UI
 *
 * @author Shai Almog
 */
public class SignInForm extends BaseForm {

    public SignInForm(Resources res) {
        super(new BorderLayout());

        if (!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout) getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");

        add(BorderLayout.NORTH, new Label(res.getImage("Logo.png"), "LogoLabel"));

        TextField username = new TextField("", "Username", 4, TextField.ANY);
        username.setUIID("TextFieldBlack");
        username.setAlignment(LEFT);
        TextField password = new TextField("", "Mot de passe", 20, TextField.PASSWORD);
        password.setUIID("TextFieldBlack");
        password.setAlignment(LEFT);
        username.setSingleLineTextArea(true);
        password.setSingleLineTextArea(true);
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");
        signUp.addActionListener(e -> new SignUpForm(res).show());
        signUp.setUIID("Link");
        Label doneHaveAnAccount = new Label("Don't have an account?");

        Container content = BoxLayout.encloseY(
                new FloatingHint(username),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                signIn,
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp)
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        signIn.requestFocus();
        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ConnectionRequest con = new ConnectionRequest();
                con.setUrl("http://localhost/pidev2017/selectUtilisateur.php?username=" + username.getText() + "&password=" + password.getText());
                System.out.println(" " + username + " " + password);
                con.addResponseListener(new ActionListener<NetworkEvent>() {
                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        try {
                            String response = new String(con.getResponseData());
                            System.out.println("json : " + response);
                            
                            JSONParser j = new JSONParser();
                            JSONObject obj = new JSONObject(response);
                            Session.setId((String) obj.get("id"));

                            new NewsfeedForm(res).show();
                        } catch (Exception e) {
                            Dialog.show("Error", "Please provide valid entries for both fields  ", "OK", "Cancel");
                            System.out.println(".actionPerformed() " + e);
                        }
                    }
                });
                NetworkManager.getInstance().addToQueue(con);

            }
        });
    }

    
}
