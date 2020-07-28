
int phases_1_2_false() {
  int x;
  x = 0;

  while (x < 1000) {
     invariant: (x >= 0) and (x - 1002 <= 0);
    if (x < 1000) {
      x = x + 1;
    } else {
      x = x + 2;
    }
  }
  return x;
  //__VERIFIER_assert(!(x % 2));
}
