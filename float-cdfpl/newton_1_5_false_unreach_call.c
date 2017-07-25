//newton 1.5


	float newton_1_5_false_unreach_call(float IN)
	{
		float f_IN = IN - (IN*IN*IN)/6 + (IN*IN*IN*IN*IN)/120 + (IN*IN*IN*IN*IN*IN*IN)/5040;
		  float fp_IN = 1 - (IN*IN)/2 + (IN*IN*IN*IN)/24 + (IN*IN*IN*IN*IN*IN)/720;
		  
		  float x = IN - f_IN / fp_IN;	// x = IN - f(IN)/fp(IN)
		  return x;
	}

