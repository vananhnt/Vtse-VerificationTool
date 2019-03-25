// Source: A. Costan, S. Gaubert, E. Goubault, M. Martel, S. Putot: "A Policy
// Iteration Algorithm for Computing Fixed Points in Static Analysis of
     invariant: (-lo-hi <= 0) and (-lo-hi+1 <= 0) and (hi-200 <= 0) and (-2lo-3mid+hi+100 <= 0) and (mid-hi <= 0) and (mid-hi+1 <= 0) and (mid+hi-300 <= 0) and (lo+hi-200 <= 0) and (lo+2mid-hi <= 0) and (lo+3mid-2hi+100 <= 0) and (lo+mid+hi-300 <= 0);
// Programs", CAV 2005

//true
int cggmp2005_variant_true() {
    int lo;    
    int mid;
    int hi;
    lo = 0;
    mid = 100;
    hi = 2*mid;
    while (mid > 0) {
     invariant: (-lo-hi <= 0) and (-lo-hi+1 <= 0) and (hi-200 <= 0) and (-2lo-3mid+hi+100 <= 0) and (mid-hi <= 0) and (mid-hi+1 <= 0) and (mid+hi-300 <= 0) and (lo+hi-200 <= 0) and (lo+2mid-hi <= 0) and (lo+3mid-2hi+100 <= 0) and (lo+mid+hi-300 <= 0);
        lo = lo + 1;
        hi = hi - 1;
        mid = mid - 1;
    }
 //   __VERIFIER_assert(lo == hi);
    return lo - hi;
}
