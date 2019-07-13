
int phases_1_1_true() {
  int x;
  x = 0;

  while (x < 1000) {
     invariant: (x >= 0) and (x - 502 <= 0);
    if (x < 500) {
      x = x + 1;
    } else {
      x = x + 2;
    }
  }
  return x;
  //__VERIFIER_assert(!(x % 2));
}
