// Source: E. De Angelis, F. Fioravanti, J. A. Navas, M. Proietti:
// "Verification of Programs by Combining Iterated Specialization with
// Interpolation", HCVS 2014
//true
int afnp2014_true() {
    int x;
    int y;
    x = 1;
    y = 0;
    while (y < 1000) {
     invariant: (x + y >= 0) and (x + y - 1 >= 0) and (x - y >= 0) and (x - y - 1 >= 0) and (x - y - 1 <= 0) and (x + y - 1 <= 0);
        x = x + y;
        y = y + 1;
    }
    //__VERIFIER_assert(x >= y);
    return x - y;
}
