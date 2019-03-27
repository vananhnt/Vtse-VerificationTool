
int simple_1_1_false() {
  int x = 0;

  while (x < 100001) {
    x = x + 2;
  }
  return x;
  //__VERIFIER_assert(x % 2);
}
