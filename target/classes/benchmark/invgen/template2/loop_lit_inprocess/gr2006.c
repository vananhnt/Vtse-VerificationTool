// Source: Denis Gopan, Thomas Reps: "Lookahead Widening", CAV 2006.
//true
int gr2006() {
    int x;
    int y;
    x = 0;
    y = 0;
    while (y >= 0) {
        invariant: x - y >= 0 and x + y >= 0;
        if (x < 50) {
            y = y + 1;
            x = x + 1;
        } else {
            y = y - 1;
            x = x + 1;
        }

    }
   // __VERIFIER_assert(x == 100);
    return x;
}
