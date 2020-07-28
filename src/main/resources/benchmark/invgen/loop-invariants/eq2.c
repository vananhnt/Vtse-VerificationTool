int main(void) {
  int x;
  x = 0;
  while (__VERIFIER_nondet_int()) {
    x = x + 2;
  }
//  __VERIFIER_assert(!(x % 2));
  return x;
}