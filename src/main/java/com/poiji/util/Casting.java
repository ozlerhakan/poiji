package com.poiji.util;

import com.poiji.option.PoijiOptions;

public interface Casting {
	Object castValue(Class<?> fieldType, String value, PoijiOptions options);
}
