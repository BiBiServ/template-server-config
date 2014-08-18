/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.cebitec.bibiserv.statistic.data;

import de.unibi.cebitec.bibiserv.statistic.StatisticTestData;
import static de.unibi.cebitec.bibiserv.statistic.StatisticTestData.random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author jkrueger
 */
public class Status {
    
    private String bibiservid;
    private String sessionid;
    private String id;
    private String fct;
    private int statuscode = 600;
    private int cputime;
    private String interf = "WEB";
    private Timestamp timestamp;
    private String user;

    
    public Status(String id, String fct, String user, Timestamp timestamp){
        this(null,id,fct,user,timestamp);
        interf = "REST";
    }
    
    public Status(String sessionid, String id, String fct, String user, Timestamp timestamp){
        this.sessionid = sessionid;
        this.id = id;
        this.fct = fct;
        cputime = StatisticTestData.random(5, 60*60*24);
        this.timestamp = timestamp;
        this.bibiservid = "statistictest_"+random(10000,99999)+"_"+timestamp.getTime();
        this.user = user;
        
        
    }
    
    public String getBibiservid() {
        return bibiservid;
    }

    public void setBibiservid(String bibiservid) {
        this.bibiservid = bibiservid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFct() {
        return fct;
    }

    public void setFct(String fct) {
        this.fct = fct;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public int getCputime() {
        return cputime;
    }

    public void setCputime(int cputime) {
        this.cputime = cputime;
    }

    public String getInterf() {
        return interf;
    }

    public void setInterf(String interf) {
        this.interf = interf;
    }
    
    
    public void store(Connection con) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("INSERT INTO status (id,toolname,functionname,statuscode,userid,interfacetype,cputime,created,lastmod) VALUES (?,?,?,?,?,?,?,?,?)");
        stmt.setString(1, bibiservid);
        stmt.setString(2, id);
        stmt.setString(3, fct);
        stmt.setInt(4, statuscode);
        stmt.setString(5, user);
        stmt.setString(6, interf);
        stmt.setInt(7,cputime);
        stmt.setTimestamp(8, timestamp);
        stmt.setTimestamp(9, timestamp);
        stmt.execute();
        stmt.close();
        
        if (sessionid != null) {
            stmt = con.prepareStatement("INSERT INTO stats_usage (bibiservid, sessionid) VALUES (?,?)");
            stmt.setString(1,bibiservid);
            stmt.setString(2,sessionid);
            stmt.execute();
            stmt.close();
        }
        
    }
    
    
}
