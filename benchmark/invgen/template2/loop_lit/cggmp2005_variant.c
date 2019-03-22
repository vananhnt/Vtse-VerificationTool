// Source: A. Costan, S. Gaubert, E. Goubault, M. Martel, S. Putot: "A Policy
// Iteration Algorithm for Computing Fixed Points in Static Analysis of
     invariant: mid <= 0 and lo + mid = 0 and hi - mid <= 0 and hi + mid <= 0 and hi + 2*lo + mid <= 0 and 2*hi + lo - mid <= 0;
// Programs", CAV 2005

//true
int cggmp2005_variant() {
    int lo;    
    int hi;
    int mid;
    lo = 0;
    mid = 100;
    hi = 2*mid;
    
    while (mid > 0) {
     invariant: mid <= 0 and lo + mid = 0 and hi - mid <= 0 and hi + mid <= 0 and hi + 2*lo + mid <= 0 and 2*hi + lo - mid <= 0;
        lo = lo + 1;
        hi = hi - 1;
        mid = mid - 1;
    }
 //   __VERIFIER_assert(lo == hi);
    return lo - hi;
}
