int f(int a, int b) {
	return a + b;
}
void master(void) {
  {
  if (m_pc == 0) {
    //goto M_ENTRY;
	  a = b;
  } else {
    a = b;
	  if (m_pc == 1) {
      //goto M_WAIT;
    	a = b;
    } else {
    	a = b;
    }
 }
 }
}
void transmit1(void){

  {
  if (t1_pc == 0) {
    goto T1_ENTRY;
  } else {
    if (t1_pc == 1) {
      goto T1_WAIT;
    } else {

    }
  }
  T1_ENTRY: ;
  {
  while (1) {
    //while_1_continue: /* CIL Label */ ;
    t1_pc = 1;
    t1_st = 2;

    //goto return_label;
    T1_WAIT:
    {
    token += 1;
    E_2 = 1;
    //immediate_notify();
    E_2 = 2;
    }
  }
  //while_1_break: /* CIL Label */ ;
  }

  //return_label: /* CIL Label */
  //return;
}
}


//int main() {
//	int sum = 0;
//	int a = 0;
//	int b = 1;
//	sum = sum + f(a, b);
//
//	a = 9;x	x
//	b = 8;
//	sum = sum + f(a, b);
//	return sum;
//}
