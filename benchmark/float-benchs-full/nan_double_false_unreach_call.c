/* Assertion can be violated because uninitialized doubles can be NaN. 
 */

int nan_double_false_unreach_call(double IN)
{
  double x;
  x = IN;
  if (x == x) {
	return 0;
	}
  else return 1;
}
