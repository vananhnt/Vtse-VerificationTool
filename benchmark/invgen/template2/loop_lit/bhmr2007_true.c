// Source: Dirk Beyer, Thomas A. Henzinger, Rupak Majumdar, Andrey
// Rybalchenko: "Path Invariants", PLDI 2007.

//true
int bhmr2007_true() {
    int i;
    int a;
    int b;
    int n;
    i = 0; a = 0; b = 0; n = 100;
 
    while (i < n) {
     invariant: (-b-n <= 0) and (-b-n+1 <= 0) and (-b+n-100 <= 0) and (b-n <= 0) and (b-n+1 <= 0) and (b+n-100 <= 0) and (a-b-n <= 0) and (a-b-n+1 <= 0) and (a-b+n-100 <= 0) and (a+b-n <= 0) and (a+b-n+1 <= 0) and (a+b+n-100 <= 0) and (i-b-n <= 0) and (i-b-n+1 <= 0) and (i-b+n-100 <= 0) and (i+b-n <= 0) and (i+b-n+1 <= 0) and (i+b+n-100 <= 0) and (i+a-b-n <= 0) and (i+a-b-n+1 <= 0) and (i+a-b+n-100 <= 0) and (i+a+b-n <= 0) and (i+a+b-n+1 <= 0) and (i+a+b+n-100 <= 0);
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
