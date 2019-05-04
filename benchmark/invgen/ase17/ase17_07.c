//assume( n >= 0 );

int ase17_07() {
  int i; int a; int b; int n;
  i = 0; a = 0; b = 0; n = 10;
  while( i < n ) {
    if(__BLAST_NONDET) {
      a = a+1;
      b = b+2;
      i = i+1;
    } else {
      a = a+2;
      b = b+1;
      i = i+1;
    }
  }
  //assert( a+b == 3*n );
    return a+b-3*n;
}
