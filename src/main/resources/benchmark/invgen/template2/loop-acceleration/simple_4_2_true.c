
int simple_4_2_true() {
  int x = 10000;
  while (x > 0) {
     invariant: x > 0;
    x = x - 2;
  }
  return x;
 // __VERIFIER_assert(!(x % 2));
}
