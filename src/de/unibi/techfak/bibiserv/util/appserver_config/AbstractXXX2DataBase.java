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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Abstract Ant task which insert XXX into a database. XXX must be defined by
 * the implementing classes.
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
    protected String dbURL = null;

    /**
     * Set the database url (jdbc-string), valid strings are:
     * <ul>
     * <li>jdbc:derby://localhost:1527/bibiserv2</li>
     * <li>jdbc:derby://localhost:1527/bibiserv2;user=me;password=mine;</li>
     * <li>jdbc:derby:bibiserv2
     * <pre>embedded only</pre></li>
     * <li> ... </li>
     * </ul>
     *
     * @param dbURL - databasse url (see description above)
     */
    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }
    protected boolean embedded = false;

    /**
     * Set the optional attribute embedded. Forces the task to use the embedded
     * driver instead the network driver.
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
    public abstract void insertSQL(Connection con, File src_file) throws Exception;

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
        try {
            checkFile(src_file);
        } catch (Exception e) {
            throw new BuildException(e);
        }

        try {
            createConnection();
            insertSQL(con, src_file);
        } catch (Exception ex) {
            Logger.getLogger(Structure2DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (embedded) {
                shutdown();
            }
        }
    }

    protected Connection createConnection() throws Exception {

        if (embedded) {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        } else {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
        }
        //Get a connection
        con = DriverManager.getConnection(dbURL);
        return con;
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

    /**
     * Extract (unzip) a specified entry from zipped buffer.
     *
     * @param in - The zipped Inputstream buffer
     * @param entry - Name of entry to be extract
     * @return Returns a byte [] buffer of the unzipped entry.
     * @throws Exception in a case the entry was not found.
     */
    public static byte[] extractNamedEntryfromZippedBuffer(InputStream in, String entry) throws Exception {
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry zipentry;

        byte[] tmp = null;

        while ((zipentry = zin.getNextEntry()) != null) {
            if (zipentry.getName().equals(entry)) {
                tmp = new byte[(int) zipentry.getSize()];
                int readbytes = 0;
                while (tmp.length > readbytes) {
                    readbytes += zin.read(tmp, readbytes, tmp.length - readbytes);
                }
                break;
            }
        }
        zin.close();
        // if zipstream does not contain a matching entry, the tmp byte [] is null.
        if (tmp == null) {
            throw new Exception("Zipped Buffer does not contain an entry '" + entry + "'!");
        }

        return tmp;
    }

    public static byte[] createChecksum(File filename) throws Exception {
        InputStream fis = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    public static String getMD5Checksum(File filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result
                    += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

}
