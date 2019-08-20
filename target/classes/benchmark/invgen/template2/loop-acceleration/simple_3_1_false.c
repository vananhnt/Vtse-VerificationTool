
int simple_3_1_false() {
  int x;
  int n;
  x = 0;
  n = 5;

  while (x < n) {
     invariant: (n - x - 5 <= 0) and (n - x - 1 >= 0) and (n + x - 5 <= 0) and (n + x - 1 >= 0);
    x = x + 2;
  }
  return x;
  //__VERIFIER_assert(x % 2); -> x%2 == 1
}
