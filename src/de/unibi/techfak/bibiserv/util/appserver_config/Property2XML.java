/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.techfak.bibiserv.util.appserver_config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 *
 * @author jkrueger
 */
public class Property2XML extends Task{

        private String src = null;
    private File src_file = null;
    private String dest = null;
    private File dest_file = null;

    private void setSrc(String src) {
        this.src = src;
    }

    private void setDest(String dest) {
        this.dest = dest;
    }

    @Override
    public void execute() throws BuildException {
        if (src == null) {
            src_file = new File(src);
            if (!src_file.isFile()) {
                throw new BuildException("Srcfile +'" + src_file + " not found .");
            }
        }

        Properties prop = new Properties();
        //read properties from file
        try {
            prop.load(new FileInputStream(src_file));
            // write properties to file
            prop.storeToXML(new FileOutputStream(new File(dest)),"converted by "+getClass().getName());

         } catch (IOException e) {
            throw new BuildException(e);
        }

    }

}
