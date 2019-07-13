//false
int Mono1_1_false() {
  int x;
  x = 0;
  while (x < 10000) {
     invariant: (x >= 0) and (x - 10002 <= 0);
    if (x < 10000) {
      x = x + 1;
    } else {
      x = x + 2;
    }
  }
  return x;
  //__VERIFIER_assert(x == 100000001) ;
}
