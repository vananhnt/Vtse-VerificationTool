
int functions_1_2_true() {
  int x = 0;

  while (x < 10000) {
    x = x + 2;
  }
  return x;
 // __VERIFIER_assert(x % 2);
}
