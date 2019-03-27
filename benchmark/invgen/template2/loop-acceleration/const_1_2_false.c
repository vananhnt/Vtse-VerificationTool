
int const_1_false() {
  int x = 1;
  int y = 0;

  while (y < 1024) {
    x = 0;
    y = y + 1;
  }
  return x;
 // __VERIFIER_assert(x == 1);
}
