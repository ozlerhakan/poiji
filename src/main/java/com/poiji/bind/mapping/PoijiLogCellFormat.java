package com.poiji.bind.mapping;

import org.apache.poi.ss.util.CellAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hakan on 26.04.2020
 */
public final class PoijiLogCellFormat {

    private final List<InternalCellFormat> formats = new ArrayList<>();

    public List<InternalCellFormat> formats() {
        return formats;
    }

    void addFormat(CellAddress cellAddress, short formatIndex, String formatString, String cellType, String cellStyleStr) {
        final InternalCellFormat poijiCellFormat = new InternalCellFormat();
        poijiCellFormat.setCellAddress(cellAddress);
        poijiCellFormat.setCellStypeStr(cellStyleStr);
        poijiCellFormat.setFormatIndex(formatIndex);
        poijiCellFormat.setFormatString(formatString);
        poijiCellFormat.setCellType(cellType);
        formats.add(poijiCellFormat);
    }

    public final static class InternalCellFormat {

        private String cellType;
        private short formatIndex;
        private String formatString;
        private String cellStypeStr;
        private CellAddress cellAddress;

        public CellAddress getCellAddress() {
            return cellAddress;
        }

        void setCellAddress(CellAddress cellAddress) {
            this.cellAddress = cellAddress;
        }

        public String getCellStypeStr() {
            return cellStypeStr;
        }

        void setCellStypeStr(String cellStypeStr) {
            this.cellStypeStr = cellStypeStr;
        }

        public short getFormatIndex() {
            return formatIndex;
        }

        void setFormatIndex(short formatIndex) {
            this.formatIndex = formatIndex;
        }

        public String getFormatString() {
            return formatString;
        }

        void setFormatString(String formatString) {
            this.formatString = formatString;
        }

        public String getCellType() {
            return cellType;
        }

        public void setCellType(String cellType) {
            this.cellType = cellType;
        }

        @Override
        public String toString() {
            return "InternalCellFormat{" +
                    "cellType='" + cellType + '\'' +
                    ", formatIndex=" + formatIndex +
                    ", formatString='" + formatString + '\'' +
                    ", cellStypeStr='" + cellStypeStr + '\'' +
                    ", cellAddress=" + cellAddress +
                    '}';
        }
    }
}
