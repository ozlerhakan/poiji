package com.poiji.deserialize.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellsJoinedByName;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Event {

    @ExcelCell(0)
    private String name;

    @ExcelCell(1)
    private LocalDate date;

    @ExcelCell(2)
    private LocalTime time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Event{" +
               "name='" + name + '\'' +
               ", date=" + date +
               ", time=" + time +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(name, event.name) && Objects.equals(date, event.date) && Objects.equals(time, event.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, time);
    }
}
