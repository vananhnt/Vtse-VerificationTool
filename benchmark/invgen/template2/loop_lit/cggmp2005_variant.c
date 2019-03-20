// Source: A. Costan, S. Gaubert, E. Goubault, M. Martel, S. Putot: "A Policy
// Iteration Algorithm for Computing Fixed Points in Static Analysis of
// Programs", CAV 2005

int cggmp2005_variant() {
    int lo, mid, hi;
    lo = 0;
    mid = 100;
    hi = 2*mid;
    
    while (mid > 0) {
        lo = lo + 1;
        hi = hi - 1;
        mid = mid - 1;
    }
 //   __VERIFIER_assert(lo == hi);
    return 0;
}
