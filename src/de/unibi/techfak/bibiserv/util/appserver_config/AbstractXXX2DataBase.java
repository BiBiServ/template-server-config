/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 BiBiServ Curator Team, http://bibiserv.cebitec.uni-bielefeld.de,
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
 * "Portions Copyrighted 2011 BiBiServ Curator Team"
 *
 * Contributor(s): Jan Krueger
 *
 */ 

package de.unibi.techfak.bibiserv.util.appserver_config;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Abstract Ant task which insert XXX into a database. XXX must be
 * defined by the implementing classes.
 *
 * @author Jan Krüger - jkrueger(at)cebitec.uni-bielefeld.de
 */
public abstract class AbstractXXX2DataBase extends Task {

    private String src = null;

    /**
     * Set the structure source file to be insert into table STRUCTURE.
     *
     * @param src
     */
    public void setSrc(String src) {
        this.src = src;
    }
    private String dbURL = null;

    /**
     * Set the database url (jdbc-string), valid strings are:
     * <ul>
     *  <li>jdbc:derby://localhost:1527/bibiserv2</li>
     *  <li>jdbc:derby://localhost:1527/bibiserv2;user=me;password=mine;</li>
     *  <li>jdbc:derby:bibiserv2 <pre>embedded only</pre></li>
     *  <li> ... </li>
     * </ul>
     *
     * @param dbURL - databasse url (see description above)
     */
    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }
    private boolean embedded = false;

    /**
     * Set the optional attribute embedded. Forces the task to use the embedded driver
     * instead the network driver.
     *
     * @param embedded
     */
    public void setEmbedded(boolean embedded) {
        this.embedded = embedded;
    }
    
    
    /**
     * Check if given file contains the expected content.
     * 
     * @param src_file
     * @throws Exception in the case 
     */
    public abstract void checkFile(File src_file) throws Exception;
    
    /**
     * Insert src_file content into database
     * 
     * @param con
     * @param src_file 
     */
    public abstract void insertSQL(Connection con, File src_file) throws FileNotFoundException, SQLException;
    
    private Connection con = null;
    

    @Override
    public void execute() throws BuildException {
        if (dbURL == null) {
            throw new BuildException("Attribute 'dbURL' is mandantory und must be valid jdbcURL!");
        }
        if (src == null) {
            throw new BuildException("Attribute 'src' is mandantory!");
        }
        File src_file = new File(src);
        if (!src_file.isFile()) {
            throw new BuildException("'src'file +'" + src_file + "' not found!");
        }
        checkFile(src_file);

        try {
            createConnection();
            insertSQL(con,src_file);
        } catch (SQLException ex) {
            Logger.getLogger(Structure2DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Structure2DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            shutdown();
        }
    }

    protected void createConnection() {
        try {
            if (embedded) {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            } else {
                Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            }
            //Get a connection
            con = DriverManager.getConnection(dbURL);
        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    protected void shutdown() {
        try {
            if (con != null) {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                con.close();
            }
        } catch (SQLException sqlExcept) {
        }

    }
    
    
}
