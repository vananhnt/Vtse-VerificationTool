
double C1 =  0.0416666666666666019037;
double C2 = -0.00138888888888741095749;
double C3 =  0.0000248015872894767294178;
double C4 = -0.00000275573143513906633035;
double C5 =  0.00000000208757232129817482790;
double C6 = -0.0000000000113596475577881948265;

double mcos(double x) 
{
  double a;
  double hz;
  double z;
  double r;
  double qx;
  double zr;
  z = x*x;
  if (x < 0.0) x = -x;
  hz = 0.5 * z;
  r = z*(C1+z*(C2+z*(C3+z*(C4+z*(C5+z*C6)))));
  zr = z*r;
  if (x < 0.3) {
    return 1.-(hz-zr);
  }
  else {
    if (x > 0.78125) {
      qx = 0.28125;
    }
    else {
      qx = x/4.0;
    }
    hz = hz-qx;
    a = 1.0-qx;
    return a-(hz-zr);
  }
}

double cos_polynomial_true_unreach_call(double IN)
{
 double r;

  //__VERIFIER_assume(IN >= -0.5 && IN <= 0.75);

  r = mcos(IN);



  //__VERIFIER_assert(r >= 0. && r <= 1.1);
  return r;
}
