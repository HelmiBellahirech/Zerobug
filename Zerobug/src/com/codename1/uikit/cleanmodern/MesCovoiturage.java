/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.Entite.Covoiturage;
import com.mycompany.Entite.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author OrbitG
 */
public class MesCovoiturage extends BaseForm{
     ConnectionRequest con;
    Covoiturage c;

    public MesCovoiturage(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Esprit Entraide ");
       
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();

        addTab(swipe, res.getImage("cov.jpg"), spacer1, "", "", "Bienvenue !");
        addTab(swipe, res.getImage("cov1.jpg"), spacer2, "", "", "Bienvenue !");

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        Container al = new Container();
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("Mes Annonces", barGroup);
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("Mes annonces", barGroup);
        featured.setUIID("SelectBar");
//       
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(1, all),
                FlowLayout.encloseBottom(arrow)
        ));

        all.setSelected(true);
        arrow.setVisible(true);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);

        });
        bindButtonSelection(all, arrow);
     
//        bindButtonSelection(popular, arrow);
        //   bindButtonSelection(myFavorite, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        // EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(100, 100, 0xffff0000), true);
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/PIDEV/Entraide2.0/web/app_dev.php/mobile/alluser/"+Session.getId()+"");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                List<Covoiturage> list = new ArrayList<>(getListLogement(new String(con.getResponseData())));

                for (Covoiturage o : list) {
                    Storage.getInstance().clearStorage();
                    EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(100, 100, 0xffff0000), true);
                    Image i = URLImage.createToStorage(placeholder, "S" + o.getDepart(), "http://localhost/PIDEV/Entraide2.0/web/eventupload/hh", URLImage.RESIZE_SCALE);

                    addButton(i, o.getDepart(), false, 26, 32, res, o);
                    System.out.println(o.getDepart());

                }
            }
        });
        NetworkManager.getInstance().addToQueue(con);
   

        
    }

    public ArrayList<Covoiturage> getListLogement(String json) {
        ArrayList<Covoiturage> listLogements = new ArrayList<>();
        System.out.println("Ena fi Affiche ");
        try {

            JSONParser j = new JSONParser();

            Map<String, Object> logements = j.parseJSON(new CharArrayReader(json.toCharArray()));

            System.out.println(logements);
            List<Map<String, Object>> list = (List<Map<String, Object>>) logements.get("root");
            for (Map<String, Object> obj : list) {
                Covoiturage l = new Covoiturage();
                l.setDepart(obj.get("depart").toString());
                l.setArrive(obj.get("arrive").toString());
                l.setComfort(obj.get("comfort").toString());
                System.out.println(((LinkedHashMap) obj.get("date")).get("timestamp").toString());

                String f1 = ((LinkedHashMap) obj.get("date")).get("timestamp").toString().substring(0, 1);
                String f2 = ((LinkedHashMap) obj.get("date")).get("timestamp").toString().substring(2, 9);
                String f3 = f1 + f2 + "00";
                System.out.println(f3);
                System.out.println(f3);
                Long s1 = Long.parseLong(f3);
                Long s2 = Long.parseLong("86400");
                Long s3 = s1 + s2;
                Long s4 = s3 * 1000;
                System.out.println("**************" + s4);
                //   System.out.println((Long)Long.parseLong(f3)*1000+24*3600);
                Date d = new Date(s4);
                DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println(f.format(d));

                String f11 = ((LinkedHashMap) obj.get("dateSys")).get("timestamp").toString().substring(0, 1);
                String f22 = ((LinkedHashMap) obj.get("dateSys")).get("timestamp").toString().substring(2, 9);
                String f33 = f11 + f22 + "00";
                System.out.println(f33);
                System.out.println(f33);
                Long s11 = Long.parseLong(f33);
                Long s22 = Long.parseLong("86400");
                Long s33 = s11 + s22;
                Long s44 = s33 * 1000;
                System.out.println("**************" + s44);
                //   System.out.println((Long)Long.parseLong(f3)*1000+24*3600);
                Date d1 = new Date(s44);
                DateFormat f0 = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println(f.format(d));
                l.setDate(f.format(d));
                l.setDate_sys(f0.format(d1));
//                l.setDate_sys(obj.get("date_sys").toString());
                // l.setNbrPlaces(Integer.parseInt(String.valueOf(obj.get("nbrplaces"))));
                l.setFumeur(obj.get("fumeur").toString());
                System.out.println(((LinkedHashMap) obj.get("heure")).get("timestamp").toString());
                String h = ((LinkedHashMap) obj.get("heure")).get("timestamp").toString().substring(0, 4);
                long h1 = Long.parseLong(h);
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                String time = df.format(h1);
                l.setHeure(time);
                l.setPrix(Float.parseFloat(obj.get("prix").toString()));
                float a = Float.parseFloat(obj.get("nbrplaces").toString());
                l.setNbrPlaces((int) a);
                listLogements.add(l);

            }
            System.out.println("Quitter Logement");

        } catch (IOException ex) {
        }
        return listLogements;

    }

    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();

    }

    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        FlowLayout.encloseIn(likes, comments),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

    private void addButton(Image img, String title, boolean liked, int likeCount, int commentCount, Resources res, Covoiturage l) {
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        cnt.setLeadComponent(image);
        TextArea ta = new TextArea(title);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);

        Label likes = new Label(likeCount + " Likes  ", "NewsBottomLine");
        likes.setTextPosition(RIGHT);
        if (!liked) {
            FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);
        } else {
            Style s = new Style(likes.getUnselectedStyle());
            s.setFgColor(0xff2d55);
            FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
            likes.setIcon(heartImage);
        }
        Label comments = new Label(commentCount + " Comments", "NewsBottomLine");
        FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);

        cnt.add(BorderLayout.CENTER,
                BoxLayout.encloseY(
                        ta,
                        BoxLayout.encloseX(likes, comments)
                ));
        add(cnt);
        image.addActionListener(e -> new DetailsCovoiturage(res, l).show()); //ToastBar.showMessage(title, FontImage.MATERIAL_INFO));
    }

    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if (b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
}
