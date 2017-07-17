#include<stdio.h>
#include<math.h>

const int MAX=3;

void test() {
	int a = 0;
	a = a + 2;
	int b;

	while (a > 0) {
		a --;
		b = a + 10;
	}
	for (int i = 0; i < a; i++) {
		for (int j = 0; j < 9; j++) {
			b = 10;
		}
	}

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

int sum( int i) {
	int resulf = i++;
	return  resulf;
}


