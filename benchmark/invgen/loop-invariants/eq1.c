int main(void) {
  int w, x, y, z; 
  w = 100;
  w - x = 0;
  y = 500;
  y - z = 0;
  while (__VERIFIER_nondet_uint()) {
    if (__VERIFIER_nondet_uint()) {
      w = w + 1;
      x = x + 1;
    } else {
      y = y - 1;
      z = z - 1;
    }
  }
//  __VERIFIER_assert(w == x && y == z);
  return (w - x) + (y - z);
}