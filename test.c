#include<stdio.h>
#include<math.h>

const int MAX=3;

void test() {
	int a = 0;
	a = a + 2;
	int b;
	b = a;
	if (a == 2) {
		b = a + 1;
	}
//	for (int i=0; i<10; i++){
//		a = a + 1;
//		if ( i == 1){
//			break;
//		}
//		a = 0;
//	}
	while (a > 0) {
		a --;
		b = a + 10;
	}

//	do {
//		a ++;
//	} while (a < 10);

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


