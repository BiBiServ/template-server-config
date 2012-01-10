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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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
 * Ant task that inserts a module archive file (car file) in a table modules.
 * The table is defined as follows :
 * 
 * <pre>
 * create table modules (
 *   id varchar(100) primary key not null,
 *   info clob,
 *   file blob,
 *   active smallint default 0
 * );
 * </pre>
 *
 * @author Jan Krueger - jkrueger(at)cebitec.uni-bielefeld.de
 */
public class Module2DataBase extends AbstractXXX2DataBase {

    private PreparedStatement stmt = null;
    private String id = "unknown";
    private byte [] info ;
    public static final String INFO_FILENAME = "web/WEB-INF/info.xml";
    public static final String INFO_SCHEMA = "http://bibiserv.cebitec.uni-bielefeld.de/xsd/bibiserv2/admin-module-info.xsd";

    @Override
    public void insertSQL(Connection con, File src_file) throws FileNotFoundException, SQLException {
        createConnection();
        stmt = con.prepareStatement("INSERT INTO MODULES (ID,INFO, FILE, ACTIVE) VALUES (?, ? , ? , ?)");
        stmt.setString(1, id);
        stmt.setClob(2, new StringReader(new String(info)));
        stmt.setBlob(3, new FileInputStream(src_file));
        stmt.setShort(4, (short) 1);
        stmt.execute();
        stmt.close();
    }

    @Override
    public void checkFile(File car_file) throws Exception {
        //extract info.xml from car file
        info = extractNamedEntryfromZippedBuffer(new FileInputStream(car_file), INFO_FILENAME);


        // Parse an XML document into a DOM tree.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);


        DocumentBuilder parser = dbf.newDocumentBuilder();

        Document document = parser.parse(new ByteArrayInputStream(info));

        // Create a SchemaFactory capable of understanding WXS schemas.
        SchemaFactory factory =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // Load a WXS schema, represented by a Schema instance.
        Source schemaFile = new StreamSource(new URL(INFO_SCHEMA).openStream());
        Schema schema = factory.newSchema(schemaFile);

        // Create a Validator object, which can be used to validate
        // an instance document.
        Validator validator = schema.newValidator();

        // Validate the DOM tree.
        validator.validate(new DOMSource(document));

        // get informations from info document
        id = document.getElementsByTagName("id").item(0).getChildNodes().item(0).getNodeValue();   

    }

  
}
