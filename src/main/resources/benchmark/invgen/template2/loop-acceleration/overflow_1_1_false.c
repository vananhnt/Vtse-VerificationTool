
int overflow_1_1_false(int x) {

  while (x >= 10) {
     invariant: x - 10 >= 0;
    x = x + 2;
  }
  return x;
  //__VERIFIER_assert(!(x % 2));
}
