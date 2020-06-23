//int dis (int a, int b) {
//    float result = 0;
//    if (a > b) {
//        result = a - b;
//    } else {
//        result = b - a;
//    }
//    return result;
//}

//int relu(int a){
//    if (a < 0) {
//        a = 0;
//    }
//    return a;
//}

int main(int n, int m){
    int result = 0;
    for(int i=0;i<n;i++){
        if(result < 10){
            result = result + i;
        }
    }
    return result;
}

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