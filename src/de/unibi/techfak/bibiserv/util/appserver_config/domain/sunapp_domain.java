/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.techfak.bibiserv.util.appserver_config.domain;

import generated.JdbcConnectionPool;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 *
 * @author jkrueger
 */
public class sunapp_domain extends Task{

    private String fn = null;

    /**
     * mandantory attribute @file
     *
     * @param fn - path to domain.xml file
     */
    public void setFile(String fn){
        this.fn= fn;
    }


    @Override
    public void execute() throws BuildException {

    }


    // JdbcConnectionPool subelement
    List<JdbcConnectionPool> list_of_jdbcconnectionpool = new ArrayList<JdbcConnectionPool>();

    public JdbcConnectionPool createSunapp_jdbc_connection_pool() {
        sunapp_jdbc_connection_pool tmp = new sunapp_jdbc_connection_pool();
        list_of_jdbcconnectionpool.add(tmp.getJdbcConnectionPool());
        return tmp.getJdbcConnectionPool();
    }



    
}
