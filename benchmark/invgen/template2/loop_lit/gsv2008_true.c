// Source: Sumit Gulwani, Saurabh Srivastava, Ramarathnam Venkatesan: "Program
// Analysis as Constraint Solving", PLDI 2008.

int gsv2008_true() {
    int x;
    int y;
    x = -50;
    y = 50;
    //if (!(-1000 < y && y < LARGE_INT)) return 0;
    while (x < 0) {
        x = x + y;
        y = y + 1;
    }
   // __VERIFIER_assert(y > 0);
    return y;
}
