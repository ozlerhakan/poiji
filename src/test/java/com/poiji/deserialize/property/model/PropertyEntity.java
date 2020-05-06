package com.poiji.deserialize.property.model;

import com.poiji.annotation.ExcelProperty;

import java.util.Date;

public class PropertyEntity {

    @ExcelProperty
    private String title;

    @ExcelProperty
    private Date modified;

    @ExcelProperty
    private String category;

    @ExcelProperty
    private String contentStatus;

    @ExcelProperty
    private Date created;

    @ExcelProperty
    private String creator;

    @ExcelProperty
    private String description;

    @ExcelProperty
    private String keywords;

    @ExcelProperty
    private Date lastPrinted;

    @ExcelProperty
    private String subject;

    @ExcelProperty
    private String revision;

    @ExcelProperty
    private String customProperty;

    public String getTitle() {
        return title;
    }

    public Date getModified() {
        return modified;
    }

    public String getCategory() {
        return category;
    }

    public String getContentStatus() {
        return contentStatus;
    }

    public Date getCreated() {
        return created;
    }

    public String getCreator() {
        return creator;
    }

    public String getDescription() {
        return description;
    }

    public String getKeywords() {
        return keywords;
    }

    public Date getLastPrinted() {
        return lastPrinted;
    }

    public String getSubject() {
        return subject;
    }

    public String getRevision() {
        return revision;
    }

    public String getCustomProperty() {
        return customProperty;
    }
}
