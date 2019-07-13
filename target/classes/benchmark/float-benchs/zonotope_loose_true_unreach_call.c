
double zonotope_loose_true_unreach_call(double IN)
{
  double y;
  //__VERIFIER_assume(IN >= 0. && IN <= 10.);

  y = IN*IN - IN;
  if (y >= 0) y = IN / 10.0;
  else y = IN*IN + 2.0;

  //__VERIFIER_assert(y >= 0. && y <= 105.);
  return y;
}
