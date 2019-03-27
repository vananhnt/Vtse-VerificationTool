
int multivar_1_1_true(int x) {
  int y = x;

  while (x < 1024) {
    x = x + 1;
    y = y + 1;
  }
  return x - y;
 // __VERIFIER_assert(x == y);
}
