
int simple_2_2_false() {
  int x;
  x = 0;
  while (x < 10000) {
     invariant: x >= 0;
    x = x + 1;
  }
  return x;
 // __VERIFIER_assert(x > 0x0fffffff);
}
