// Source: A. Costan, S. Gaubert, E. Goubault, M. Martel, S. Putot: "A Policy


int cggmp2005_true() {
    int i;
    int j;
    i = 1;
    j = 10;
    while (j >= i) {
     invariant: +0) <= 0;
        i = i + 2;
        j = -1 + j;
    }
  //  __VERIFIER_assert(j == 6);
    return j;
}
