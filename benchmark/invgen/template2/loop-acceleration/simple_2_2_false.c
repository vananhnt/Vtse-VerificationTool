
int simple_2_2_false() {
  int x;

  while (x < 10000) {
    x = x + 1;
  }
  return x;
 // __VERIFIER_assert(x > 0x0fffffff);
}
