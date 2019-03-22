// Source: Sumit Gulwani, Nebosja Jojic: "Program Verification as
// Probabilistic Inference", POPL 2007.
int gj2007() {
    int x;
    int y;
    x = 0;
    y = 50;
    while(x < 100) {
     invariant: y - 1 >= 0 ; x - y + 1 <= 0 ; x - y + 50 >= 0;
        if (x < 50) {
            x = x + 1;
        } else {
            x = x + 1;
            y = y + 1;
        }
    }
  //  __VERIFIER_assert(y == 100);
    return y;
}
