int simple_2_1_true() {
  int x;

  while (x < 10000) {
    x = x + 1;
  }
  return x;
  //__VERIFIER_assert(x >= 10000);
}
