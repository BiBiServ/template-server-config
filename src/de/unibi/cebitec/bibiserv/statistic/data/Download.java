/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.cebitec.bibiserv.statistic.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * CREATE TABLE stats_download (
    sessionid character varying(100) NOT NULL,
    id character varying(100) NOT NULL,
    url character varying(1024) NOT NULL,
    name character varying(100) NOT NULL,
    timestamp timestamp WITH DEFAULT CURRENT_TIMESTAMP
 );
 * 
 * @author jkrueger
 */
public class Download {
    
    private String sessionid;
    private String id;
    private String url;
    private String name;
    private Timestamp timestamp;

    
    
    public Download(String sessionid, String id, String name, Timestamp timestamp){
        this.sessionid = sessionid;
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        url = "http://resources/"+name;
        
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    
 
    
    public void store (Connection con) throws SQLException{
        PreparedStatement stmt = con.prepareStatement("INSERT INTO stats_download (id,url, name, timestamp,sessionid) VALUES (?,?,?,?,?)");
        stmt.setString(1, id);
        stmt.setString(2, url);
        stmt.setString(3, name);
        stmt.setTimestamp(4, timestamp);
        stmt.setString(5, sessionid);
        stmt.execute();
        stmt.close();
    
    }
}
