package com.poiji.deserialize.model.byid;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelCellRange;

public class PersonCreditInfo {

    @ExcelCellName("No.")
    private Integer no;

    @ExcelCellRange
    private PersonInfo personInfo;

    @ExcelCellRange
    private CardInfo cardInfo;

    public Integer getNo() {
        return no;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public static class PersonInfo {
        @ExcelCellName("Name")
        private String name;
        @ExcelCellName("Age")
        private Integer age;
        @ExcelCellName("City")
        private String city;
        @ExcelCellName("State")
        private String state;
        @ExcelCellName("Zip Code")
        private String zipCode;

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getZipCode() {
            return zipCode;
        }
    }

    public static class CardInfo {
        @ExcelCellName("Card Type")
        private String type;
        @ExcelCellName("Last 4 Digits")
        private String last4Digits;
        @ExcelCellName("Expiration Date")
        private String expirationDate;

        public String getType() {
            return type;
        }

        public String getLast4Digits() {
            return last4Digits;
        }

        public String getExpirationDate() {
            return expirationDate;
        }
    }
}
