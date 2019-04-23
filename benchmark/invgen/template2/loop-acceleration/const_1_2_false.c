
int const_1_2_false() {
  int x; int y;
  x = 0; y = 0;

  while (y < 10) {
     invariant: (x + y >= 0) and (x - y >= 0) and (x - y <= 0) and (x + y <= 0);
    x = 0*x;
    y = y + 1;
  }
  return x;
 // __VERIFIER_assert(x == 1);
}
