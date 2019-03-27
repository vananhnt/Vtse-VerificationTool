
int underapprox_2_1_false() {
  int x = 0;
  int y = 1;

  while (x < 6) {
    x = x + 1;
    y = 2*y;
  }
  return x;
//  __VERIFIER_assert(x != 6);
}
