/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010-2012 BiBiServ Curator Team, http://bibiserv.cebitec.uni-bielefeld.de,
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
 * "Portions Copyrighted 2010-2012 BiBiServ Curator Team"
 *
 * Contributor(s): Jan Krueger
 *
 */
package de.unibi.techfak.bibiserv.util.appserver_config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.xml.XMLConstants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;

/**
 * Ant task that inserts a bibiserv archive file (bar file) in a table items.
 * The table is defined as follows :
 * 
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
 * @author Jan Krueger - jkrueger(at)cebitec.uni-bielefeld.de
 */
public class App2DataBase extends AbstractXXX2DataBase {

    private PreparedStatement stmt = null;
    private String id = "unknown";
    private byte []  runnableitem;
    public static final String RUNNABLE_FILENAME = "config/runnableitem.xml";
    public static final String RUNNABLE_SCHEMA = "http://bibiserv.cebitec.uni-bielefeld.de/xsd/bibiserv2/BiBiServAbstraction.xsd";

    @Override
    public void insertSQL(Connection con, File src_file) throws Exception {
        createConnection();
        stmt = con.prepareStatement("INSERT INTO ITEM (ID, TIME, ITEM, TYPE, BAR, MD5) VALUES (?,CURRENT_TIMESTAMP, ? ,?, ?, ?)");
        stmt.setString(1, id);
        stmt.setClob(2, new StringReader(new String(runnableitem)));
        stmt.setString(3, "runnable");
        stmt.setBlob(4, new FileInputStream(src_file));
        stmt.setString(5, getMD5Checksum(src_file));
        stmt.execute();
        stmt.close();

    }

    @Override
    public void checkFile(File car_file) throws Exception {
        //extract info.xml from car file
        runnableitem = extractNamedEntryfromZippedBuffer(new FileInputStream(car_file), RUNNABLE_FILENAME);


        // Parse an XML document into a DOM tree.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);


        DocumentBuilder parser = dbf.newDocumentBuilder();

        Document document = parser.parse(new ByteArrayInputStream(runnableitem));

        // Create a SchemaFactory capable of understanding WXS schemas.
        SchemaFactory factory =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // Load a WXS schema, represented by a Schema instance.
        Source schemaFile = new StreamSource(new URL(RUNNABLE_SCHEMA).openStream());
        Schema schema = factory.newSchema(schemaFile);

        // Create a Validator object, which can be used to validate
        // an instance document.
        Validator validator = schema.newValidator();

        // Validate the DOM tree.
        validator.validate(new DOMSource(document));

        // source is schema valid, but at this point we only support category files
        if (document.getDocumentElement().getLocalName().equals("runnableItem")) {
            id = document.getDocumentElement().getAttribute("id");
        } else {
            throw new Exception("File is valid BiBiServAbstraction file, but not an runnableItem");
        }

    }

    
}
