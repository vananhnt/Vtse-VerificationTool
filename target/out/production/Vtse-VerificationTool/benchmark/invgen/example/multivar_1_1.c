
int multivar_1_1() {
  int x;
  int y;
  x = 1;
  y = 1;
  while (x < 1024) {
     invariant: (y >= 0) and (y - 1 >= 0) and (x - y >= 0) and (2*x - y - 1 >= 0) and (x - y <= 0) and (x - 2*y + 1 <= 0);
     x = x + 1;
     y = y + 1;
  }
  return y - x;
 // __VERIFIER_assert(x == y);
}
