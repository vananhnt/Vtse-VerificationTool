int assert = 0;

int add (int x, int y) {
	int n = 10;
	int tong;
	if (n > 0) {
		tong = x + y;
	} else {
		int z = x*y; // unreachable code
		assert = 1;  //
	}
	return tong;
}
