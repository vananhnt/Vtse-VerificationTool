
int multivar_1_1() {
  int x;
  int y;
  x = 1;
  y = 1;
  while (x < 1024) {
     invariant: x = y;
     x = x + 1;
     y = y + 1;
  }
  return y - x;
 // __VERIFIER_assert(x == y);
}
