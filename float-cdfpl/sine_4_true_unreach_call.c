
	public float sine_4_true_unreach_call(float IN)
	{

		float x = IN;
		float result = x - (x*x*x)/6 + (x*x*x*x*x)/120 + (x*x*x*x*x*x*x)/5040;

		return result;
	}
