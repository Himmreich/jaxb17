package com.example.jaxb17.config;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.NonNull;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.net.MalformedURLException;
import java.net.URISyntaxException;


@Configuration
public class Config {

    @Bean
    public JAXBContext jaxbContext() throws JAXBException {
        return JAXBContext.newInstance(org.gleif.ObjectFactory.class.getPackage().getName());
    }

    @Bean
    public Marshaller marshaller(@NonNull JAXBContext jaxbContext) throws JAXBException {
        val marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    @Bean
    public Schema schema() throws URISyntaxException, MalformedURLException, SAXException {
        val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        return schemaFactory.newSchema(Config.class.getClassLoader().getResource("2021-03-04_lei-cdf-v3-1.xsd").toURI().toURL());
    }

    @Bean
    public Unmarshaller unmarshaller(@NonNull JAXBContext jaxbContext, @NonNull Schema schema) throws JAXBException {
        val unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setSchema(schema);
        return unmarshaller;
    }
}