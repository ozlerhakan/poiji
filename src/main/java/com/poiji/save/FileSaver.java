package com.poiji.save;

import java.util.Collection;
import java.util.stream.Stream;

public interface FileSaver {

    <T> void save(Collection<T> data);

    <T> void save(Stream<T> data);
}
