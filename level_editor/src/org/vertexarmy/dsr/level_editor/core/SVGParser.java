package org.vertexarmy.dsr.level_editor.core;

import java.io.File;
import java.io.IOException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import lombok.RequiredArgsConstructor;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.xpath.NodeSet;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * created by Alex
 * on 3/7/2015.
 */
public class SVGParser {
    private static final SAXSVGDocumentFactory DOCUMENT_FACTORY = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
    private static final XPath XPATH = XPathFactory.newInstance().newXPath();

    private final Document document;

    public static SVGParser fromFilename(String filename) throws IOException {
        Document doc = DOCUMENT_FACTORY.createDocument(new File(filename).toURI().toURL().toString());
        return new SVGParser(doc);
    }

    private SVGParser(Document doc) {
        document = doc;
    }

    public NodeList xpath(String xpath) {
        try {
            return new NodeList((org.w3c.dom.NodeList) XPATH.evaluate(xpath, document.getDocumentElement(), XPathConstants.NODESET));
        } catch (XPathExpressionException ignored) {
            return new NodeList();
        }
    }

    @RequiredArgsConstructor
    public static class NodeList implements org.w3c.dom.NodeList {
        private final org.w3c.dom.NodeList originalList;

        public NodeList() {
            this(new NodeSet());
        }

        @Override
        public org.w3c.dom.Node item(int index) {
            return originalList.item(index);
        }

        @Override
        public int getLength() {
            return originalList.getLength();
        }

        public Node first() {
            return item(0);
        }
    }
}
