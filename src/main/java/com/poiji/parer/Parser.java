package com.poiji.parer;

public interface Parser<T> {

    T parse(String value) throws NumberFormatException;

}
