//false
int Mono6_1_false() {
    int x;
    int y;
    int z;
	x = 0;
	y = 10000000;
	z = 5000000;
	while (x < y) {
     invariant: (z >= 0) and (z - 1 >= 0) and (x - z + 5000000 >= 0);
		if (x < 5000000) {
		    z = z;
			x = x + 1;
		} else {
			z = z + 1;
			x = x + 1;
		}
	}
  //__VERIFIER_assert(z!=x);
  return z;
}
