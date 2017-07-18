#include<stdio.h>
#include<math.h>

const int MAX=3;

void test() {
	int a = 0;
	a = a + 2;
	int b;

	for (int i = 0; i < 10; i ++) {
		if (i == 9) {
			break;
		} else {
			a++;
		}
	}
}

//void testSwitch() {
//	int a = 10;
//	int b = 10;
//	int c = 20;
//
//	switch (a) {
//	case b:
//	  /* Code */
//		c = 30;
//	  break;
//	case c:
//	  /* Code */
//		c = 40;
//	  break;
//	default:
//	  /* Code */
//		c = 50;
//	  break;
//	}
//}

int sum( int i) {
	int resulf = i++;
	return  resulf;
}


