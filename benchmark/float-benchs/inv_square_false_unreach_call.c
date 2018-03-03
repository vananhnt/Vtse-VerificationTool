//extern void __VERIFIER_error() __attribute__ ((__noreturn__));
/*
  The assertion does not hold.
 */

//extern float __VERIFIER_nondet_float(void);
//extern void __VERIFIER_assume(int expression);
//void __VERIFIER_assert(int cond) { if (!(cond)) { ERROR: __VERIFIER_error(); } return; }

int inv_square_false_unreach_call(int IN)
{
  float x;
  float  y;

  x = IN;
 // __VERIFIER_assume(x >= -0.1 && x <= 0.1);

  if (x != 0) {
    y = x * x;
    //__VERIFIER_assert(y != 0.f);

  }
  return y;
}
