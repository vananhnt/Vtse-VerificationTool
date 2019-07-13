float foo (float a, float b) {
	float result = 0;
	if (a > b) {
		result = a / (b - 2);	
	} else {
		result = a + b;
	}	
	return result;
}
