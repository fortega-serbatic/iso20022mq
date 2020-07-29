package com.abanca.swift.iso20022mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.Resource;
import javax.xml.transform.stream.StreamSource;
import java.nio.file.Files;
import java.util.Date;

@SpringBootApplication
@EnableJms
public class Iso20022mqApplication {

    static final String qName = "DEV.QUEUE.1"; // A queue from the default MQ Developer container config

    static String xmlSWIFN001 = "D:\\Dv\\Repo\\Abanca\\iso20022mq\\src\\main\\resources\\SWIFN101_Sample.xml";

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Iso20022mqApplication.class, args);

        // Create the JMS Template object to control connections and sessions.
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        // Send a single message with a timestamp
        String msg = "Hello from IBM MQ at " + new Date();

        // The default SimpleMessageConverter class will be called and turn a String
        // into a JMS TextMessage
       //jmsTemplate.convertAndSend(qName, msg);

        status();
    }



    static void status() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("MQ JMS Sample started. Message sent to queue: " + Iso20022mqApplication.qName);
        System.out.println("========================================");
    }
}
