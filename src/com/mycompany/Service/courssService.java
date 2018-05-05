/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.Service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entite.ReservSalle;
import com.mycompany.Entite.courss;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nadaghanem
 */
public class courssService 
{
     public ArrayList<courss> getList() {
        ArrayList<courss> listC = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();

        con.setUrl("http://localhost/Entraide2.0/web/app_dev.php/revision/moncours");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();

                try {
                    Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(tasks);
                    //System.out.println(tasks);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
                    for (Map<String, Object> obj : list) {
                        courss cr = new courss();

                       cr.setModule(obj.get("module").toString());
                       cr.setMatiere(obj.get("matiere").toString());
                       cr.setFichier(obj.get("fichier").toString());
                     
                        String date_pub=obj.get("date_pub").toString();
                        
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m");
                        Date Datee = null ;
                       
                        try
                        {
                            Datee = format.parse(date_pub);
                            
                        }catch(ParseException ex)
                        {
                        System.out.println(ex.getMessage());
                        }
                        cr.setDate_pub(Datee);
                        

                        listC.add(cr);

                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listC;

    
}}
    

