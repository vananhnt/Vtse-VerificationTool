
int main()
{
  double x,y;

  x = __VERIFIER_nondet_double();
  __VERIFIER_assume(x >= 0. && x <= 10.);

  y = x*x - x;
  if (y >= 0) y = x / 10.;
  else y = x*x + 2.;

  __VERIFIER_assert(y >= 0. && y <= 4.);
  return 0;
}
