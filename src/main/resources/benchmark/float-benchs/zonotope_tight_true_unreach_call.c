
double zonotope_tight_true_unreach_call(double IN)
{
  double x;
  double y;
  x = IN;
  //  __VERIFIER_assume(x >= 0. && x <= 10.);

  y = x*x - x;
  if (y >= 0) y = x / 10.0;
  else y = x*x + 2.0;

  //__VERIFIER_assert(y >= 0. && y <= 4.);
  return y;
}
