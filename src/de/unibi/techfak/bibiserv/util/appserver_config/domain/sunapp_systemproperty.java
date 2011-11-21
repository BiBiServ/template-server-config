/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 BiBiServ Curator Team, http://bibiserv.cebitec.uni-bielefeld.de,
 * All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common
 * Development and Distribution License("CDDL") (the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at http://www.sun.com/cddl/cddl.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.  When distributing the software, include
 * this License Header Notice in each file.  If applicable, add the following
 * below the License Header, with the fields enclosed by brackets [] replaced
 *  by your own identifying information:
 *
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 */ 

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
