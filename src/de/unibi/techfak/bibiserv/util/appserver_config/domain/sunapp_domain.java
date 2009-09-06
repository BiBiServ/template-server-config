/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.techfak.bibiserv.util.appserver_config.domain;

import generated.Domain;
import generated.JdbcConnectionPool;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 *
 * @author jkrueger
 */
public class sunapp_domain extends Task {

    private String fn = null;

    /**
     * mandantory attribute @file
     *
     * @param fn - path to domain.xml file
     */
    public void setFile(String fn) {
        this.fn = fn;
    }

    @Override
    public void execute() throws BuildException {
        Domain domain = null;

        // check if file is set ...
        if (fn == null) {
            throw new BuildException("Attribute src must be set (point either to a new file or an existing one!");
        }

        File file = new File(fn);
        // check if file points to an existing domain configuration file
        if (file.exists() && file.isFile()) {
            try {
                JAXBContext jaxbc = JAXBContext.newInstance(generated.Domain.class);
                Unmarshaller um = jaxbc.createUnmarshaller();
                domain = (Domain) um.unmarshal(file);
                // create a copy of current desription in a file named ${fn}.old
                // @TODO : instead of .old use current date (yy_mm_dd_hh_mm) !
                marshallToFile(new File(fn+".old"), domain);

            } catch (JAXBException e) {
                System.err.println("An JAXBException occured (fn :" + file.toString() + "):");
                throw new BuildException(e);
            }

        } else {
            domain = new Domain();
        }

        // now modify file


        
        // and write on disk
        try {
            marshallToFile(file, domain);
        } catch (JAXBException e){
            System.err.println("An JAXNException occurred while marshal current domain to file!");
            throw new BuildException(e);
        }
    }

    private void marshallToFile(File file, Domain domain) throws JAXBException {

            JAXBContext jaxbc = JAXBContext.newInstance(generated.Domain.class);
            Marshaller m = jaxbc.createMarshaller();
            m.marshal(domain, file);
            System.out.println("Write "+file.toString()+ " ...");

       

    }

    // JdbcConnectionPool subelement(s)
    List<JdbcConnectionPool> list_of_jdbcconnectionpool = new ArrayList<JdbcConnectionPool>();

    public JdbcConnectionPool createSunapp_jdbc_connection_pool() {
        sunapp_jdbc_connection_pool tmp = new sunapp_jdbc_connection_pool();
        list_of_jdbcconnectionpool.add(tmp.getJdbcConnectionPool());
        return tmp.getJdbcConnectionPool();
    }

    // Systemproperty subelement(s)
    List<sunapp_property> list_of_systemproperties = new ArrayList<sunapp_property>();

    public sunapp_property createSunapp_property() {
        sunapp_property tmp = new sunapp_property();
        list_of_systemproperties.add(tmp);
        return tmp;
    }
}
