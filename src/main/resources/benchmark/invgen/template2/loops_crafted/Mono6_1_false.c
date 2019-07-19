//false
int Mono6_1_false() {
    int x;
    int y;
    int z;
	x = 0;
	y = 10000000;
	z = 5000000;
	while (x<y) {
     invariant: (z - 1 >= 0) and (y - 3*z + 5000000 <= 0) and (y - z - 5000000 <= 0) and (x - z + 5000000 >= 0) and (x - y - z + 15000000 >= 0) and (x + y - z - 5000000 >= 0);
		if (x < 5000000) {
			x = x + 1;
			y = y;
			z = z;
		} else {
			x = x + 1;
			z = z + 1;
			y = y;
		}
	}
  //__VERIFIER_assert(z!=x);
  return z - x;
}
