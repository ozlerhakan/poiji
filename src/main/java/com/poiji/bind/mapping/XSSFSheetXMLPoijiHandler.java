package com.poiji.bind.mapping;

import com.poiji.option.PoijiOptions;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.Comments;
import org.apache.poi.xssf.model.SharedStrings;
import org.apache.poi.xssf.model.Styles;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.Attributes2Impl;

/**
 * Created by hakan on 26.04.2020
 */
class XSSFSheetXMLPoijiHandler extends XSSFSheetXMLHandler {

    private final Styles stylesTable;
    private final PoijiOptions poijiOptions;
    private final PoijiLogCellFormat cellFormat;

    XSSFSheetXMLPoijiHandler(Styles styles,
                             Comments comments,
                             SharedStrings strings,
                             SheetContentsHandler sheetContentsHandler,
                             DataFormatter dataFormatter,
                             boolean formulasNotResults,
                             PoijiOptions poijiOptions) {
        super(styles, comments, strings, sheetContentsHandler, dataFormatter, formulasNotResults);
        this.stylesTable = styles;
        this.poijiOptions = poijiOptions;
        this.cellFormat = this.poijiOptions.getPoijiCellFormat();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        Attributes2Impl attributes2 = new Attributes2Impl(attributes);

        if (!"oddHeader".equals(localName) &&
                !"evenHeader".equals(localName) &&
                !"firstHeader".equals(localName) &&
                !"firstFooter".equals(localName) &&
                !"oddFooter".equals(localName) &&
                !"evenFooter".equals(localName)) {
            if ("c".equals(localName)) {
                String cellType = attributes.getValue("t");
                String cellStyleStr = attributes.getValue("s");
                if (!"b".equals(cellType) &&
                        !"e".equals(cellType) &&
                        !"inlineStr".equals(cellType) &&
                        !"s".equals(cellType) &&
                        !"str".equals(cellType) &&
                        cellStyleStr != null) {
                    int styleIndex = Integer.parseInt(cellStyleStr);

                    if (poijiOptions.isDisableXLSXNumberCellFormat() &&
                            attributes2.getLength() > styleIndex) {
                        attributes2.removeAttribute(styleIndex);
                    }
                }
            }
        }

        super.startElement(uri, localName, qName, attributes2);

        if (this.cellFormat == null) {
            return;
        }

        if ("c".equals(localName)) {
            // Set up defaults.
            String cellRef = attributes.getValue("r");
            String cellType = attributes.getValue("t");
            String cellStyleStr = attributes.getValue("s");
            CellAddress cellAddress = new CellAddress(cellRef);
            if ("s".equals(cellType) || "str".equals(cellType)) {
                this.cellFormat.addFormat(cellAddress, (short) 0, null, cellType, cellStyleStr);
                return;
            }
            // Number, but almost certainly with a special style or format
            XSSFCellStyle style = null;
            if (stylesTable != null) {
                if (cellStyleStr != null) {
                    int styleIndex = Integer.parseInt(cellStyleStr);
                    style = stylesTable.getStyleAt(styleIndex);
                }
            }
            if (style != null) {
                short formatIndex = style.getDataFormat();
                String formatString = style.getDataFormatString();

                this.cellFormat.addFormat(cellAddress, formatIndex, formatString, cellType, cellStyleStr);
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }
}
