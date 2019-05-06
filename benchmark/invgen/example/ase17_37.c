/*
 * Taken from "Counterexample Driven Refinement for Abstract Interpretation" (TACAS'06) by Gulavani
     invariant: (m - x <= 0) and (m - x + 1 >= 0);
 */

int ase17_37() {
  int x;
  int m;
  x = 0;
  m = 0;
  while(x < 1000000) {
     invariant: (m - x <= 0) and (m - x + 1 >= 0);
    m = x;
    x = x + 1;
  }
  //assert(0<=m && m<n);
  return m;
}
