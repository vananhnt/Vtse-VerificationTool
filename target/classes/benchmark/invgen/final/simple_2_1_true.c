int simple_2_1_true() {
  int x;
  x = 0;

  while (x < 10000) {
     invariant: -x <= 0;
    x = x + 1;
  }
  return x;
  //__VERIFIER_assert(x >= 10000);
}
