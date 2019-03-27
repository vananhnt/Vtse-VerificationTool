
int overflow_1_1_false() {
  int x = 10;

  while (x >= 10) {
    x = x + 2;
  }
  return x;
  //__VERIFIER_assert(!(x % 2));
}
