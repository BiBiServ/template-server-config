/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.unibi.techfak.bibiserv.util.appserver_config.domain;

/**
 * A simple representation of the java_config tag within 
 *
 * @author jkrueger
 */
public class sunapp_java_config {

    private String mode;

    private String classpath_prefix;
    private String classpath_suffix;
    private String debug_enabled;
    private String debug_options;
    private String env_classpath_ignored;
    private String java_home;
    private String javac_options;
    private String rmic_options;
    private String system_classpath;
    private String server_classpath;
    private String native_library_path_prefix;
    private String native_library_path_suffix;
    private String bytecode_preprocessors;

    public sunapp_java_config(){

    }

    public String getBytecode_preprocessors() {
        return bytecode_preprocessors;
    }

    public void setBytecode_preprocessors(String bytecode_preprocessors) {
        this.bytecode_preprocessors = bytecode_preprocessors;
    }

    public String getClasspath_prefix() {
        return classpath_prefix;
    }

    public void setClasspath_prefix(String classpath_prefix) {
        this.classpath_prefix = classpath_prefix;
    }

    public String getClasspath_suffix() {
        return classpath_suffix;
    }

    public void setClasspath_suffix(String classpath_suffix) {
        this.classpath_suffix = classpath_suffix;
    }

    public String getDebug_enabled() {
        return debug_enabled;
    }

    public void setDebug_enabled(String debug_enabled) {
        this.debug_enabled = debug_enabled;
    }

    public String getDebug_options() {
        return debug_options;
    }

    public void setDebug_options(String debug_options) {
        this.debug_options = debug_options;
    }

    public String getEnv_classpath_ignored() {
        return env_classpath_ignored;
    }

    public void setEnv_classpath_ignored(String env_classpath_ignored) {
        this.env_classpath_ignored = env_classpath_ignored;
    }

    public String getJava_home() {
        return java_home;
    }

    public void setJava_home(String java_home) {
        this.java_home = java_home;
    }

    public String getJavac_options() {
        return javac_options;
    }

    public void setJavac_options(String javac_options) {
        this.javac_options = javac_options;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNative_library_path_prefix() {
        return native_library_path_prefix;
    }

    public void setNative_library_path_prefix(String native_library_path_prefix) {
        this.native_library_path_prefix = native_library_path_prefix;
    }

    public String getNative_library_path_suffix() {
        return native_library_path_suffix;
    }

    public void setNative_library_path_suffix(String native_library_path_suffix) {
        this.native_library_path_suffix = native_library_path_suffix;
    }

    public String getRmic_options() {
        return rmic_options;
    }

    public void setRmic_options(String rmic_options) {
        this.rmic_options = rmic_options;
    }

    public String getServer_classpath() {
        return server_classpath;
    }

    public void setServer_classpath(String server_classpath) {
        this.server_classpath = server_classpath;
    }

    public String getSystem_classpath() {
        return system_classpath;
    }

    public void setSystem_classpath(String system_classpath) {
        this.system_classpath = system_classpath;
    }
    
}
