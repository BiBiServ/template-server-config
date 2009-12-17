package de.unibi.techfak.bibiserv.util.appserver_config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 *
 * @author dhagemei
 */
public class XML2Database extends Task {

    private String src = null;
    private File src_file = null;

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public void execute() throws BuildException {
        if (src != null) {

            src_file = new File(src);
            if (!src_file.isFile()) {
                throw new BuildException("'src'file +'" + src_file + " not found .");
            } else {
                //FileInputStream is;
                //is = new FileInputStream(src);
                FileReader ir = null;
                try {
                    org.apache.derby.jdbc.ClientDataSource ds = new org.apache.derby.jdbc.ClientDataSource();
                    ds.setDatabaseName("bibiserv2");
                    ds.setPortNumber(1527);
                    ds.setServerName("localhost");
                    ds.setUser("app");
                    ds.setPassword("app");
                    ir = new FileReader(src_file);
                    //BufferedInputStream bis = new BufferedInputStream(is);
                    BufferedReader br = new BufferedReader(ir);
                    //System.out.println("Content of xml-file :");
                    /**
                     * While loop fgor looking at content of xml file
                     */
                    //String s;
                    //while ((s = br.readLine()) != null) {
                    //    System.out.print(s);
                    //}
                    /**
                     * Block where the xml description is saved to DB
                     *
                     * ...can be replaced/deleted when this step happens by ant
                     */
                    Connection con = ds.getConnection();
                    //XML has to
                    PreparedStatement stmnt = con.prepareStatement("INSERT INTO STRUCTURE (TIME, CONTENT) VALUES (CURRENT_TIMESTAMP, XMLPARSE (DOCUMENT CAST (? AS CLOB) PRESERVE WHITESPACE))");
                    stmnt.setClob(1, br);
                    stmnt.execute();
                    System.out.println("Now reading from filled DB...");
                } catch (SQLException ex) {
                    Logger.getLogger(XML2Database.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(XML2Database.class.getName()).log(Level.SEVERE, null, ex);
                } 
                finally {
                    try {
                        ir.close();
                    } catch (IOException ex) {
                        Logger.getLogger(XML2Database.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        } else {
            throw new BuildException("No 'src' source defined! A source property file must be defined! ");
        }

    }
}
