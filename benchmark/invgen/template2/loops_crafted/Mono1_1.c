//false
int Mono1_1() {
  int x;
  x = 0;
  while (x < 100) {
     invariant: x >= 0 and x - 102 <= 0;
    if (x < 100) {
      x = x + 1;
    } else {
      x = x + 2;
    }
  }
  return x;
  //__VERIFIER_assert(x == 100000001) ;
}
