//false
int Mono6_1() {
    int x;
    int y;
    int z;
	x = 0;
	y - 10000000 = 0;
	z - 5000000 = 0;
	while (x<y) {
	invariant:;
		if (x < 5000000) {
			x = x + 1;
		} else {
			z = z + 1;
			x = x + 1;
		}
	}
  //__VERIFIER_assert(z!=x);
  int res = z - x;
  return res;
}
