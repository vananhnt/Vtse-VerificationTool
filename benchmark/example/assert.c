int flag = 0;
int add (int x, int y) {
	int n = 10;
	int tong;
	if (n > 0) {
		tong = x + y;
	} else {
		int z = x*y; // unreachable code
		flag = 1;  //
	}
	return tong;
}
ASSERT(flag = 0)






