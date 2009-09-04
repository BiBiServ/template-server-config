/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.techfak.bibiserv.util.appserver_config.domain;

import generated.Isolation;
import generated.JdbcConnectionPool;

import java.util.List;
import java.util.Properties;
import org.apache.tools.ant.BuildException;

/**
 *
 * @author jkrueger
 */
public class sunapp_jdbc_connection_pool {




    private String name = null;  //REQUIRED

    private String datasource_classname = null;  //REQUIRED
    // res-type (javax.sql.DataSource | javax.sql.XADataSource | javax.sql.ConnectionPoolDataSource) #IMPLIED
    private String res_type = null;
    //steady-pool-size CDATA "8"
    private int steady_pool_size = 8;
    //max-pool-size CDATA "32"
    private int max_pool_size = 32;
    //max-wait-time-in-millis CDATA "60000"
    private int max_wait_time_in_millis = 60000;
    //pool-resize-quantity CDATA "2"
    private int pool_resize_quantity = 2;
    //idle-timeout-in-seconds CDATA "300"
    private int idle_timeout_in_seconds = 300;
    //transaction-isolation-level %isolation; #IMPLIED
    private String transaction_isolation_level = null;
    //is-isolation-level-guaranteed %boolean; "true"
    private boolean is_isolation_level_guaranteed = true;
    //is-connection-validation-required %boolean; "false"
    private boolean is_connection_validation_required = false;
    //connection-validation-method (auto-commit | meta-data | table) "auto-commit"
    private String connection_validation_method = "auto-commit";
    //validation-table-name CDATA #IMPLIED
    private String validation_table_name = null;
    //fail-all-connections %boolean; "false"
    private boolean fail_all_connections  = false;
    //non-transactional-connections %boolean; "false"
    private boolean non_transactional_connections  = false;
    //allow-non-component-callers %boolean; "false"
    private boolean allow_non_component_callers  = false;
    //validate-atmost-once-period-in-seconds CDATA "0"
    private int validate_atmost_once_period_in_seconds = 0;
    //connection-leak-timeout-in-seconds CDATA "0"
    private int connection_leak_timeout_in_seconds = 0;
    //connection-leak-reclaim %boolean; "false"
    private boolean connection_leak_reclaim = false;
    //connection-creation-retry-attempts CDATA "0"
    private int connection_creation_retry_attempts = 0;
    //connection-creation-retry-interval-in-seconds CDATA "10"
    private int connection_creation_retry_interval_in_seconds = 10;
    //statement-timeout-in-seconds CDATA "-1"
    private int statement_timeout_in_seconds = -1;
    //lazy-connection-enlistment %boolean; "false"
    private boolean lazy_connection_enlistment = false;
    //lazy-connection-association %boolean; "false"
    private boolean lazy_connection_association = false;
    //associate-with-thread %boolean; "false"
    private boolean associate_with_thread = false;
    //match-connections %boolean; "false"
    private boolean match_connection = false;
    //max-connection-usage-count CDATA "0"
    private int max_connection_usage_count = 0;
    //wrap-jdbc-objects %boolean; "false"
    private boolean wrap_jdbc_objects = false;

    /** simple Constructor */
    public sunapp_jdbc_connection_pool() {};



     /** internal properties representation of subelements **/
    private Properties properties = new Properties();

    private JdbcConnectionPool jdbcconnectionpool = new JdbcConnectionPool();

    public JdbcConnectionPool getJdbcConnectionPool() {
        return jdbcconnectionpool;
    }

    public void excecute() {

        // iterate over all property sub elements ...
        List<generated.Property> list_of_properties = jdbcconnectionpool.getProperty();
        for (Object key : properties.keySet()){
            generated.Property p = new generated.Property();
            p.setName((String)key);
            p.setValue(properties.getProperty((String)key));
            list_of_properties.add(p);
        }
        
        
        if (jdbcconnectionpool.getName() == null) {
            throw new BuildException("Attribute 'name' must be specified!");
        }
        
        if (jdbcconnectionpool.getDatasourceClassname() == null) {
            throw new BuildException("Attribute 'DatasourceClassname' must be specified!");
        }
        if ((jdbcconnectionpool.getResType() == null) || !(
                jdbcconnectionpool.getResType().equals("javax.sql.DataSource") || 
                jdbcconnectionpool.getResType().equals("javax.sql.XADataSource") || 
                jdbcconnectionpool.getResType().equals("javax.sql.ConnectionPoolDataSource"))) {
            throw new BuildException("Attribute 'res-type' must be specified and on of (javax.sql.DataSource | javax.sql.XADataSource | javax.sql.ConnectionPoolDataSource)");
        }

        if (!(jdbcconnectionpool.getConnectionValidationMethod().equals("auto-commit") ||
                jdbcconnectionpool.getConnectionValidationMethod().equals("meta-data") ||
                jdbcconnectionpool.getConnectionValidationMethod().equals("table"))) {
            throw new BuildException();
        }
    }


   


