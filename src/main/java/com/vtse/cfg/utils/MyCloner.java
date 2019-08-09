package com.vtse.cfg.utils;

import com.rits.cloning.Cloner;

/**
 * @author va
 */

public class MyCloner {

//	@SuppressWarnings("deprecation")

	public static <T> T clone(T object) {
//
//		Kryo kryo = new Kryo();
//		try {
//			kryo.setAsmEnabled(true);
//			// kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
//			((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
//			return kryo.copy(object);
//		}
//		finally {
//			kryo = null;
//		}
		Cloner cloner = new Cloner();
		T clone = cloner.deepClone(object);
		return clone;
	}
}
