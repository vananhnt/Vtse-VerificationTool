float f(float x)
{
  return x - (x*x*x)/6.0f + (x*x*x*x*x)/120.0f + (x*x*x*x*x*x*x)/5040.0f;
}

float fp(float x)
{
  return 1 - (x*x)/2.0f + (x*x*x*x)/24.0f + (x*x*x*x*x*x)/720.0f;
}
int main() {
  	float IN = 1;
	float ITERATIONS = 3;
  	float x = IN - f(IN)/fp(IN);
	
	if (ITERATIONS > 1) {
		x = x - f(x)/fp(x);
		if (ITERATIONS > 2) {
		x = x - f(x)/fp(x);
		}
		}
	return 0;
}