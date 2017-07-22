// input :: chuoi ki tu tu ban phim                 yuht
// output :: chuoi nguoc lai trong rev.txt.....     thuy
#include <iostream>
#include <fstream>
#include <cstring>
using namespace std;

int test( int b, int c){

	if( b == c){
		b = 1;
	} else{
		c = 2;
	}
	return b + c;
}
