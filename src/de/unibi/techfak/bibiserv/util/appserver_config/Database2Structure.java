package de.unibi.techfak.bibiserv.util.appserver_config;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Ant task that select a bibiserv structure file from a table STRUCTURE.
 * The table is defined as follows :
 * <pre>
 *  create table structure (
 *      time timestamp,
 *      content clob
 *  );
 * 
 * </pre>
 *
 * @author Jan Krüger - jkrueger(at)cebitec.uni-bielefeld.de
 */
public class Database2Structure extends Task {

    private String dst = null;

    /**
     * Set the structure destintion file to be insert into table STRUCTURE.
     *
     * @param src
     */
    public void setSrc(String dst) {
        this.dst = dst;
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
    private Connection con = null;
    private PreparedStatement stmt = null;

    @Override
    public void execute() throws BuildException {
        if (dbURL == null) {
            throw new BuildException("Attribute 'dbURL' is mandantory und must be valid jdbcURL!");
        }




        try {
            createConnection();
            stmt = con.prepareStatement("SELECT CONTENT FROM STRUCTURE WHERE TIME=(SELECT MAX(TIME) FROM STRUCTURE)");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Clob clob = rs.getClob("CONTENT");
                if (dst != null) {
                    File dst_file = new File(dst);
                    OutputStream out = new BufferedOutputStream(new FileOutputStream(dst_file));
                    InputStream in = clob.getAsciiStream();
                    byte[] buf = new byte[4096];
                    int bytes_read = 0;
                    while ((bytes_read = in.read(buf)) != -1) {
                        out.write(buf, 0, bytes_read);
                    }
                    out.close();
                    in.close();
                    System.out.println("Wrote content of Clob to file '" + dst_file + "'!");
                } else {
                    System.out.println("Destination is null, so print Clob to STDOUT!");
                    System.out.println(clob.getSubString(1, (int) clob.length()));
                }
            } else {
                System.out.println("call next() 'SELECT CONTENT FROM STRUCTURE WHERE TIME=(SELECT MAX(TIME) FROM STRUCTURE)' returns false ...");
            }


        } catch (SQLException ex) {
            Logger.getLogger(Database2Structure.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database2Structure.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            shutdown();
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
