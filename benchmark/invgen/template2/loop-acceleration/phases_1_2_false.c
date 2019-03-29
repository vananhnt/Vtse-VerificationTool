
int phases_1_2_false() {
  int x;
  x = 0;

  while (x < 10000) {
     invariant: (x >= 0) and (x - 5002 <= 0);
    if (x < 5000) {
      x = x + 1;
    } else {
      x = x + 2;
    }
  }
  return x;
  //__VERIFIER_assert(!(x % 2));
}
