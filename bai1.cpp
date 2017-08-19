// input :: chuoi ki tu tu ban phim                 yuht
// output :: chuoi nguoc lai trong rev.txt.....     thuy
#include <iostream>
#include <fstream>
#include <cstring>
using namespace std;

int test( int b, int c){

	for (int i=0; i<10; i++){
		b = i + b;
	}

	return b + c;
}
