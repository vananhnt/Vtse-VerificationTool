
int functions_1_1_false() {
  int x;
  x = 0;

  while (x < 10000) {
    x = x + 2;
  }
  return x;
  //__VERIFIER_assert(!(x % 2));
}
