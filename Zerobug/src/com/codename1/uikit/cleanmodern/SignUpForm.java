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
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignUpForm extends BaseForm {

    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
         TextField nom = new TextField("", "Nom", 20, TextField.ANY);
        nom.setUIID("TextFieldBlack");
        nom.setAlignment(LEFT);
        TextField prenom = new TextField("", "Prenom", 20, TextField.ANY);
        prenom.setUIID("TextFieldBlack");
        prenom.setAlignment(LEFT);
        TextField username = new TextField("", "Username", 20, TextField.ANY);
        username.setUIID("TextFieldBlack");
        username.setAlignment(LEFT);
        TextField email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        email.setUIID("TextFieldBlack");
        email.setAlignment(LEFT);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        password.setUIID("TextFieldBlack");
        password.setAlignment(LEFT);
        TextField confirmPassword = new TextField("", "Confirm Password", 20, TextField.PASSWORD);
        confirmPassword.setUIID("TextFieldBlack");
        confirmPassword.setAlignment(LEFT);
        username.setSingleLineTextArea(false);
        email.setSingleLineTextArea(false);
        password.setSingleLineTextArea(false);
        confirmPassword.setSingleLineTextArea(false);
        Button next = new Button("Next");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        
        Container content = BoxLayout.encloseY(
                new Label("Sign Up", "LogoLabel"),
                new FloatingHint(nom),
                createLineSeparator(),
                new FloatingHint(prenom),
                createLineSeparator(),
                new FloatingHint(username),
                createLineSeparator(),
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                new FloatingHint(confirmPassword),
                createLineSeparator()
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ConnectionRequest req = new ConnectionRequest();
                req.setUrl("http://localhost/pidev2017/insert.php?nom=" + nom.getText() + "&prenom=" + prenom.getText() + "&username=" + username.getText() + "&email=" + email.getText() + "&password=" + password.getText() + "&confirmPassword=" + confirmPassword.getText() + "");
                req.addResponseListener(new ActionListener<NetworkEvent>() {

                    @Override
                    public void actionPerformed(NetworkEvent evt) {
                        byte[] data = (byte[]) evt.getMetaData();
                        String s = new String(data);
                        System.out.println(s);
                        if (s.equals("success")) {
                            Dialog.show("Confirmation", "ajout ok", "Ok", null);
                            new SignInForm(res).show();
                        } else if (s.equals("nom")) {
                            Dialog.show("Erreur", "Le nom ne doit pas être vide et doit être valide", "Ok", null);
                        } else if (s.equals("prenom")) {
                            Dialog.show("Erreur", "Le prenom ne doit pas être vide et doit être valide", "Ok", null);
                        } else if (s.equals("username")) {
                            Dialog.show("Erreur", "Le nom d'utilisateur doit être de cette forme : essai94", "Ok", null);
                        } else if (s.equals("mail")) {
                            Dialog.show("Erreur", "Adresse e-mail incorrecte", "Ok", null);
                        } else if (s.equals("password")) {
                            Dialog.show("Erreur", "Le mot de passe et sa confirmation ne doivent pas être différents", "Ok", null);
                        }
                    }
                });
                 NetworkManager.getInstance().addToQueue(req);
            }
        });
    }
    
}
