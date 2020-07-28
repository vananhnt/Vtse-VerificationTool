int phases_2_1_false() {
  int x;
  int y;
  x = 1;
  y = 100;

  while (x < y) {
     invariant: (x - y + 99 >= 0) and (x + y - 101 <= 0) and (x + y - 1 >= 0) and (99*x - y + 1 <= 0) and (100*x - y <= 0) and (101*x - y - 1 >= 0);
    if (x < y / x) {
      x = x * x;
    } else {
      x = x + 1;
    }
  }
    return x - y;
}
