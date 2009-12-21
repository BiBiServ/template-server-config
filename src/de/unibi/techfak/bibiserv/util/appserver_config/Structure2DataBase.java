package de.unibi.techfak.bibiserv.util.appserver_config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Ant task that inserts a bibiserv structure file (as CLOB) in a table STRUCTURE.
 * The table is defined as follows :
 * <pre>
 *  create table structure (
 *      time timestamp,
 *      content clob
 *  );
 * 
 * </pre>
 *
 * @author Daniel Hagemeier - dhagemei(at)cebitec.uni-bielefeld.de
 *         Jan Krüger - jkrueger(at)cebitec.uni-bielefeld.de
 */
public class Structure2DataBase extends Task {

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
    public void setEmbedded(boolean embedded){
        this.embedded = embedded;
    }
    private Connection con = null;
    private PreparedStatement stmt = null;

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
            throw new BuildException("'src'file +'" + src_file + " not found .");
        }
       FileReader ir = null;
       try {
            createConnection();
            stmt = con.prepareStatement("INSERT INTO STRUCTURE (TIME, CONTENT) VALUES (CURRENT_TIMESTAMP, (? AS CLOB))");
            stmt.setClob(1, new BufferedReader(new FileReader(src_file)));
            stmt.execute();
            System.out.println("Written Structure of BiBiServ to '"+dbURL+"' into table STRUCTURE");
        } catch (SQLException ex) {
            Logger.getLogger(Structure2DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Structure2DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ir.close();
                shutdown();
            } catch (IOException ex) {
                Logger.getLogger(Structure2DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void createConnection() {
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

    private void shutdown() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                con.close();
            }
        } catch (SQLException sqlExcept) {
        }

    }
}
