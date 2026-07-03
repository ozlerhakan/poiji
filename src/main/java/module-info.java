module com.poiji {
    requires java.xml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.commons.collections4;

    exports com.poiji.annotation;
    exports com.poiji.bind;
    exports com.poiji.config;
    exports com.poiji.exception;
    exports com.poiji.option;
    exports com.poiji.parser;
    exports com.poiji.util;
}