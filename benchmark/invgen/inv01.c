void inv01()
{
 int x=1; int y=1;
 while(__BLAST_NONDET) {
   invariant: x = y ;
   int t1 = x;
   int t2 = y;
   x = t1 + t2;
   y = t1 + t2;
 }
   post: y >= 1;
}
