
package de.unibi.techfak.bibiserv.util.appserver_config.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *  Implementation of http-service element with glassfish-domain config. 
 *  The http-service element can have a couple of child elements (access-log, 
 *  http-listener, virtual-server, request-processing, keep-alive, connection-pool,
 *  http-protocol, http-file-cache, property). Currently on only two child supported
 *  http-listener and property. Missing elements must be implemented ... :-)
 *
 *
 * @author Jan Krueger - jkrueger(at)cebitec.uni-bielefeld.de
 */
public class sunapp_http_service {

     /**
     * Each <b>http_service</b> can have one or more http_listener child elements.
     *
      <pre>
      &lt;http-service&gt;
        &lt;http-listener&gt;
            &lt; property name="..." value="..." /&gt;
            ...
        &lt;/http-listener&gt;
        ...
      &lt;http-service&gt;
      </pre>
     *
     * @return
     */
    public sunapp_http_listener createSunapp_http_listener(){
        sunapp_http_listener temp = new sunapp_http_listener();
        http_listener.add(temp);
        return temp;
    }


    public List<sunapp_http_listener> getHttp_listener(){
        return http_listener;
    }

    private List<sunapp_http_listener> http_listener = new ArrayList<sunapp_http_listener>();

      /**
     * Each <b>http_service</b> can have one or more property child elements.
     *
      <pre>
        &lt;http-service&gt;
            &lt; property name="..." value="..." /&gt;
            ...
        &lt;/http-service&gt;
      </pre>
     *
     * @return
     */
    public sunapp_property createSunapp_property(){
        sunapp_property temp = new sunapp_property();
        properties.add(temp);
        return temp;
    }

    public List<sunapp_property> getProperties(){
        return properties;
    }

    private List<sunapp_property> properties = new ArrayList<sunapp_property>();

}
