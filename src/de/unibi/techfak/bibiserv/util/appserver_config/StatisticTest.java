/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010/2011 BiBiServ Curator Team, http://bibiserv.cebitec.uni-bielefeld.de,
 * All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common
 * Development and Distribution License("CDDL") (the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at http://www.sun.com/cddl/cddl.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.  When distributing the software, include
 * this License Header Notice in each file.  If applicable, add the following
 * below the License Header, with the fields enclosed by brackets [] replaced
 *  by your own identifying information:
 *
 * "Portions Copyrighted 2010/2011 BiBiServ Curator Team"
 *
 * Contributor(s): Jan Krueger
 *
 */
package de.unibi.techfak.bibiserv.util.appserver_config;

import de.unibi.cebitec.bibiserv.statistic.StatisticTestData;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tools.ant.BuildException;

/**
 * Ant task that inserts a bibiserv item file (as CLOB) in a table ITEM. The
 * table is defined as follows :
 * <pre>
 * create table item (
 *   id varchar(100),
 *   item clob,
 *   time timestamp,
 *   bar blob,
 *   md5 varchar(33),
 *   type varchar(20) default 'runnable' -- supported values are runnable, linked, default
 * );
 * </pre>
 *
 * @author Jan Krüger - jkrueger(at)cebitec.uni-bielefeld.de
 */
public class StatisticTest extends AbstractXXX2DataBase {

    @Override
    public void insertSQL(Connection con, File src_file) throws FileNotFoundException, SQLException {
        // not needed
    }

    @Override
    public void checkFile(File src_file) throws Exception {
        // not needed
    }

    @Override
    public void execute() throws BuildException {
        if (dbURL == null) {
            throw new BuildException("Attribute 'dbURL' is mandantory und must be valid jdbcURL!");
        }
        StatisticTestData std = new StatisticTestData();
        try {
            std.setConnection(createConnection());
            std.checkProperties();
            std.run();
        } catch (Exception e) {
            std.help();
            throw new BuildException(e);
        }
    }
}
