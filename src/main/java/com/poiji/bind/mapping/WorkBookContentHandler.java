package com.poiji.bind.mapping;

import com.poiji.option.PoijiOptions;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthew 2018/09/01
 */
final class WorkBookContentHandler implements ContentHandler {

    private WorkBookSheet individualSheet;
    private final List<WorkBookSheet> sheets = new ArrayList<>();
    private final PoijiOptions options;

    WorkBookContentHandler(final PoijiOptions options) {
        this.options = options;
    }

    protected List<WorkBookSheet> getSheets() {
        return sheets;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        // no-op
    }

    @Override
    public void startDocument() {
        // no-op
    }

    @Override
    public void endDocument() {
        // no-op
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) {
        // no-op
    }

    @Override
    public void endPrefixMapping(String prefix) {
        // no-op
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) {

        // there are multiple elements to an excel xml layout
        // we only care about the sheet infor
        if ("x:sheet".equals(qName) || "sheet".equals(qName)) {
            individualSheet = new WorkBookSheet();

            // loop throught all the attributes and add to the new sheet
            for (int i = 0; i < atts.getLength(); i++) {
                // examples
                // Attribute: name:Sheet3
                // Attribute: sheetId:3
                // Attribute: state:hidden
                if (atts.getQName(i).equals("state")) {
                    String state = atts.getValue(i);
                    if (!options.ignoreHiddenSheets()) {
                        state = "visible";
                    }
                    individualSheet.setState(state);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        // onces finished reading the element, if end of sheet, add to array of work
        // books sheets so can loop them later
        // set this sheet to null as its not needed any more
        if ("x:sheet".equals(qName) || "sheet".equals(qName)) {
            sheets.add(individualSheet);
            individualSheet = null;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) {
        // no-op
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) {
        // no-op
    }

    @Override
    public void processingInstruction(String target, String data) {
        // no-op
    }

    @Override
    public void skippedEntity(String name) {
        // no-op
    }

}
