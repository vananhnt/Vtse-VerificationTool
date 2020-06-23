int foo (int a, int b) {
    float result = 0;
    if (a > b) {
        result = a - b;
    } else {
        result = a + b;
    }
    return result;
}


int main(int n, int m){
    result = 0;
    for(int i=0;i<n;i++){
        result = result * 2;
        for(int j=0;j<m;j++){
            result = result + j;
        }
    }
    return result;
}