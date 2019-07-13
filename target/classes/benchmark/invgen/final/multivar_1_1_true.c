
int multivar_1_1_true(int x) {
  int y;
  y = x;
  while (x < 5) {
     invariant: (x - y <= 0) and (x - y >= 0);
    x = x + 1;
    y = y + 1;
  }
  return y;
 // __VERIFIER_assert(x == y);
}
