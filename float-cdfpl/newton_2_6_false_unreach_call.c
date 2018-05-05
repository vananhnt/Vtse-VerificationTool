//newton 2.6

	float newton_2_6_false_unreach_call(float IN)
	{
		 float f_IN = IN - (IN*IN*IN)/6 + (IN*IN*IN*IN*IN)/120 + (IN*IN*IN*IN*IN*IN*IN)/5040;
		  float fp_IN = 1 - (IN*IN)/2 + (IN*IN*IN*IN)/24 + (IN*IN*IN*IN*IN*IN)/720;
		  
		  float x = IN - f_IN / fp_IN;	// x = IN - f(IN)/fp(IN)
		  
		  float f_x1 = x - (x*x*x)/6 + (x*x*x*x*x)/120 + (x*x*x*x*x*x*x)/5040;
		  float fp_x1 = 1 - (x*x)/2 + (x*x*x*x)/24 + (x*x*x*x*x*x)/720;

		  x = x - f_x1/fp_x1;		// x = x - f(x) / fp(x)
		  
		  return x;
	}

