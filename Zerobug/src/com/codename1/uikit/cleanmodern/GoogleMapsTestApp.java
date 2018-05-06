/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.InteractionDialog;
import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import static com.codename1.uikit.cleanmodern.GoogleMapsGeocodeTest.getCoords;
import com.mycompany.Entite.Covoiturage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author habib
 */
public class GoogleMapsTestApp extends BaseForm {

    private static final String HTML_API_KEY = "AIzaSyAuE9d29HjPxeJ4WNbbapJjegGRala6lvg";
    private Form current;
    private static final String MAPS_KEY = "AIzaSyAuE9d29HjPxeJ4WNbbapJjegGRala6lvg";

    public void init(Object context) {

        try {
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
            Display.getInstance().setCommandBehavior(Display.COMMAND_BEHAVIOR_SIDE_NAVIGATION);
            UIManager.getInstance().getLookAndFeel().setMenuBarClass(SideMenuBar.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Coord getCoords(String address) {
        Coord ret = null;
        try {
            ConnectionRequest request = new ConnectionRequest("https://maps.googleapis.com/maps/api/geocode/json", false);
            request.addArgument("key", HTML_API_KEY);
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

    public void start(Resources res, Covoiturage r) {

        if (current != null) {
            current.show();
            return;
        }
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);

        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());

        Form hi = new Form("Localisation");
        hi.setLayout(new BorderLayout());
        DetailsCovoiturage dc = new DetailsCovoiturage(res, r);
        final MapContainer cnt = new MapContainer(HTML_API_KEY);
        final MapContainer cnt1 = new MapContainer(HTML_API_KEY);

        Coord coords = getCoords(dc.depart);
        Coord coords1 = getCoords(dc.arrive);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + coords.getLatitude());
        Button btnMoveCamera = new Button("Retour");
        btnMoveCamera.addActionListener(e -> {
            previous.showBack();
        });
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(3));
        FontImage markerImg1 = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(3));

        Button btnAddMarker = new Button("Show Marker");
        btnAddMarker.addActionListener(e -> {

            cnt.setCameraPosition(coords);
            cnt1.setCameraPosition(coords1);

            cnt.addMarker(
                    EncodedImage.createFromImage(markerImg, false),
                    cnt.getCameraPosition(),
                    "Hi marker",
                    "Optional long description",
                    evt -> {
                        ToastBar.showMessage("Depart", FontImage.MATERIAL_PLACE);
                    }
            );
            cnt1.setCameraPosition(coords1);
            cnt.addMarker(
                    EncodedImage.createFromImage(markerImg1, false),
                    cnt1.getCameraPosition(),
                    "Hi marker",
                    "Optional long description",
                    evt -> {
                        ToastBar.showMessage("Arrive", FontImage.MATERIAL_PLACE);
                    }
            );

        });

        Button btnAddPath = new Button("Add Path");
        btnAddPath.addActionListener(e -> {

            cnt.addPath(
                    cnt.getCameraPosition(),
                    coords, coords1
            );
        });

        Button btnClearAll = new Button("Clear All");
        btnClearAll.addActionListener(e -> {
            cnt.clearMapLayers();
        });

        Container root = LayeredLayout.encloseIn(
                BorderLayout.center(cnt),
                 BorderLayout.north(
                        FlowLayout.encloseBottom(btnMoveCamera)
                ),
                BorderLayout.south(
                        FlowLayout.encloseBottom(btnAddMarker, btnAddPath)
                )
        );

        hi.add(BorderLayout.CENTER, root);
        hi.show();

    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }

}
