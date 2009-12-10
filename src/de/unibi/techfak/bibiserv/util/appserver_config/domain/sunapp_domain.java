package de.unibi.techfak.bibiserv.util.appserver_config.domain;

import generated.Config;
import generated.Configs;
import generated.Domain;
import generated.JavaConfig;
import generated.JdbcConnectionPool;
import generated.JdbcResource;
import generated.Property;
import generated.ResourceRef;
import generated.Resources;
import generated.Server;
import generated.Servers;
import generated.SystemProperty;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import sun.security.jgss.wrapper.SunNativeProvider;

/**
 * public class sunapp_domain represents an ant task (sunapp_domain)
 * element. The only attribute 'file' is required. If 'file' link to
 * an existing sun glassfish domain configuration, additional information
 * will be append , otherwise an new configuration will be created.
 *
 *
 *
 * @author Jan Krueger (jkrueger(at)cebitec.uni-bielefeld.de)
 */
public class sunapp_domain extends Task {

    private String fn = null;

    private sunapp_java_config sunapp_java_config = null;

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

            } catch (Exception e) {
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
        List<SystemProperty> l_o_P = config.getSystemProperty();
        try {
            for (sunapp_systemproperty prop : list_of_systemproperties) {
                if (resourceexists(Property.class, Property.class.getMethod("getName"), l_o_P, prop.getName()) == null) {
                    l_o_P.add(prop.getProperty());
                } else {
                    System.out.println("property with name '"+prop.getName() + "' exitst ... ignore entry");
                }

            }
            
        }catch  (NoSuchMethodException e) {
            throw new BuildException(e);
        }


        JavaConfig javaconfig = config.getJavaConfig();
        // check
        if (sunapp_java_config != null){
            // check mode
            String mode = sunapp_java_config.getMode();
            if ((mode == null) ||
                    ((!sunapp_java_config.getMode().equalsIgnoreCase("replace")) ||
                     (!sunapp_java_config.getMode().equalsIgnoreCase("append")))) {
                throw new BuildException("attribute mode must be not null. The only attribute values currently supported are \"replace\" and \"append\" !");
               
            }

            // classpathprefix
            if (sunapp_java_config.getClasspath_prefix() != null && !sunapp_java_config.getClasspath_prefix().equals("")) {
                 if (mode.equals("append") && javaconfig.getClasspathPrefix() != null && !javaconfig.getClasspathPrefix().equals("")) {
                    javaconfig.setClasspathPrefix(javaconfig.getClasspathPrefix()+":"+sunapp_java_config.getClasspath_prefix());
                } else {
                    javaconfig.setClasspathPrefix(sunapp_java_config.getClasspath_prefix());
                }
            }

            // classpathsuffix
            if (sunapp_java_config.getClasspath_suffix() != null && !sunapp_java_config.getClasspath_suffix().equals("")) {
                 if (mode.equals("append") && javaconfig.getClasspathSuffix() != null && !javaconfig.getClasspathSuffix().equals("")) {
                    javaconfig.setClasspathSuffix(javaconfig.getClasspathSuffix()+":"+sunapp_java_config.getClasspath_suffix());
                } else {
                    javaconfig.setClasspathSuffix(sunapp_java_config.getClasspath_suffix());
                }
            }

            // serverclasspath
            if (sunapp_java_config.getServer_classpath() != null && !sunapp_java_config.getServer_classpath().equals("")) {
                 if (mode.equals("append") && javaconfig.getServerClasspath() != null && !javaconfig.getServerClasspath().equals("")) {
                    javaconfig.setServerClasspath(javaconfig.getServerClasspath()+":"+sunapp_java_config.getServer_classpath());
                } else {
                    javaconfig.setServerClasspath(sunapp_java_config.getServer_classpath());
                }
            }
            // systemclasspath
            if (sunapp_java_config.getSystem_classpath() != null && !sunapp_java_config.getSystem_classpath().equals("")) {
                 if (mode.equals("append") && javaconfig.getSystemClasspath() != null && !javaconfig.getSystemClasspath().equals("")) {
                    javaconfig.setSystemClasspath(javaconfig.getSystemClasspath()+":"+sunapp_java_config.getSystem_classpath());
                } else {
                    javaconfig.setServerClasspath(sunapp_java_config.getSystem_classpath());
                }
            }

            // nativelibrarypathprefix
            if (sunapp_java_config.getNative_library_path_prefix() != null && !sunapp_java_config.getNative_library_path_prefix().equals("")) {
                 if (mode.equals("append") && javaconfig.getNativeLibraryPathPrefix() != null && !javaconfig.getNativeLibraryPathPrefix().equals("")) {
                    javaconfig.setNativeLibraryPathPrefix(javaconfig.getNativeLibraryPathPrefix()+":"+sunapp_java_config.getNative_library_path_prefix());
                } else {
                    javaconfig.setNativeLibraryPathPrefix(sunapp_java_config.getNative_library_path_prefix());
                }
            }


            // nativelibrarypathsuffix
            if (sunapp_java_config.getNative_library_path_suffix() != null && !sunapp_java_config.getNative_library_path_suffix().equals("")) {
                 if (mode.equals("append") && javaconfig.getNativeLibraryPathSuffix() != null && !javaconfig.getNativeLibraryPathSuffix().equals("")) {
                    javaconfig.setNativeLibraryPathSuffix(javaconfig.getNativeLibraryPathSuffix()+":"+sunapp_java_config.getNative_library_path_suffix());
                } else {
                    javaconfig.setNativeLibraryPathSuffix(sunapp_java_config.getNative_library_path_suffix());
                }
            }

            // debugoptions
            if (sunapp_java_config.getDebug_options() != null && !sunapp_java_config.getDebug_options().equals("")) {
                 if (mode.equals("append") && javaconfig.getDebugOptions() != null && !javaconfig.getDebugOptions().equals("")) {
                    javaconfig.setDebugOptions(javaconfig.getDebugOptions()+":"+sunapp_java_config.getDebug_options());
                } else {
                    javaconfig.setDebugOptions(sunapp_java_config.getDebug_options());
                }
            }

            // javac_options
            if (sunapp_java_config.getJavac_options() != null && !sunapp_java_config.getJavac_options().equals("")) {
                 if (mode.equals("append") && javaconfig.getJavacOptions() != null && !javaconfig.getJavacOptions().equals("")) {
                    javaconfig.setJavacOptions(javaconfig.getJavacOptions()+":"+sunapp_java_config.getJavac_options());
                } else {
                    javaconfig.setJavacOptions(sunapp_java_config.getJavac_options());
                }
            }


            /* for the attributes debug_enabled, java_home, bytecode_preprocesors the 'append'
             * mode make no sense, so we replace the attribute ...*/

            if (sunapp_java_config.getDebug_enabled() != null && !sunapp_java_config.getDebug_enabled().equals("")) {
                javaconfig.setDebugEnabled(sunapp_java_config.getDebug_enabled());
            }

            if (sunapp_java_config.getJava_home() != null && !sunapp_java_config.getJava_home().equals("")){
                javaconfig.setJavaHome(sunapp_java_config.getJava_home());
            }

            if (sunapp_java_config.getBytecode_preprocessors() != null && !sunapp_java_config.getBytecode_preprocessors().equals("")){
                javaconfig.setBytecodePreprocessors(sunapp_java_config.getBytecode_preprocessors());
            }

            // jvm options
            List <Object> jvmoptionlist = javaconfig.getJvmOptionsOrProperty();
            for (String jvmoption : sunapp_java_config.getJvmOptionsList()) {
                jvmoptionlist.add(0,jvmoption);
            }

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
        } catch (Exception e) {
            System.err.println("An Exception occurred while marshal current domain to file!");
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
    private void marshallToFile(File file, Domain domain) throws JAXBException, IOException {

        JAXBContext jaxbc = JAXBContext.newInstance(generated.Domain.class);
        Marshaller m = jaxbc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.setProperty(Marshaller.JAXB_FRAGMENT, true);
        FileOutputStream out = new FileOutputStream(file);
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes("UTF-8"));
        out.write("<!DOCTYPE domain PUBLIC \"-//Sun Microsystems Inc.//DTD Application Server 9.1 Domain//EN\" \"http://www.sun.com/software/appserver/dtds/sun-domain_1_3.dtd\">\n".getBytes("UTF-8"));

        m.marshal(domain, out);
        System.out.println("Write " + file.toString() + " ...");
    }

    /**
     * Check if a given resource (given by c, specified by m) is already in the list l_o_O (has value)
     *
     * @param c
     * @param m
     * @param l_o_O
     * @param value
     * @return the object itself or null
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
    private List<JdbcResource> list_of_jdbcresources = new ArrayList<JdbcResource>();


    /**
     * Each <b>domain</b> can have one or more jdbc_resource (child)child elements.
     *
      <pre>
      &lt;domain&gt;
        &lt;resources&gt;
            ...
            &lt;jdbc-resource .../&gt;
            ...
        &lt;/resource&gt;
      &lt;/domain&gt;
      </pre>
     *
     * This method collect all sunap_jdbc_resources in a list.
     *
     * @return Returns current sunapp_jdbc_resource.
     */
    public sunapp_jdbc_resource createSunapp_jdbc_resource() {
        sunapp_jdbc_resource tmp = new sunapp_jdbc_resource();
        list_of_jdbcresources.add(tmp.getJdbcResource());
        return tmp;
    }
    // JdbcConnectionPool subelement(s)
    private List<sunapp_jdbc_connection_pool> list_of_jdbcconnectionpool = new ArrayList<sunapp_jdbc_connection_pool>();

     /**
     * Each <b>domain</b> can have one or more jdbc_connection_pool (child)child elements.
     *
      <pre>
      &lt;domain&gt;
        &lt;resources&gt;
            ...
            &lt;jdbc-connection-pool ...&gt;
                ...
            &lt;/jdbc-connection-pool ...&gt;

            ...
        &lt;/resource&gt;
      &lt;/domain&gt;
      </pre>
     *
     * This method collect all sunap_jdbc_resources in a list.
     *
     * @return Returns current sunapp_jdbc_connection_pool.
     */
    public sunapp_jdbc_connection_pool createSunapp_jdbc_connection_pool() {

        sunapp_jdbc_connection_pool tmp = new sunapp_jdbc_connection_pool();
        list_of_jdbcconnectionpool.add(tmp);
        return tmp;
    }
    // Systemproperty subelement(s)
    private List<sunapp_systemproperty> list_of_systemproperties = new ArrayList<sunapp_systemproperty>();

    /**
     * Each <b>domain</b> can have one or more system property child elements.
     *
      <pre>
      &lt;domain&gt;
        ...
        &lt;systemproperty key="..." value="..."/&gt;
        ...
      &lt;domain&gt;
      </pre>
     *
     * @return
     */
    public sunapp_systemproperty createSunapp_systemproperty() {
        sunapp_systemproperty tmp = new sunapp_systemproperty();
        list_of_systemproperties.add(tmp);
        return tmp;
    }

    /**
     * Each <b>domain</b> cann have one java-config child element
     *
     *
      <pre>
      &lt;domain&gt;
        ...
        &lt;java-config classpath_prefix="..." classpath_suffix="..." ..&gt;
            &lt;jvm-options&gt;.....&lt;/jvm-options&gt;
            ...
        &lt;/java-config&gt;
        ...
      &lt;domain&gt;
      </pre>
     */

    public sunapp_java_config createSunapp_java_config() {
        sunapp_java_config = new sunapp_java_config();
        return sunapp_java_config;
    }
}
