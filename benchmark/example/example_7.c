int flag = 0;
int add (int x, int y) {
	int n = 10;
	int sum;
	if (n > 0) {
		sum = x + y;
	} else {
		int z = x*y; // unreachable code
		flag = 1;  //
	}
	return flag;
}
// ASSERT(flag = 0)






