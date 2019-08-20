
int multivar_1_2_false() {
  int x; int y;
  x = 10; y = x + 1;

  while (x < 100) {
     invariant: (y - 1 >= 0) and (x - y - 1 = 0) and (9*x - 11*y + 9 <= 0);
    x = x + 1;
    y = y + 1;
  }
  return x - y;
  //__VERIFIER_assert(x == y);
}
