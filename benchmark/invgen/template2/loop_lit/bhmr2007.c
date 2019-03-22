// Source: Dirk Beyer, Thomas A. Henzinger, Rupak Majumdar, Andrey
// Rybalchenko: "Path Invariants", PLDI 2007.

//true
int bhmr2007() {
    int i;
    int n;
    int a;
    int b;
    i = 0; a = 0; b = 0; n = 100;
 
    while (i < n) {
     invariant: b >= 0 ; b - i >= 0 ; a - 3*b + i <= 0 ; a - b - i <= 0 ; a + b - 3*i <= 0 ; 2*a - b >= 0 ; 3*a - b - i >= 0;
        if (VERIFIER_NON_DET) {
            a = a + 1;
            b = b + 2;
            i = i + 1;
        } else {
            a = a + 2;
            b = b + 1;
            i = i + 1;
        }
    }
  //  __VERIFIER_assert(a + b == 3*n);
    return a + b - 3*n;
}
