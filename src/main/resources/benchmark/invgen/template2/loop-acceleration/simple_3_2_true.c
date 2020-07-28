
int simple_3_2_true() {
  int x;
  int n;
  x = 0;
  n = 10000;

  while (x < n) {
     invariant: n - x > 0;
    x = x + 2;
    n = n;
  }
  return x;
 // __VERIFIER_assert(!(x % 2));
}
