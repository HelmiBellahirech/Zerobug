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
import com.codename1.ui.events.ActionListener;
import com.mycompany.Entite.ReservSalle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nadaghanem
 */
public class ReservService implements IntService<ReservSalle>
{
   
    
    @Override
    public void Create(ReservSalle obj) {
        
         ConnectionRequest con = new ConnectionRequest();
      String Url = "http://localhost/Entraide2.0/web/app_dev.php/revision/aj/"
                +obj.getUser()+"/"
                +obj.getSalle()+"/"
                +obj.getNbPersonnes()+"/"
                +obj.getStringdate1()+"/"
                +obj.getStringdate2()+"/"
                +obj.getEtat()+"/"
                +obj.getId_connecte();
        
        con.setUrl(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });        
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    @Override
    public void Delete(ReservSalle obj)
    {
     ConnectionRequest con = new ConnectionRequest();
      String Url = "http://localhost/Entraide2.0/web/app_dev.php/revision/suppRes/"
                +obj.getId();
                
        
        con.setUrl(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });        
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

    @Override
    public void Update(ReservSalle obj)
    {
      ConnectionRequest con = new ConnectionRequest();
      String Url = "http://localhost/Entraide2.0/web/app_dev.php/revision/modif/"
                +obj.getUser()+"/"
                +obj.getSalle()+"/"
                +obj.getNbPersonnes()+"/"
                +obj.getStringdate1()+"/"
                +obj.getStringdate2()+"/"
                +obj.getEtat()+"/"
                +obj.getId();
        
        con.setUrl(Url);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
        });        
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
    @Override
    public ArrayList<ReservSalle> getList() {
        ArrayList<ReservSalle> listRev = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();

        con.setUrl("http://localhost/Entraide2.0/web/app_dev.php/revision/aff");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //listTasks = getListTask(new String(con.getResponseData()));
                JSONParser jsonp = new JSONParser();

                try {
                    Map<String, Object> MesReserv = jsonp.parseJSON(new CharArrayReader(new String(con.getResponseData()).toCharArray()));
                    System.out.println(MesReserv);
                    //System.out.println(tasks);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) MesReserv.get("root");
                    for (Map<String, Object> obj : list) {
                        ReservSalle rev = new ReservSalle();
                    float id=Float.parseFloat(obj.get("id").toString());                 
                       rev.setId((int) id);
                       rev.setUser(obj.get("user").toString());
                      float nbr=Float.parseFloat(obj.get("salle").toString());
                      rev.setSalle((int) nbr);
                      float nbr1=Float.parseFloat(obj.get("nbPersonnes").toString());
                      rev.setNbPersonnes((int) nbr1);
                      float nbr2=Float.parseFloat(obj.get("etat").toString());
                      rev.setEtat((int) nbr2);
//                      float nbr3=Float.parseFloat(obj.get("idConnecte").toString());
//                      rev.setId_connecte((int) nbr3);      
//                        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd H:m");
//                        String dd = formater.format(ReservSalle.getDateTime1) ;
//                        Label date = new Label(dd);
                        Date Date1 = null ;
                        Date Date2 = null ; 
                       try {
                           Map<String, Object> dates = (Map<String, Object>) obj.get("dateTime1");
                           float datess = Float.parseFloat(dates.get("timestamp").toString());
                           Date dateTime1=new Date(( long)(datess - 3600) *1000);    
                       } catch (Exception e) {
                           System.out.println(e.toString());
                       }
                        listRev.add(rev);
                    }
                } catch (IOException ex) {
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listRev;
    }
       @Override
        public ReservSalle get(ReservSalle obj) {
        return null;
        }
    }
