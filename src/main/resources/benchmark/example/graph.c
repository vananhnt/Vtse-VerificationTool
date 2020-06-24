//int dis (int a, int b) {
//    float result = 0;
//    if (a > b) {
//        result = a - b;
//    } else {
//        result = b - a;
//    }
//    return result;
//}
//
//int relu(int a){
//    if (a < 0) {
//        a = 0;
//    }
//    return a;
//}


int absolute(int a){
    int result = 0;
    for(int i=0;i<10;i++){
        result = result + i;
    }
    return result;
}


int main(int n, int m){
    int result = n-m;
    result = absolute(result);
    return result;
}
//int multival_1(int x){
//    int y = x;
//    while(x < 1000){
//        x = x + 1;
//        y = y + 1;
//    }
//    return y;
//}
//int swap(int a, int b){
//    a = a + b;
//    b = a - b;
//    a = a - b;
//    return 0;
//}

//int simple(int a){
//    a = 1;
//    return a;
//}

//int main(int n, int m){
//    int result = 0;
//    for(int i=0;i<n;i++){
//        result = result * 2;
//        for(int j=0;j<m;j++){
//            result = result + j;
//        }
//    }
//    return result;
//}