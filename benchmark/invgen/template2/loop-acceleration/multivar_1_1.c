
int multivar_1_1(int x) {
  int y;
  y = x;
  while (x < 1024) {
     invariant: x = y;
    x = x + 1;
    y = y + 1;
  }
  return y;
 // __VERIFIER_assert(x == y);
}
