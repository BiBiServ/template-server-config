package de.unibi.techfak.bibiserv.util.appserver_config.domain;

import generated.Isolation;
import generated.JdbcConnectionPool;
import generated.Property;

import java.util.List;
import java.util.ArrayList;
import org.apache.tools.ant.BuildException;

/**
 * public class sunapp_jdbc_connection_pool represents an ant task (sunapp_domain)
 * childelement. It defines Bean methods for all attributes supported by
 * the sunapp_jdbc_connection_pool.
 *
 * This class uses the JAXB representation of an jdbc-connection-pool element defined
 * by http://www.sun.com/software/appserver/dtds/sun-domain_1_3.dtd.
 *
 *
 * @author Jan Krueger (jkrueger(at)cebitec.uni-bielefeld.de)
 */
public class sunapp_jdbc_connection_pool {

    /** simple Constructor */
    public sunapp_jdbc_connection_pool() {
    }

    ;
    /** internal properties representation of subelements **/
    private List<Property> properties = new ArrayList<Property>();
    /** internal used JAXb representation of an jadbc connection pool */
    private JdbcConnectionPool jdbcconnectionpool = new JdbcConnectionPool();

    /**
     * Return a JAXB representation of the described jdbc connection pool object.
     *
     * @return
     */
    public JdbcConnectionPool getJdbcConnectionPool() {
        return jdbcconnectionpool;
    }

    /**
     * Execute method - more or less with same intention like the Task class.
     * Do some further check on the given Attributes. and collect all data into
     * a single JAXB object.
     *
     * @throws BuildException
     */
    public void execute() throws BuildException {

        // iterate over all property sub elements ...
        List<Property> list_of_properties = jdbcconnectionpool.getProperty();

        for (Property prop : properties) {
            list_of_properties.add(prop);
        }


        if (jdbcconnectionpool.getName() == null) {
            throw new BuildException("Attribute 'name' must be specified!");
        }

        if (jdbcconnectionpool.getDatasourceClassname() == null) {
            throw new BuildException("Attribute 'DatasourceClassname' must be specified!");
        }
        if ((jdbcconnectionpool.getResType() == null) || !(jdbcconnectionpool.getResType().equals("javax.sql.DataSource")
                || jdbcconnectionpool.getResType().equals("javax.sql.XADataSource")
                || jdbcconnectionpool.getResType().equals("javax.sql.ConnectionPoolDataSource"))) {
            throw new BuildException("Attribute 'res-type' of Element 'sunapp_jdbc_connection_pool' must be specified and one of (javax.sql.DataSource | javax.sql.XADataSource | javax.sql.ConnectionPoolDataSource) - was " + jdbcconnectionpool.getResType());
        }

        if (!(jdbcconnectionpool.getConnectionValidationMethod().equals("auto-commit")
                || jdbcconnectionpool.getConnectionValidationMethod().equals("meta-data")
                || jdbcconnectionpool.getConnectionValidationMethod().equals("table"))) {
            throw new BuildException("Attribute 'connectionValidationMethod' of Element 'sunapp_jdbc_connection_pool' must be  one of (auto-commit | meta-data | table) - was " + jdbcconnectionpool.getConnectionValidationMethod());
        }

    }

    /**
     * Each <b>jdbc-connection-pool</b> can have one or more property child elements.
     *
    <pre>
    &lt;jdb-connection-pool&gt;
    &lt;property key="..." value="..."/&gt;
    ...
    &lt;jdb-connection-pool&gt;
    </pre>
     *
     * @return
     */
    public sunapp_property createSunapp_property() {
        sunapp_property p = new sunapp_property();
        properties.add(p.getProperty());
        return p;
    }


    /* Getter/Setter methods for all attributes supported by jdbc_connection_pool entry */
    public boolean getAllownoncomponentcallers() {
        return Boolean.parseBoolean(jdbcconnectionpool.getAllowNonComponentCallers());
    }

