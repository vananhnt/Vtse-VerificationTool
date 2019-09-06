package com.vtse.cfg.utils;

import com.rits.cloning.Cloner;
import net.sourceforge.sizeof.*;
/**
 * @author va
 */

public class MyCloner {

//	@SuppressWarnings("deprecation")

	public static <T> T clone(T object) {

		Cloner cloner = new Cloner();

		T clone = cloner.deepClone(object);
		return clone;
	}

	public static long getSize(Object object) {
		SizeOf.skipStaticField(true); //java.sizeOf will not compute static fields
		SizeOf.skipFinalField(true); //java.sizeOf will not compute final fields
		SizeOf.skipFlyweightObject(true); //java.sizeOf will not compute well-known flyweight objects
		long res = SizeOf.deepSizeOf(object);
		//System.out.println(res); //this will print the object size in bytes
		return res;
	}
}
