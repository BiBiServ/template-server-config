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

package de.unibi.techfak.bibiserv.util.appserver_config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Simple Ant task that convert an Java Property file to an XML Java property file.
 *
 * Using :
 * 
 * <pre>
 *  &lt;prop2xml src="..." dest="..."/&gt;
 * </pre>
 *
 *
 * @author Jan Krueger (jkrueger(at)cebitec.uni-bielefeld.de)
 */
public class Property2XML extends Task {

    private String src = null;
    private File src_file = null;
    private String dest = null;
    private File dest_file = null;

    public void setSrc(String src) {
        this.src = src;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    @Override
    public void execute() throws BuildException {
        if (src != null) {
            src_file = new File(src);
            if (!src_file.isFile()) {
                throw new BuildException("'src'file +'" + src_file + " not found .");
            }
        } else {
            throw new BuildException("No 'src' source defined! A source property file must be defined! ");
        }

        if (dest != null) {
            dest_file = new File(dest);
        } else {
            throw new BuildException("No 'dest' destination defined! A destinion file path must be defined! ");
        }

        Properties prop = new Properties();
        //read properties from file
        try {
            prop.load(new FileInputStream(src_file));
        } catch (FileNotFoundException e) {
            throw new BuildException("Src '" + src + "' not found ... ", e);
        } catch (IOException e) {
            throw new BuildException(e);
        }

        // write properties to file
        try {
            prop.storeToXML(new FileOutputStream(dest_file), "converted by " + getClass().getName());
        } catch (FileNotFoundException e) {
            throw new BuildException("Dest '" + dest + "' not found ... ", e);
        } catch (IOException e) {
            throw new BuildException(e);
        }

        System.out.println("Read properties from '" + src + "' and write XML properties to '" + dest + "'");
    }
}
