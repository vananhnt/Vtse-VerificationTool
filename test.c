#include<stdio.h>
#include<math.h>

const int MAX=3;

void test() {
	int a = 0;
	a = a + 2;
	int b;
	b = a;
	if (  a == 2){
		b = a + 1;
	}

	for (int i=0; i<10; i++){
		a = a + 1;
		if ( i == 1){
			break;
		}
		a = 0;
	}

}

 int sum( int i) {
	int resulf = i++;
	return  resulf;
}

