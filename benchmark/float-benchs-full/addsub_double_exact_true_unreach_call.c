/* Rounding addition and subtraction in double-precision floats. */

int addsub_double_exact_true_unreach_call()
{
  double x;
  double y;
  double z;
  double r;
  x = 100000000;
  y = x + 1;
  z = x - 1;
  r = y - z;  
  return r;
}
