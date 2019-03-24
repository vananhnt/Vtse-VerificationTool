// Source: A. Costan, S. Gaubert, E. Goubault, M. Martel, S. Putot: "A Policy
// Iteration Algorithm for Computing Fixed Points in Static Analysis of
     invariant: i + 23*j - 231 <= 0 and 10*i - j >= 0 and 11*i - 1 >= 0 and 11*i - j - 1 >= 0;
// Programs", CAV 2005

int cggmp2005() {
    int i;
    int j;
    i = 1;
    j = 10;
    while (j >= i) {
     invariant: i + 23*j - 231 <= 0 and 10*i - j >= 0 and 11*i - 1 >= 0 and 11*i - j - 1 >= 0;
        i = i + 2;
        j = -1 + j;
    }
  //  __VERIFIER_assert(j == 6);
    return j;
}
