// Source: Michael Colon, Sriram Sankaranarayanan, Henny Sipma: "Linear
// Invariant Generation using Non-Linear Constraint Solving", CAV 2003.

int css2003_true() {
    int i;
    int j;
    int k;
    i = 1;
    j = 1;
    k = 100;
    while (i < 100) {
     invariant: (-j-k <= 0) and (-j-k+1 <= 0) and (-j+k-99 <= 0) and (-100*i-j+k+1 <= 0) and (j-k <= 0) and (j-k+1 <= 0) and (j+k-101 <= 0) and (-102*i+j+k+1 <= 0) and (101*i-j-k <= 0) and (100*i-j-k+1 <= 0) and (i-j+k-100 <= 0) and (i-102*j+k+1 <= 0) and (i+j-k <= 0) and (i+j-k+1 <= 0) and (i+j+k-102 <= 0);
        i = i + 1;
        j = j + k;
        k = k - 1;
        //__VERIFIER_assert(1 <= i + k && i + k <= 2 && i >= 1);
    }
    return i + k;
}
