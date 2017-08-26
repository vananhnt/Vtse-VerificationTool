double sine_3_false_unreach_call(float IN)
{
  float x = IN;
  float result = x - (x*x*x)/6.0 + (x*x*x*x*x)/120.0 + (x*x*x*x*x*x*x)/5040.0;
  return result;
}
