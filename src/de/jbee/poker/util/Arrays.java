package de.jbee.poker.util;

public class Arrays {

	public static <T> T[] rotate1Left(T[] array) {
		if (array == null || array.length <= 1)
			return array;
		T first = array[0];
		int i = 0;
		for (; i < array.length - 1; i++) {
			array[i] = array[i + 1];
		}
		array[i] = first;
		return array;
	}
}
