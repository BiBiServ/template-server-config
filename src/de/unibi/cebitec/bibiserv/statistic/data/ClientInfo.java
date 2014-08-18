/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.cebitec.bibiserv.statistic.data;

import de.unibi.cebitec.bibiserv.statistic.StatisticTestData;
import de.unibi.cebitec.bibiserv.statistic.StatisticTestData.Browser;
import de.unibi.cebitec.bibiserv.statistic.StatisticTestData.COUNTRY;
import de.unibi.cebitec.bibiserv.statistic.StatisticTestData.Device;
import de.unibi.cebitec.bibiserv.statistic.StatisticTestData.OS;
import static de.unibi.cebitec.bibiserv.statistic.StatisticTestData.random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.Timestamp;

/**
 *
 * 
 *      CREATE TABLE stats_clientinfo (
    sessionid character varying(100) NOT NULL,
    browsername character varying(50),
    browserversion character varying(50),
    device character varying(50),
    ua character varying(200),
    os character varying(50),
    ip character varying(100),
        country character varying(3),
    timestamp timestamp WITH DEFAULT CURRENT_TIMESTAMP
);
 * @author jkrueger
 */
public class ClientInfo {
    
    private String sessionid;
    private String browsername;
    private String browserversion;
    private String device;
    private String ua;
    private String os;
    private String ip;
    private String country;
    private Timestamp timestamp;
    
    
    
    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getBrowsername() {
        return browsername;
    }

    public void setBrowsername(String browsername) {
        this.browsername = browsername;
    }

    public String getBrowserversion() {
        return browserversion;
    }

    public void setBrowserversion(String browserversion) {
        this.browserversion = browserversion;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    
    
    public static ClientInfo create(){
        return create(new Timestamp(System.currentTimeMillis()));
    }
    
    public static ClientInfo create(Timestamp timestamp){
        ClientInfo ci = new ClientInfo();
        // sessionid
        ci.setSessionid(Double.toString(Math.random()*10000000*Math.PI));
        // browsername
        ci.setBrowsername(StatisticTestData.Browser.values()[random(Browser.values().length-1)].name());
        // browserversion
        switch (ci.getBrowsername()) {
            case "Firefox": 
                ci.setBrowserversion(Integer.toString(random(10,29)));
                break;
            case "Chrome":
                ci.setBrowserversion(Integer.toString(random(30,36)));
                break;                  
            case "InternetExplorer":
                ci.setBrowserversion(Integer.toString(random(9,11)));
                break;
            case "Safari":
                ci.setBrowserversion(Integer.toString(random(5,7)));
                break;           
            default:
                ci.setBrowserversion("unknown");
        }
        // Device @ToDo: OS,Device, Browser don't fit together
        ci.setDevice(Device.values()[random(Device.values().length-1)].name());
        // OS @ToDo: OS,Device, Browser don't fit together
        ci.setOs(OS.values()[random(OS.values().length-1)].name());
        // ip
        ci.setIp(StatisticTestData.random(2,255)+"."+random(2,255)+","+random(2,255)+","+random(2,255));
        // country
        ci.setCountry(COUNTRY.values()[random(COUNTRY.values().length-1)].name());
        // timestamp
        ci.setTimestamp(timestamp);
        return ci;
    }
 
    

    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(sessionid).append(" ").append(browsername).append("[").append(browserversion).append("]")
                .append(" - ").append(os).append(" on ").append(device)
                .append(" device at ").append(timestamp).append(" from ")
                .append(country);
        return sb.toString();
    }

    

    
        public void store(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("INSERT INTO stats_clientinfo (sessionid,browsername,browserversion,device,ua,os,ip,country,timestamp) VALUES (?,?,?,?,?,?,?,?,?)");
        stmt.setString(1, sessionid);
        stmt.setString(2, browsername);
        stmt.setString(3, browserversion);
        stmt.setString(4, device);
        stmt.setString(5, ua);
        stmt.setString(6, os);
        stmt.setString(7, ip);
        stmt.setString(8, country);
        stmt.setTimestamp(9, timestamp);
        stmt.execute();
        stmt.close();

    }

}
