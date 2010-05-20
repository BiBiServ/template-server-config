package de.unibi.techfak.bibiserv.util.appserver_config.domain;

import generated.SystemProperty;

/**
 * A simple representation of the system property tag used within sunapp-domain.xml
 *
 * @author jkrueger
 */
public class sunapp_systemproperty {

    SystemProperty property = new SystemProperty();

    /** simple constructor */
    public sunapp_systemproperty() {
    }

    public SystemProperty getProperty() {
        return property;
    }

    public void setKey(String key) {
        property.setName(key);
    }

    public void setName(String key) {
        property.setName(key);
    }

    public String getKey() {
        return property.getName();
    }

    public String getName() {
        return property.getName();
    }

    public void setValue(String value) {
        property.setValue(value);
    }

    public String getValue() {
        return property.getValue();
    }
}
