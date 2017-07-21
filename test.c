#include<stdio.h>
#include<math.h>

const int MAX=3;

void test() {
	int a = 0;
	a = a + 2;
	int b;
	if (a < 10) {
		b = 2;
	}
	b = 10;
	a = a + b;
}

void testSwitch() {
	int a = 10;
	int b = 10;
	int c = 20;

	switch (a) {
	case b:
	  /* Code */
		c = 30;
	  break;
	case c:
	  /* Code */
		c = 40;
	  break;
	default:
	  /* Code */
		c = 50;
	  break;
	}
}

int sum(int i, int j) {


}




