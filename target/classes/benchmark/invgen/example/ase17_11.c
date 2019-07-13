
int ase17_11() {
  int j; int i;
  j = 0;
  for (i = 0; i < 100 ; i++) {
     invariant: (i >= 0) and (2*i - j <= 0) and (2*i - j >= 0);
     j = j + 2;
  }
//  assert(j == 2*x);
  return j;
}


