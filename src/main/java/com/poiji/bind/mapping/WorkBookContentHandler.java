package com.poiji.bind.mapping;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 *
 * @author Matthew 2018/09/01
 */
public class WorkBookContentHandler implements ContentHandler {

    public List<WorkBookSheet> sheets = new ArrayList<>();
    private WorkBookSheet individualSheet;

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

        if (qName.equals("sheet")) {
            individualSheet = new WorkBookSheet();

            for (int i = 0; i < atts.getLength(); i++) {

                //    Attribute: name:Sheet3
                //    Attribute: sheetId:3
                //    Attribute: state:hidden
                if (atts.getQName(i).equals("name")) {
                    individualSheet.name = atts.getValue(i);
                }
                if (atts.getQName(i).equals("sheetId")) {
                    individualSheet.sheetId = atts.getValue(i);
                }
                if (atts.getQName(i).equals("state")) {
                    individualSheet.state = atts.getValue(i);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("sheet")) {
            sheets.add(individualSheet);
            individualSheet = null;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
    }

}
