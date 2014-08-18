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
 * CREATE TABLE stats_clicks ( sessionid character varying(100) NOT NULL, id
 * character varying(100) NOT NULL, userid character varying(100) NOT NULL,
 * timestamp timestamp WITH DEFAULT CURRENT_TIMESTAMP );
 *
 *
 * @author jkrueger
 */
public class Clicks {

    private String sessionid;
    private String id;
    private String userid;
    private Timestamp timestamp;

    public Clicks(String sessionid, String id, String user, Timestamp timestamp) {
        this.sessionid = sessionid;
        this.userid = user;
        this.id = id;
        this.timestamp = timestamp;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

   
    public void store(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO stats_clicks (id,userid, timestamp,sessionid) VALUES (?,?,?,?)");
        stmt.setString(1, id);
        stmt.setString(2, userid);
        stmt.setTimestamp(3, timestamp);
        stmt.setString(4, sessionid);
        stmt.execute();
        stmt.close();

    }

}
