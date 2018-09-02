package com.poiji.bind.mapping;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * Read the XML of an XLXS work book to find the sheets and their attributes.
 * Then work out if sheet is hidden or not.
 *
 * @author Matthew 2018/09/01
 */
public class WorkBookContentHandler implements ContentHandler {

    public List<WorkBookSheet> sheets = new ArrayList<>();
    private WorkBookSheet individualSheet;

    @Override
    public void setDocumentLocator(Locator locator) {
        //empty method
    }

    @Override
    public void startDocument() throws SAXException {
        //empty method
    }

    @Override
    public void endDocument() throws SAXException {
        //empty method
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        //empty method
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        //empty method
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

        //there are multipel elements to an excel xml layout
        //we only care about the sheet infor
        if (qName.equals("sheet")) {
            individualSheet = new WorkBookSheet();

            //loop throught all the attributes and add to the new sheet
            for (int i = 0; i < atts.getLength(); i++) {
                //examples
                //Attribute: name:Sheet3
                //Attribute: sheetId:3
                //Attribute: state:hidden
                if (atts.getQName(i).equals("name")) {
                    individualSheet.name = atts.getValue(i);
                }
                if (atts.getQName(i).equals("sheetId")) {
                    //note the sheet id is not the same as the sheet index
                    //so we dont actually use this but nice to have
                    individualSheet.sheetId = atts.getValue(i);
                }
                if (atts.getQName(i).equals("state")) {
                    //this is how we know if its hidden or not
                    //if null, not hidden, else will be set to "hidden"
                    //there is also a very hidden but im not sure how that is indeicated
                    individualSheet.state = atts.getValue(i);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        //onces finished reading the element, if end of sheet, add to array of work books sheets so can loop them later
        //set this sheet to null as its not needed any more
        if (qName.equals("sheet")) {
            sheets.add(individualSheet);
            individualSheet = null;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //empty method
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        //empty method
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        //empty method
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        //empty method
    }

}
