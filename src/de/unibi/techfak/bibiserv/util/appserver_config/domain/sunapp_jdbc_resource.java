/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.techfak.bibiserv.util.appserver_config.domain;

import generated.JdbcResource;
import generated.ObjectType;

/**
 *
 * @author jkrueger
 */
public class sunapp_jdbc_resource {


    private JdbcResource jdbc_resource  = new JdbcResource();

    public sunapp_jdbc_resource(){

    }
    
    public JdbcResource getJdbcResource(){
        return jdbc_resource;   
    }


    public void setEnabled(boolean value){
        jdbc_resource.setEnabled(Boolean.toString(value));
    }

    public boolean getEnabled(){
        return Boolean.parseBoolean(jdbc_resource.getEnabled());
    }

    public void setJndiname(String name){
        jdbc_resource.setJndiName(name);
    }

    public String getJndiname(){
        return jdbc_resource.getJndiName();
    }

    public void setObjecttype(String type){
        jdbc_resource.setObjectType(ObjectType.fromValue(type));
    }

    public String getObjecttype(){
        return jdbc_resource.getObjectType().value();
    }

    public void setPoolname(String name){
        jdbc_resource.setPoolName(name);
    }

    public String getPoolname(){
        return jdbc_resource.getPoolName();
    }


}
