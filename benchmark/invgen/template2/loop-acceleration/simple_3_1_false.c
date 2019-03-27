
int simple_3_1_false() {
  int x = 0;
  int N = 0;

  while (x < N) {
    x = x + 2;
  }
  return x;
  //__VERIFIER_assert(x % 2); -> x%2 == 1
}
