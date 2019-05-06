package com.poiji.bind;

import java.util.function.Consumer;

/**
 * Created by hakan on 08/03/2018
 */
public interface Unmarshaller {

    <T> void unmarshal(Class<T> type, Consumer<? super T> consumer);
}
