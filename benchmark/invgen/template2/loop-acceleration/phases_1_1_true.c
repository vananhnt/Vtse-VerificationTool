
int phases_1_1_true() {
  int x = 0;

  while (x < 1000) {
    if (x < 500) {
      x = x + 1;
    } else {
      x = x + 2;
    }
  }
  return x;
  //__VERIFIER_assert(!(x % 2));
}
