
int simple_3_2_true(int n) {
  int x = 0;

  while (x < n) {
     invariant: n - x > 0;
    x = x + 2;
  }
  return x;
 // __VERIFIER_assert(!(x % 2));
}
