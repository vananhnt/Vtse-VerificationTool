//newton 1.1
float f(float x) {
  return x - (x*x*x)/6.0f + (x*x*x*x*x)/120.0f + (x*x*x*x*x*x*x)/5040.0f;
}

float fp(float x) {
  return 1 - (x*x)/2.0f + (x*x*x*x)/24.0f + (x*x*x*x*x*x)/720.0f;
}

float newton_1_1_true_unreach_call(float IN){
	float x = IN - f(IN) / fp(IN);	// x = IN - f(IN)/fp(IN)
	return x;
}

