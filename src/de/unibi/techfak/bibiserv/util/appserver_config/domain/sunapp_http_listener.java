package de.unibi.techfak.bibiserv.util.appserver_config.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Implemenation of http-listener element.
 *
 * @author Jan Krueger - jkrueger(at)cebitec.uni-bielefeld.de
 */
public class sunapp_http_listener {

    private String acceptor_threads;
    private String adress;
    private String blocking_enabled;
    private String default_virtual_server;
    private String enabled;
    private String external_port;
    private String family;
    private String id;
    private String port;
    private String redirect_port;
    private String security_enabled;
    private String server_name;
    private String xpowered_by;
    private List<sunapp_property> properties = new ArrayList<sunapp_property>();

    public String getAcceptor_threads() {
        return acceptor_threads;
    }

    public void setAcceptor_threads(String acceptor_threads) {
        this.acceptor_threads = acceptor_threads;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getBlocking_enabled() {
        return blocking_enabled;
    }

    public void setBlocking_enabled(String blocking_enabled) {
        this.blocking_enabled = blocking_enabled;
    }

    public String getDefault_virtual_server() {
        return default_virtual_server;
    }

    public void setDefault_virtual_server(String default_virtual_server) {
        this.default_virtual_server = default_virtual_server;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getExternal_port() {
        return external_port;
    }

    public void setExternal_port(String external_port) {
        this.external_port = external_port;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRedirect_port() {
        return redirect_port;
    }

    public void setRedirect_port(String redirect_port) {
        this.redirect_port = redirect_port;
    }

    public String getSecurity_enabled() {
        return security_enabled;
    }

    public void setSecurity_enabled(String security_enabled) {
        this.security_enabled = security_enabled;
    }

    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
    }

    public String getXpowered_by() {
        return xpowered_by;
    }

    public void setXpowered_by(String xpowered_by) {
        this.xpowered_by = xpowered_by;
    }

    /**
     * Each <b>http_listener</b> can have one or more property child elements.
     *
    <pre>
    &lt;http-listener&gt;
    &lt; property name="..." value="..." /&gt;
    ...
    &lt;/http-listener&gt;
    </pre>
     *
     * @return
     */
    public sunapp_property createSunapp_property() {
        sunapp_property temp = new sunapp_property();
        properties.add(temp);
        return temp;
    }

    public List<sunapp_property> getProperties() {
        return properties;
    }
}
