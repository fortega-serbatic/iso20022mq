/*
 * Copyright © 2017, 2020 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.abanca.swift.iso20022mq;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class Listener {
    static boolean warned = false;

    String xmlSWIFN001 = "D:\\Dv\\Repo\\Abanca\\iso20022mq\\src\\main\\resources\\SWIFN101_Sample.xml";

    String xslSWIFN001 = "D:\\Dv\\Repo\\Abanca\\iso20022mq\\src\\main\\resources\\SWIFN101_to_Pain.001.xsl";

    String xsdSWIFN001 = "D:\\Dv\\Repo\\Abanca\\iso20022mq\\src\\main\\resources\\OMFD04SCHE03_WS480651.xsd";

    @JmsListener(destination = Iso20022mqApplication.qName)
    public void receiveMessage(String msg) {
        infinityWarning();

        System.out.println();
        System.out.println("========================================");
        System.out.println("Received message is: " + msg);
        System.out.println("========================================");

        StreamSource source = new StreamSource(new StringReader(msg));
        StreamSource stylesource = new StreamSource(xslSWIFN001);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        String xmlResult = "";

        try {
            transformer = factory.newTransformer(stylesource);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            xmlResult = writer.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
            xmlResult = e.getCause().toString();
        }

        try {

            SchemaFactory sFactory = SchemaFactory
                    .newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema;
            schema = sFactory.newSchema(new StreamSource(xsdSWIFN001));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlResult));

        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
            xmlResult = e.getCause().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("========================================");
        System.out.println("Process message is: " + xmlResult);
        System.out.println("========================================");

    }

    void infinityWarning() {
        if (!warned) {
            warned = true;
            System.out.println();
            System.out.println("========================================");
            System.out.println("MQ JMS Listener started for queue: " + Iso20022mqApplication.qName);
            System.out.println("NOTE: This program does not automatically end - it continues to wait");
            System.out.println("      for more messages, so you may need to hit BREAK to end it.");
            System.out.println("========================================");
        }
    }
}
