package app.utils;

public class Helper {

	public static void reverse(String[] arr) {
		int length = arr.length;
		int halfLength = length / 2;
		String temp;
		for (int i = 0; i < halfLength; i++) {
			temp = arr[i];
			arr[i] = arr[length - i -1];
			arr[length - i - 1] = temp;
		}
	}
}
