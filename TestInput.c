// TEST INPUT
#include<stdio.h>
#include<math.h>
//void set(int n){
//	n = 0;
//}
//	float f(float x){
//		  return x - (x*x*x)/6 + (x*x*x*x*x)/120 + (x*x*x*x*x*x*x)/5040;
//	}

//float fp(float x){
//	  return 1 - (x*x)/2 + (x*x*x*x)/24 + (x*x*x*x*x*x)/720;
//	}

//int max (int a, int b) {
//		int max;
//		if (a > b) {
//			max = a;
//		}
//		else {
//			b = 1;
//			max = fp(b);
//		}
//		return max;
//	}


	float sum(int n) {
		int sum = n;
		int a = sum + n;
		for (int i = 0; i <= n; i++) {
			sum = ((sum + i) + 2) * (i+1) / i;
		}

		return sum;
	}
	

//	float foo36(float IN){
//	  float f_IN = IN - (IN*IN*IN)/6 + (IN*IN*IN*IN*IN)/120 + (IN*IN*IN*IN*IN*IN*IN)/5040;
//	  float fp_IN = 1 - (IN*IN)/2 + (IN*IN*IN*IN)/24 + (IN*IN*IN*IN*IN*IN)/720;
//
//	  float x = IN - f_IN / fp_IN;	// x = IN - f(IN)/fp(IN)
//
//	  float f_x1 = x - (x*x*x)/6 + (x*x*x*x*x)/120 + (x*x*x*x*x*x*x)/5040;
//	  float fp_x1 = 1 - (x*x)/2 + (x*x*x*x)/24 + (x*x*x*x*x*x)/720;
//
//	  x = x - f_x1/fp_x1;		// x = x - f(x) / fp(x)
//
//	  float f_x2 = x - (x*x*x)/6 + (x*x*x*x*x)/120 + (x*x*x*x*x*x*x)/5040;
//	  float fp_x2 = 1 - (x*x)/2 + (x*x*x*x)/24 + (x*x*x*x*x*x)/720;
//
//	  x = x - f_x2/fp_x2;		// // x = x - f(x) / fp(x)
//
//	  return x;
//	}

//int fo(int flag, int n){
//		int k = 1;
//		if(flag > 0)
//			k = n*n;
//
//		int i = 0;
//		int j = 0;
//
//		while(i <= n) {
//			i++;
//			j = j + i;
//		}
//
//		int z = k + i + j;
//
//		return z;
//	}

//	int factorial(int n) {
//		int fac = 1;
//
//		for (int i = 2; i <= n; i++) {
//			fac = fac * i;
//		}
//
//		return fac;
//	}
//
//	int sqr (int a) {
//		return a * a;
//	}
	
//	double foo(double a, double b) {
//		double result;
//		double c = 3 / (b - 3);
//		if (a > b) {
//			result = a / (b - 2);
//			a += 1;
//		} else {
//			result = a + b;
//			a += 1;
//		}
//
//		return result;
//	}
//
//
//	double abs(double x) {
//		double abs = x;
//		if (x < 0) {
//			abs = -x;
//		}
//
//		return abs;
//	}
//
//	public static void main(String[] args) {
//		float x = foo36(0.125);
//		System.out.println("x = " + x);
//	}

}
