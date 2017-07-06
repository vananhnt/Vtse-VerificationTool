// input :: chuoi ki tu tu ban phim                 yuht
// output :: chuoi nguoc lai trong rev.txt.....     thuy
#include <iostream>
#include <fstream>
#include <cstring>
using namespace std;

int main(){
    ofstream out("rev.txt");
    string str;
    getline(cin, str);

    for (int i = str.length() -1 ; i>=0; i-- )
        out << str[i];
    out.close();
return 0;
}