    public void setAllownoncomponentcallers(boolean allownoncomponentcallers) {
        jdbcconnectionpool.setAllowNonComponentCallers(Boolean.toString(allownoncomponentcallers));
    }

    public String getAssociatewiththread() {
        return jdbcconnectionpool.getAssociateWithThread();
    }

    public void setAssociatewiththread(String associatewiththread) {
        jdbcconnectionpool.setAssociateWithThread(associatewiththread);
    }

    public String getConnectioncreationretryattempts() {
        return jdbcconnectionpool.getConnectionCreationRetryAttempts();
    }

    public void setConnectioncreationretryattempts(String connectioncreationretryattempts) {
        jdbcconnectionpool.setConnectionCreationRetryAttempts(connectioncreationretryattempts);
    }

    public String getConnectioncreationretryintervalinseconds() {
        return jdbcconnectionpool.getConnectionCreationRetryIntervalInSeconds();
    }

    public void setConnectioncreationretryintervalinseconds(String connectioncreationretryintervalinseconds) {
        jdbcconnectionpool.setConnectionCreationRetryIntervalInSeconds(connectioncreationretryintervalinseconds);
    }

    public String getConnectionleakreclaim() {
        return jdbcconnectionpool.getConnectionLeakReclaim();
    }

    public void setConnectionleakreclaim(String connectionleakreclaim) {
        jdbcconnectionpool.setConnectionLeakReclaim(connectionleakreclaim);
    }

    public String getConnectionleaktimeoutinseconds() {
        return jdbcconnectionpool.getConnectionLeakTimeoutInSeconds();
    }

    public void setConnectionleaktimeoutinseconds(String connectionleaktimeoutinseconds) {
        jdbcconnectionpool.setConnectionLeakTimeoutInSeconds(connectionleaktimeoutinseconds);
    }

    public String getConnectionvalidationmethod() {
        return jdbcconnectionpool.getConnectionValidationMethod();
    }

    public void setConnectionvalidationmethod(String connectionvalidationmethod) {
        jdbcconnectionpool.setConnectionValidationMethod(connectionvalidationmethod);
    }

    public String getDatasourceclassname() {
        return jdbcconnectionpool.getDatasourceClassname();
    }

    public void setDatasourceclassname(String datasourceclassname) {
        jdbcconnectionpool.setDatasourceClassname(datasourceclassname);
    }

    public String getFailallconnections() {
        return jdbcconnectionpool.getFailAllConnections();
    }

    public void setFailallconnections(String failallconnections) {
        jdbcconnectionpool.setFailAllConnections(failallconnections);
    }

    public String getIdletimeoutinseconds() {
        return jdbcconnectionpool.getIdleTimeoutInSeconds();
    }

    public void setIdletimeoutinseconds(String idletimeoutinseconds) {
        jdbcconnectionpool.setIdleTimeoutInSeconds(idletimeoutinseconds);
    }

    public String getIsconnectionvalidationrequired() {
        return jdbcconnectionpool.getIsConnectionValidationRequired();
    }

    public void setIsconnectionvalidationrequired(String isconnectionvalidationrequired) {
        jdbcconnectionpool.setIsConnectionValidationRequired(isconnectionvalidationrequired);
    }

    public String getIsisolationlevelguaranteed() {
        return jdbcconnectionpool.getIsIsolationLevelGuaranteed();
    }

    public void setIsisolationlevelguaranteed(String isisolationlevelguaranteed) {
        jdbcconnectionpool.setIsIsolationLevelGuaranteed(isisolationlevelguaranteed);
    }

    public String getLazyconnectionassociation() {
        return jdbcconnectionpool.getLazyConnectionAssociation();
    }

    public void setLazyconnectionassociation(String lazyconnectionassociation) {
        jdbcconnectionpool.setLazyConnectionAssociation(lazyconnectionassociation);
    }

    public String getLazyconnectionenlistment() {
        return jdbcconnectionpool.getLazyConnectionEnlistment();
    }

