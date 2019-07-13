//extern void __VERIFIER_error() __attribute__ ((__noreturn__));
/* The assertion does not hold. */

//extern int __VERIFIER_nondet_int(void);
//extern void __VERIFIER_assume(int expression);
//void __VERIFIER_assert(int cond) { if (!(cond)) { ERROR: __VERIFIER_error(); } return; }

int float_int_inv_square_false_unreach_call(int IN)
{
  int x;
  x = IN;
  float y;
  float z;

  //x = __VERIFIER_nondet_int();
  //__VERIFIER_assume(x >= -10 && x <= 10);

  y = x*x - 0.4;
  //__VERIFIER_assert(y != 0.f);
  z = 0.1 / y;
  return y;
}
