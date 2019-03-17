//false
int Mono5_1() {
	int x;
	int y;
	int z;
	x = 0;
	y - 10000000 = 0;
	z - 5000000 = 0;
	while (x<y) {
	invariant: z - 5000000 <= 0 and x + z - 5000000 <= 0 and x + z - 1 >= 0;
		if (x < 5000000) {
			x = x + 1;
		} else {
			z = z - 1;
			x = x + 1;
		}
	}
//  __VERIFIER_assert(z!=0);
	return z;
}