    public void setLazyconnectionenlistment(String lazyconnectionenlistment) {
        jdbcconnectionpool.setLazyConnectionEnlistment(lazyconnectionenlistment);
    }

    public String getMatchconnections() {
        return jdbcconnectionpool.getMatchConnections();
    }

    public void setMatchconnections(String matchconnection) {
        jdbcconnectionpool.setMatchConnections(matchconnection);
    }

    public String getMaxconnectionusagecount() {
        return jdbcconnectionpool.getMaxConnectionUsageCount();
    }

    public void setMaxconnectionusagecount(String maxconnectionusagecount) {
        jdbcconnectionpool.setMaxConnectionUsageCount(maxconnectionusagecount);
    }

    public String getMaxpoolsize() {
        return jdbcconnectionpool.getMaxPoolSize();
    }

    public void setMaxpoolsize(String maxpoolsize) {
        jdbcconnectionpool.setMaxPoolSize(maxpoolsize);
    }

    public String getMaxwaittimeinmillis() {
        return jdbcconnectionpool.getMaxWaitTimeInMillis();
    }

    public void setMaxwaittimeinmillis(String maxwaittimeinmillis) {
        jdbcconnectionpool.setMaxWaitTimeInMillis(maxwaittimeinmillis);
    }

    public String getName() {
        return jdbcconnectionpool.getName();
    }

    public void setName(String name) {
        jdbcconnectionpool.setName(name);
    }

    public String getNontransactionalconnections() {
        return jdbcconnectionpool.getNonTransactionalConnections();
    }

    public void setNontransactionalconnections(String nontransactionalconnections) {
        jdbcconnectionpool.setNonTransactionalConnections(nontransactionalconnections);
    }

    public String getPoolresizequantity() {
        return jdbcconnectionpool.getPoolResizeQuantity();
    }

    public void setPoolresizequantity(String poolresizequantity) {
        jdbcconnectionpool.setPoolResizeQuantity(poolresizequantity);
    }

    public String getRestype() {
        return jdbcconnectionpool.getResType();
    }

    public void setRestype(String restype) {
        jdbcconnectionpool.setResType(restype);
    }

    public String getStatementtimeoutinseconds() {
        return jdbcconnectionpool.getStatementTimeoutInSeconds();
    }

    public void setStatementtimeoutinseconds(String statementtimeoutinseconds) {
        jdbcconnectionpool.setStatementTimeoutInSeconds(statementtimeoutinseconds);
    }

    public String getSteadypoolsize() {
        return jdbcconnectionpool.getSteadyPoolSize();
    }

    public void setSteadypoolsize(String steadypoolsize) {
        jdbcconnectionpool.setSteadyPoolSize(steadypoolsize);
    }

    public String getTransactionisolationlevel() {
        return jdbcconnectionpool.getTransactionIsolationLevel().value();
    }

    public void setTransactionisolationlevel(String transactionisolationlevel) {
        jdbcconnectionpool.setTransactionIsolationLevel(Isolation.valueOf(transactionisolationlevel));
    }

    public String getValidateatmostonceperiodinseconds() {
        return jdbcconnectionpool.getValidateAtmostOncePeriodInSeconds();
    }

    public void setValidateatmostonceperiodinseconds(String validateatmostonceperiodinseconds) {
        jdbcconnectionpool.setValidateAtmostOncePeriodInSeconds(validateatmostonceperiodinseconds);
    }

    public String getValidationtablename() {
        return jdbcconnectionpool.getValidationTableName();
    }

    public void setValidationtablename(String validationtablename) {
        jdbcconnectionpool.setValidationTableName(validationtablename);
    }

    public String getWrapjdbcobjects() {
        return jdbcconnectionpool.getWrapJdbcObjects();
    }

    public void setWrapjdbcobjects(String wrapjdbcobjects) {
        jdbcconnectionpool.setWrapJdbcObjects(wrapjdbcobjects);
    }
}
