package de.unibi.techfak.bibiserv.util.appserver_config.domain;

/**
 * A simple representation of the property tag used within sunapp-domain.xml
 *
 * @author jkrueger
 */
public class sunapp_property {

    private String key = null;

    private String value = null;

    /** simple constructor */
    public sunapp_property(){}


    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }


}
