int inv01() {
 int x=1; int y=1;
 while(1 > 0) {
   invariant: y >= 1 ;
   int t1 = x;
   int t2 = y;
   x = t1 + t2;
   y = t1 + t2;
 }
   y = -y;
   return y;
   //post: y >= 1;
}
