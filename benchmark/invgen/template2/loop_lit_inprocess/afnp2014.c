// Source: E. De Angelis, F. Fioravanti, J. A. Navas, M. Proietti:
// "Verification of Programs by Combining Iterated Specialization with
// Interpolation", HCVS 2014

int afnp2014() {
    int x;
    int y;
    x = 1;
    y = 0;
    while (y < 1000) {
    invariant:;
        x = x + y;
        y = y + 1;
    }
    //__VERIFIER_assert(x >= y);
    return x - y;
}