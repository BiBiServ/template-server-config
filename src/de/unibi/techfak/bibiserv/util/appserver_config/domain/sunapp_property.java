package de.unibi.techfak.bibiserv.util.appserver_config.domain;

import generated.Property;

/**
 * A simple representation of the property tag used within sunapp-domain.xml
 *
 * @author jkrueger
 */
public class sunapp_property {


    Property property = new Property();

    /** simple constructor */
    public sunapp_property(){}


    public Property getProperty(){
        return property;
    }

    public void setKey(String key){
       property.setName(key);
    }

    public void setName(String key){
        property.setName(key);
    }

    public String getKey(){
        return property.getName();
    }

    public String getName(){
        return property.getName();
    }

    public void setValue(String value){
        property.setValue(value);
    }

    public String getValue(){
        return property.getValue();
    }


}
