int const_1_1_true() {
  int x;
  int y;
  x = 1;
  y = 0;

  while (y < 1024) {
     invariant: (x - y - 1 = 0) and (x + y - 1 = 0);
    x = 0;
    y = y + 1;
  }
  return x;
//  __VERIFIER_assert(x == 0);
}