    /**
     * Each <b>jdbc-connection-pool</b> can one or more property child elements.
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
    public sunapp_property createProperty(){
        sunapp_property p = new sunapp_property();
        properties.put(p.getKey(), p.getValue());
        return p;
    }

    public String getAllow_non_component_callers() {
        return jdbcconnectionpool.getAllowNonComponentCallers();
    }

    public void setAllow_non_component_callers(String allow_non_component_callers) {
       jdbcconnectionpool.setAllowNonComponentCallers(allow_non_component_callers);
    }

    public String getAssociate_with_thread() {
        return jdbcconnectionpool.getAssociateWithThread();
    }

    public void setAssociate_with_thread(String associate_with_thread) {
       jdbcconnectionpool.setAssociateWithThread(associate_with_thread);
    }

    public String getConnection_creation_retry_attempts() {
        return jdbcconnectionpool.getConnectionCreationRetryAttempts();
    }

    public void setConnection_creation_retry_attempts(String connection_creation_retry_attempts) {
        jdbcconnectionpool.setConnectionCreationRetryAttempts(connection_creation_retry_attempts);
    }

    public String getConnection_creation_retry_interval_in_seconds() {
        return jdbcconnectionpool.getConnectionCreationRetryIntervalInSeconds();
    }

    public void setConnection_creation_retry_interval_in_seconds(String connection_creation_retry_interval_in_seconds) {
        jdbcconnectionpool.setConnectionCreationRetryIntervalInSeconds(connection_creation_retry_interval_in_seconds);
    }

    public String getConnection_leak_reclaim() {
        return jdbcconnectionpool.getConnectionLeakReclaim();
    }

    public void setConnection_leak_reclaim(String connection_leak_reclaim) {
        jdbcconnectionpool.setConnectionLeakReclaim(connection_leak_reclaim);
    }

    public String getConnection_leak_timeout_in_seconds() {
        return jdbcconnectionpool.getConnectionLeakTimeoutInSeconds();
    }

    public void setConnection_leak_timeout_in_seconds(String connection_leak_timeout_in_seconds) {
        jdbcconnectionpool.setConnectionLeakTimeoutInSeconds(connection_leak_timeout_in_seconds);
    }

    public String getConnection_validation_method() {
        return jdbcconnectionpool.getConnectionValidationMethod();
    }

    public void setConnection_validation_method(String connection_validation_method) {
       jdbcconnectionpool.setConnectionValidationMethod(connection_validation_method);
    }

    public String getDatasource_classname() {
        return jdbcconnectionpool.getDatasourceClassname();
    }

    public void setDatasource_classname(String datasource_classname) {
        jdbcconnectionpool.setDatasourceClassname(datasource_classname);
    }

    public String getFail_all_connections() {
        return jdbcconnectionpool.getFailAllConnections();
    }

    public void setFail_all_connections(String fail_all_connections) {
        jdbcconnectionpool.setFailAllConnections(fail_all_connections);
    }

    public String getIdle_timeout_in_seconds() {
        return jdbcconnectionpool.getIdleTimeoutInSeconds();
    }

    public void setIdle_timeout_in_seconds(String idle_timeout_in_seconds) {
        jdbcconnectionpool.setIdleTimeoutInSeconds(idle_timeout_in_seconds);
    }

    public String getIs_connection_validation_required() {
        return jdbcconnectionpool.getIsConnectionValidationRequired();
    }

    public void setIs_connection_validation_required(String is_connection_validation_required) {
       jdbcconnectionpool.setIsConnectionValidationRequired(is_connection_validation_required);
    }

    public String getIs_isolation_level_guaranteed() {
        return jdbcconnectionpool.getIsIsolationLevelGuaranteed();
    }

    public void setIs_isolation_level_guaranteed(String is_isolation_level_guaranteed) {
        jdbcconnectionpool.setIsIsolationLevelGuaranteed(is_isolation_level_guaranteed);
    }

    public String getLazy_connection_association() {
        return jdbcconnectionpool.getLazyConnectionAssociation();
    }

    public void setLazy_connection_association(String lazy_connection_association) {
       jdbcconnectionpool.setLazyConnectionAssociation(lazy_connection_association);
    }

    public String getLazy_connection_enlistment() {
        return jdbcconnectionpool.getLazyConnectionEnlistment();
    }

    public void setLazy_connection_enlistment(String lazy_connection_enlistment) {
        jdbcconnectionpool.setLazyConnectionEnlistment(lazy_connection_enlistment);
    }

    public String getMatch_connection() {
        return jdbcconnectionpool.getMatchConnections();
    }

    public void setMatch_connection(String match_connection) {
       jdbcconnectionpool.setMatchConnections(match_connection);
    }

    public String getMax_connection_usage_count() {
        return jdbcconnectionpool.getMaxConnectionUsageCount();
    }

    public void setMax_connection_usage_count(String max_connection_usage_count) {
       jdbcconnectionpool.setMaxConnectionUsageCount(max_connection_usage_count);
    }

    public String getMax_pool_size() {
        return jdbcconnectionpool.getMaxPoolSize() ;
    }

    public void setMax_pool_size(String max_pool_size) {
        jdbcconnectionpool.setMaxPoolSize(max_pool_size);
    }

    public String getMax_wait_time_in_millis() {
        return jdbcconnectionpool.getMaxWaitTimeInMillis();
    }

    public void setMax_wait_time_in_millis(String max_wait_time_in_millis) {
        jdbcconnectionpool.setMaxWaitTimeInMillis(max_wait_time_in_millis);
    }

    public String getName() {
        return jdbcconnectionpool.getName();
    }

    public void setName(String name) {
        jdbcconnectionpool.getName();
    }

    public String getNon_transactional_connections() {
        return jdbcconnectionpool.getNonTransactionalConnections();
    }

    public void setNon_transactional_connections(String non_transactional_connections) {
        jdbcconnectionpool.setNonTransactionalConnections(non_transactional_connections);
    }

    public String getPool_resize_quantity() {
        return jdbcconnectionpool.getPoolResizeQuantity();
    }

    public void setPool_resize_quantity(String pool_resize_quantity) {
        jdbcconnectionpool.setPoolResizeQuantity(pool_resize_quantity);
    }

    public String getRes_type() {
        return jdbcconnectionpool.getResType();
    }

    public void setRes_type(String res_type) {
        jdbcconnectionpool.setResType(res_type);
    }

    public String getStatement_timeout_in_seconds() {
        return jdbcconnectionpool.getStatementTimeoutInSeconds();
    }

    public void setStatement_timeout_in_seconds(String statement_timeout_in_seconds) {
        jdbcconnectionpool.setStatementTimeoutInSeconds(statement_timeout_in_seconds);
    }

    public String getSteady_pool_size() {
        return jdbcconnectionpool.getSteadyPoolSize();
    }

    public void setSteady_pool_size(int steady_pool_size) {
        this.steady_pool_size = steady_pool_size;
    }

    public String getTransaction_isolation_level() {
        return jdbcconnectionpool.getTransactionIsolationLevel().value();
    }

    public void setTransaction_isolation_level(String transaction_isolation_level) {
      jdbcconnectionpool.setTransactionIsolationLevel(Isolation.valueOf(transaction_isolation_level));
    }

    public String getValidate_atmost_once_period_in_seconds() {
        return jdbcconnectionpool.getValidateAtmostOncePeriodInSeconds();
    }

    public void setValidate_atmost_once_period_in_seconds(String validate_atmost_once_period_in_seconds) {
        jdbcconnectionpool.setValidateAtmostOncePeriodInSeconds(validate_atmost_once_period_in_seconds);
    }

    public String getValidation_table_name() {
        return jdbcconnectionpool.getValidationTableName();
    }

    public void setValidation_table_name(String validation_table_name) {
        jdbcconnectionpool.setValidationTableName(validation_table_name);
    }

    public String  getWrap_jdbc_objects() {
        return jdbcconnectionpool.getWrapJdbcObjects();
    }

    public void setWrap_jdbc_objects(String wrap_jdbc_objects) {
        jdbcconnectionpool.setWrapJdbcObjects(wrap_jdbc_objects);
    }



}
