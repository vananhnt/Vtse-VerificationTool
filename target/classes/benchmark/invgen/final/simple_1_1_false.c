
int simple_1_1_false() {
  int x;
  x = 0;
  while (x < 10001) {
     invariant: -x <= 0;
    x = x + 2;
  }
  return x;
  //__VERIFIER_assert(x % 2);
}
