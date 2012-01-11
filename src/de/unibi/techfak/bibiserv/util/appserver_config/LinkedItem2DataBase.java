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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.xml.XMLConstants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Ant task that inserts a bibiserv linkeditem file (as CLOB) in a table ITEM.
 * The table is defined as follows :
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
public class LinkedItem2DataBase extends AbstractXXX2DataBase {

    private PreparedStatement stmt = null;
    private String id = "unknown";

    @Override
    public void insertSQL(Connection con, File src_file) throws FileNotFoundException, SQLException {
        createConnection();
        stmt = con.prepareStatement("INSERT INTO ITEM (ID,TIME, ITEM,TYPE) VALUES (?,CURRENT_TIMESTAMP, ? ,?)");
        stmt.setString(1, id);
        stmt.setClob(2, new BufferedReader(new FileReader(src_file)));
        stmt.setString(3, "linked");
        stmt.execute();
        stmt.close();
    }

    @Override
    public void checkFile(File src_file) throws Exception {


        // Parse an XML document into a DOM tree.
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            
            
            DocumentBuilder parser = dbf.newDocumentBuilder();
            
        Document document = parser.parse(src_file);

        // Create a SchemaFactory capable of understanding WXS schemas.
        SchemaFactory factory =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // Load a WXS schema, represented by a Schema instance.
        Source schemaFile = new StreamSource(new URL("http://bibiserv.cebitec.uni-bielefeld.de/xsd/bibiserv2/BiBiServAbstraction.xsd").openStream());
        Schema schema = factory.newSchema(schemaFile);

        // Create a Validator object, which can be used to validate
        // an instance document.
        Validator validator = schema.newValidator();

        // Validate the DOM tree.
        validator.validate(new DOMSource(document));

        // source is schema valid, but at this point we only support category files
        if (document.getDocumentElement().getLocalName().equals("linkeditem")) {
            id = document.getDocumentElement().getAttribute("id");
        } else {
            throw new Exception("File is valid BiBiServAbstraction file, but not a linked item");
        }


    }
}
