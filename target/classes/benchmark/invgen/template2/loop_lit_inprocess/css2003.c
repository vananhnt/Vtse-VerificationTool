// Source: Michael Colon, Sriram Sankaranarayanan, Henny Sipma: "Linear
// Invariant Generation using Non-Linear Constraint Solving", CAV 2003.

int css2003() {
    int i;
    int j;
    int k;
    i = 1;
    j = 1;
    k = 100;
    while (i < 100) {
    invariant: k <= 0 and j <= 0 and i <= 0;
        i = i + 1;
        j = j + k;
        k = k - 1;
        //__VERIFIER_assert(1 <= i + k && i + k <= 2 && i >= 1);
    }
    return i + k;
}
