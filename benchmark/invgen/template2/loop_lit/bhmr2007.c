// Source: Dirk Beyer, Thomas A. Henzinger, Rupak Majumdar, Andrey
// Rybalchenko: "Path Invariants", PLDI 2007.

int bhmr2007() {
    int i, n, a, b;
    i = 0; a = 0; b = 0; n = 100;
 
    while (i < n) {
    invariant:;
        if (__VERIFIER_nondet_int()) {
            a = a + 1;
            b = b + 2;
        } else {
            a = a + 2;
            b = b + 1;
        }
        i = i + 1;
    }
  //  __VERIFIER_assert(a + b == 3*n);
    return (a + b - 3*n);
}