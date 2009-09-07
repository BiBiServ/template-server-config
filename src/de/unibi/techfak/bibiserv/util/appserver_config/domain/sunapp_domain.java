/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibi.techfak.bibiserv.util.appserver_config.domain;

import generated.Config;
import generated.Configs;
import generated.Domain;
import generated.JdbcConnectionPool;
import generated.JdbcResource;
import generated.Property;
import generated.ResourceRef;
import generated.Resources;
import generated.Server;
import generated.Servers;
import java.io.File;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
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
                marshallToFile(new File(fn + ".old"), domain);

            } catch (JAXBException e) {
                System.err.println("An JAXBException occured (fn :" + file.toString() + "):");
                throw new BuildException(e);
            }

        } else {
            domain = new Domain();
        }

        // get resources list
        Resources resources = domain.getResources();
        if (resources == null) {
            resources = new Resources();
            domain.setResources(resources);
        }
        List<Object> list_of_resources = resources.getCustomResourceOrExternalJndiResourceOrJdbcResource();

        // get config named server ...

        Config config = null;
        if (domain.getConfigs() == null) {
            System.out.println("No <Config> present ... start with an empty Configuration. ATTENTION! This is normally not a good idea" +
                    "to start with an empty configuration. Start with an fresh unpacked and unmodified domain.xml instead.");
            Configs configs = new Configs();
            domain.setConfigs(configs);
            configs.getConfig().add(config = new Config());
            config.setName("server-config");
        } else {
            try {
                config = (Config) resourceexists(
                        Config.class,
                        Config.class.getMethod("getName"),
                        domain.getConfigs().getConfig(),
                        "server-config");
                if (config == null) {
                    throw new BuildException("No 'server-config' found, abort generation. Start with an empty and unmodified domain.xml!");
                }
            } catch (NoSuchMethodException e) {
                throw new BuildException(e);
            }

        }

        // get server config-ref

        Server server = null;
        if (domain.getServers() == null) {
            System.out.println("No <Server> present ... start with an empty <Server> tag. ATTENTION! This is normally not a good idea" +
                    "to start with an empty <Server> tag. Start with an fresh unpacked and unmodified domain.xml instead.");
            Servers servers = new Servers();
            domain.setServers(servers);
            servers.getServer().add(server=new Server());
            server.setName("server");
            server.setConfigRef("server-config");

        } else {
            try {
                server = (Server) resourceexists(Server.class, Server.class.getMethod("getConfigRef"), domain.getServers().getServer(),"server-config");
                if (server == null) {
                    throw new BuildException("No server with configref 'server-config' found, abort generation. Start with an empty and unmodified domain.xml!");
                }
            } catch (NoSuchMethodException e){
                throw new BuildException(e);
            }

        }




        // now modify file
        System.out.println("add " + list_of_systemproperties.size() + " appserver system propert(y|ies)!");
        List<Property> l_o_P = config.getProperty();
        try {
            for (sunapp_property prop : list_of_systemproperties) {
                if (resourceexists(Property.class, Property.class.getMethod("getName"), l_o_P, prop.getName()) == null) {
                    l_o_P.add(prop.getProperty());
                } else {
                    System.out.println("property with name '"+prop.getName() + "' exitst ... ignore entry");
                }

            }
            
        }catch  (NoSuchMethodException e) {
            throw new BuildException(e);
        }

        System.out.println("add " + list_of_jdbcconnectionpool.size() + " appserver connection pools!");
        try {
            for (sunapp_jdbc_connection_pool sunapp_pool : list_of_jdbcconnectionpool) {
                sunapp_pool.execute();
                JdbcConnectionPool pool = sunapp_pool.getJdbcConnectionPool();
                if (resourceexists(
                        JdbcConnectionPool.class,
                        JdbcConnectionPool.class.getMethod("getName"),
                        list_of_resources,
                        pool.getName()) == null) {

                    list_of_resources.add(pool);
                    // add resource ref for pool
                    ResourceRef resource_ref = new ResourceRef();
                    resource_ref.setEnabled("true");
                    resource_ref.setRef(pool.getName());
                    server.getResourceRef().add(resource_ref);

                } else {
                    System.out.println("jdbc-connection-pool with name '" + pool.getName() + "' exists ... ignore entry");
                }
            }
        } catch (NoSuchMethodException e) {
            throw new BuildException(e);
        }


        System.out.println("add " + list_of_jdbcresources.size() + " appserver jdbc resources!");
        try {
            for (generated.JdbcResource jdbc_res : list_of_jdbcresources) {
                if (resourceexists(JdbcResource.class,
                        JdbcResource.class.getDeclaredMethod("getJndiName"),
                        list_of_resources,
                        jdbc_res.getJndiName()) == null) {
                    list_of_resources.add(jdbc_res);
                    // add resource ref for jdbc resource
                    ResourceRef resource_ref = new ResourceRef();
                    resource_ref.setEnabled("true");
                    resource_ref.setRef(jdbc_res.getJndiName());
                    server.getResourceRef().add(resource_ref);
                } else {
                    System.out.println("jdbc-resource with jndi-name '" + jdbc_res.getJndiName() + "' exists ... ignore entry");
                }
            }
        } catch (NoSuchMethodException e) {
            throw new BuildException(e);
        }



        // and write on disk
        try {
            marshallToFile(file, domain);
        } catch (JAXBException e) {
            System.err.println("An JAXBException occurred while marshal current domain to file!");
            throw new BuildException(e);
        }
    }

    /**
     * Marshall current domain object to file.
     *
     * @param file
     * @param domain
     * @throws JAXBException
     */
    private void marshallToFile(File file, Domain domain) throws JAXBException {

        JAXBContext jaxbc = JAXBContext.newInstance(generated.Domain.class);
        Marshaller m = jaxbc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(domain, file);
        System.out.println("Write " + file.toString() + " ...");
    }

    /**
     * Check if a given resource (given by c, specified by m) is already in the list l_o_O (has value)
     *
     * @param c
     * @param m
     * @param l_o_O
     * @param value
     * @return true, if resource exists 
     * @throws BuildException
     */
    private Object resourceexists(Class c, java.lang.reflect.Method m, List<?> l_o_O, String value) throws BuildException {
        for (Object o : l_o_O) {
            try {
                if (o.getClass().equals(c)) {
                    Object result = m.invoke(o, new Object[]{});
                    if (value != null && result != null && value.equals((String) result)) {
                        return o;
                    }
                }

            } catch (Exception e) {
                throw new BuildException(e);
            }
        }
        return null;
    }
    // JdbcResources subelement(s)
    List<JdbcResource> list_of_jdbcresources = new ArrayList<JdbcResource>();

    public sunapp_jdbc_resource createSunapp_jdbc_resource() {
        sunapp_jdbc_resource tmp = new sunapp_jdbc_resource();
        list_of_jdbcresources.add(tmp.getJdbcResource());
        return tmp;
    }
    // JdbcConnectionPool subelement(s)
    List<sunapp_jdbc_connection_pool> list_of_jdbcconnectionpool = new ArrayList<sunapp_jdbc_connection_pool>();

    public sunapp_jdbc_connection_pool createSunapp_jdbc_connection_pool() {

        sunapp_jdbc_connection_pool tmp = new sunapp_jdbc_connection_pool();
        list_of_jdbcconnectionpool.add(tmp);
        return tmp;
    }
    // Systemproperty subelement(s)
    List<sunapp_property> list_of_systemproperties = new ArrayList<sunapp_property>();

    public sunapp_property createSunapp_property() {
        sunapp_property tmp = new sunapp_property();
        list_of_systemproperties.add(tmp);
        return tmp;
    }
}
