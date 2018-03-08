package com.poiji.bind;

import java.util.List;

/**
 * Created by hakan on 08/03/2018
 */
public interface Unmarshaller {

    <T> List<T> unmarshal(Class<T> type);
}
