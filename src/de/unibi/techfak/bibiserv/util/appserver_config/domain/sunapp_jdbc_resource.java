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

import generated.JdbcResource;
import generated.ObjectType;

/**
 *
 * @author jkrueger
 */
public class sunapp_jdbc_resource {

    private JdbcResource jdbc_resource = new JdbcResource();

    public sunapp_jdbc_resource() {
    }

    public JdbcResource getJdbcResource() {
        return jdbc_resource;
    }

    public void setEnabled(boolean value) {
        jdbc_resource.setEnabled(Boolean.toString(value));
    }

    public boolean getEnabled() {
        return Boolean.parseBoolean(jdbc_resource.getEnabled());
    }

    public void setJndiname(String name) {
        jdbc_resource.setJndiName(name);
    }

    public String getJndiname() {
        return jdbc_resource.getJndiName();
    }

    public void setObjecttype(String type) {
        jdbc_resource.setObjectType(ObjectType.fromValue(type));
    }

    public String getObjecttype() {
        return jdbc_resource.getObjectType().value();
    }

    public void setPoolname(String name) {
        jdbc_resource.setPoolName(name);
    }

    public String getPoolname() {
        return jdbc_resource.getPoolName();
    }
}
