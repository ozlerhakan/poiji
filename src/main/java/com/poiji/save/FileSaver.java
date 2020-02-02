package com.poiji.save;

import java.util.List;

public interface FileSaver {

    <T> void save(List<T> data);
}
