/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.util.Callback;
import com.mycompany.Entite.Covoiturage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GoogleMapsGeocodeTest extends BaseForm {
    private static final String MAPS_KEY = "AIzaSyAuE9d29HjPxeJ4WNbbapJjegGRala6lvg"; // Your maps key here
    private Form current;
    

    public void init(Object context) {
        try {
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(Resources res , Covoiturage r ) {
        if (current != null) {
            current.show();
            return;
        }
        Form hi = new Form("Maps Geocode Test");
        hi.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        
Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());

DetailsCovoiturage dc = new DetailsCovoiturage(res, r);
        Button coord = new Button("Get Coord");
        coord.addActionListener(evt -> {
            Coord coords = getCoords(dc.depart);
       
            coords.getLongitude();
            ToastBar.showErrorMessage(  "haboub"+   coords.getLatitude());
        });

        hi.add(coord);
        hi.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }



    public static Coord getCoords(String address) {
        Coord ret = null;
        try {
            ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/geocode/json", false);
            request.addArgument("key", MAPS_KEY);
            request.addArgument("address", address);

            NetworkManager.getInstance().addToQueueAndWait(request);
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData()), "UTF-8"));
            if (response.get("results") != null) {
                ArrayList results = (ArrayList) response.get("results");
                if (results.size() > 0) {
                    LinkedHashMap location = (LinkedHashMap) ((LinkedHashMap) ((LinkedHashMap) results.get(0)).get("geometry")).get("location");
                    ret = new Coord((double) location.get("lat"), (double) location.get("lng"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

  
}