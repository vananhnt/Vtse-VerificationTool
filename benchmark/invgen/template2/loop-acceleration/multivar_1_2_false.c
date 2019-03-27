
int multivar_1_2_false(int x) {
  int y = x + 1;

  while (x < 1024) {
    x = x + 1;
    y = y + 1;
  }
  return x - y;
  //__VERIFIER_assert(x == y);
}
