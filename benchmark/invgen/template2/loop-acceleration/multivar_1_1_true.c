
int multivar_1_1_true(int x) {
  int y;
  y = x;
  while (x < 1024) {
     invariant: (x - y <= 0) and (x - y >= 0);
    x = x + 1;
    y = y + 1;
  }
  return x - y;
 // __VERIFIER_assert(x == y);
}
