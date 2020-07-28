
int simple_4_1_false() {
  int x = 100001;

  while (x > 1) {
     invariant: x - 1 > 0;
    x = x - 2;
  }
  return x;
  //__VERIFIER_assert(!(x % 2));
}
