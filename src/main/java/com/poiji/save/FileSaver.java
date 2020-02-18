package com.poiji.save;

import java.util.List;
import java.util.stream.Stream;

public interface FileSaver {

    <T> void save(List<T> data);

    <T> void save(Stream<T> data);
}
