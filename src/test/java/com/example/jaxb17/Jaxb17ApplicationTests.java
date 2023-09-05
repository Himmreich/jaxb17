package com.example.jaxb17;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.val;
import org.gleif.FileContentEnum;
import org.gleif.LEIData;
import org.gleif.ObjectFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.File;
import java.io.StringWriter;
import java.net.URISyntaxException;

@SpringBootTest
class Jaxb17ApplicationTests {

    @Autowired
    private Marshaller marshaller;

    @Autowired
    private Unmarshaller unmarshaller;

    @Test
    void marshallerTest() throws JAXBException {

        val expectedValue = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<LEIData xmlns=\"http://www.gleif.org/data/schema/leidata/2016\">\n" +
                "    <LEIHeader>\n" +
                "        <FileContent>QUERY_RESPONSE</FileContent>\n" +
                "    </LEIHeader>\n" +
                "</LEIData>\n";

        val leiData = new ObjectFactory().createLEIData();
        val leiHeader = new ObjectFactory().createLEIHeaderType();
        leiHeader.setFileContent(FileContentEnum.QUERY_RESPONSE);
        leiData.setLEIHeader(leiHeader);

        val stringWriter = new StringWriter();
        marshaller.marshal(leiData, stringWriter);
        Assertions.assertEquals(expectedValue, stringWriter.toString());
    }

    @Test
    void unmarshallTest() throws URISyntaxException, JAXBException {
        val file = new File(Jaxb17ApplicationTests.class.getClassLoader().getResource("984500B12AE0B7ABC107.xml").toURI());
        val result = (LEIData) unmarshaller.unmarshal(file);
        Assertions.assertEquals("QUERY_RESPONSE", result.getLEIHeader().getFileContent().value());
    }
}
