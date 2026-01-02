package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelCellsJoinedByName;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

/**
 * Model to test processEmptyCell feature with both ExcelCell and ExcelCellsJoinedByName.
 * This model has fields that may have empty cells to verify proper handling.
 */
public class PersonWithGaps {

    @ExcelCell(0)
    private String id;

    @ExcelCell(1)
    private String firstName;

    @ExcelCell(2)
    private String middleName;

    @ExcelCell(3)
    private String lastName;

    @ExcelCellName("Email")
    private String email;

    @ExcelCellsJoinedByName(expression = "Phone.*")
    private MultiValuedMap<String, String> phones = new ArrayListValuedHashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MultiValuedMap<String, String> getPhones() {
        return phones;
    }

    public void setPhones(MultiValuedMap<String, String> phones) {
        this.phones = phones;
    }
}
