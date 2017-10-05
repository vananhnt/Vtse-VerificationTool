int m_pc  =    0;
int t1_pc  =    0;
int t2_pc  =    0;
int t3_pc  =    0;
int t4_pc  =    0;
int t5_pc  =    0;
int t6_pc  =    0;
int t7_pc  =    0;
int t8_pc  =    0;
int t9_pc  =    0;
int t10_pc  =    0;
int m_st  ;
int t1_st  ;
int t2_st  ;
int t3_st  ;
int t4_st  ;
int t5_st  ;
int t6_st  ;
int t7_st  ;
int t8_st  ;
int t9_st  ;
int t10_st  ;
int m_i  ;
int t1_i  ;
int t2_i  ;
int t3_i  ;
int t4_i  ;
int t5_i  ;
int t6_i  ;
int t7_i  ;
int t8_i  ;
int t9_i  ;
int t10_i  ;
int M_E  =    2;
int T1_E  =    2;
int T2_E  =    2;
int T3_E  =    2;
int T4_E  =    2;
int T5_E  =    2;
int T6_E  =    2;
int T7_E  =    2;
int T8_E  =    2;
int T9_E  =    2;
int T10_E  =    2;
int E_M  =    2;
int E_1  =    2;
int E_2  =    2;
int E_3  =    2;
int E_4  =    2;
int E_5  =    2;
int E_6  =    2;
int E_7  =    2;
int E_8  =    2;
int E_9  =    2;
int E_10  =    2;
int is_master_triggered(void) ;
int is_transmit1_triggered(void) ;
int is_transmit2_triggered(void) ;
int is_transmit3_triggered(void) ;
int is_transmit4_triggered(void) ;
int is_transmit5_triggered(void) ;
int is_transmit6_triggered(void) ;
int is_transmit7_triggered(void) ;
int is_transmit8_triggered(void) ;
int is_transmit9_triggered(void) ;
int is_transmit10_triggered(void) ;
void immediate_notify(void) ;
int token  ;
int __NONDET  ;
int local  ;

int is_master_triggered(void)
{ int __retres1 ;

  {
  if (m_pc == 1) {
    if (E_M == 1) {
      __retres1 = 1;
      goto return_label;
    } else {

    }
  } else {

  }
  __retres1 = 0;
  return_label: /* CIL Label */
  return (__retres1);
}
}

int is_transmit1_triggered(void)
{ int __retres1 ;

{
if (t1_pc == 1) {
  if (E_1 == 1) {
    __retres1 = 1;
    goto return_label;
  } else {

  }
} else {

}
__retres1 = 0;
return_label: /* CIL Label */
return (__retres1);
}
}
int is_transmit2_triggered(void)
{ int __retres1 ;

{
if (t2_pc == 1) {
  if (E_2 == 1) {
    __retres1 = 1;
    goto return_label;
  } else {

  }
} else {

}
__retres1 = 0;
return_label: /* CIL Label */
return (__retres1);
}
}
int is_transmit3_triggered(void)
{ int __retres1 ;

{
if (t3_pc == 1) {
  if (E_3 == 1) {
    __retres1 = 1;
    goto return_label;
  } else {

  }
} else {

}
__retres1 = 0;
return_label: /* CIL Label */
return (__retres1);
}
}
int is_transmit4_triggered(void)
{ int __retres1 ;

{
if (t4_pc == 1) {
  if (E_4 == 1) {
    __retres1 = 1;
    goto return_label;
  } else {

  }
} else {

}
__retres1 = 0;
return_label: /* CIL Label */
return (__retres1);
}
}
int is_transmit5_triggered(void)
{ int __retres1 ;

{
if (t5_pc == 1) {
  if (E_5 == 1) {
    __retres1 = 1;
    goto return_label;
  } else {

  }
} else {

}
__retres1 = 0;
return_label: /* CIL Label */
return (__retres1);
}
}
int is_transmit6_triggered(void)
{ int __retres1 ;

{
if (t6_pc == 1) {
  if (E_6 == 1) {
    __retres1 = 1;
    goto return_label;
  } else {

  }
} else {

}
__retres1 = 0;
return_label: /* CIL Label */
return (__retres1);
}
}
int is_transmit7_triggered(void)
{ int __retres1 ;

{
if (t7_pc == 1) {
  if (E_7 == 1) {
    __retres1 = 1;
    goto return_label;
  } else {

  }
} else {

}
__retres1 = 0;
return_label: /* CIL Label */
return (__retres1);
}
}
int is_transmit8_triggered(void)
{ int __retres1 ;

{
if (t8_pc == 1) {
  if (E_8 == 1) {
    __retres1 = 1;
    goto return_label;
  } else {

  }
} else {

}
__retres1 = 0;
return_label: /* CIL Label */
return (__retres1);
}
}
int is_transmit9_triggered(void)
{ int __retres1 ;

{
if (t9_pc == 1) {
  if (E_9 == 1) {
    __retres1 = 1;
    goto return_label;
  } else {

  }
} else {

}
__retres1 = 0;
return_label: /* CIL Label */
return (__retres1);
}
}
int is_transmit10_triggered(void)
{ int __retres1 ;

{
if (t10_pc == 1) {
  if (E_10 == 1) {
    __retres1 = 1;
    goto return_label;
  } else {

  }
} else {

}
__retres1 = 0;
return_label: /* CIL Label */
return (__retres1);
}
}


void activate_threads(void)
{ int tmp ;
  int tmp___0 ;
  int tmp___1 ;
  int tmp___2 ;
  int tmp___3 ;
  int tmp___4 ;
  int tmp___5 ;
  int tmp___6 ;
  int tmp___7 ;
  int tmp___8 ;
  int tmp___9 ;

  {
  {
  tmp = is_master_triggered();
  }
  if (tmp) {
    m_st = 0;
  } else {

  }
  {
  tmp___0 = is_transmit1_triggered();
  }
  if (tmp___0) {
    t1_st = 0;
  } else {

  }
  {
  tmp___1 = is_transmit2_triggered();
  }
  if (tmp___1) {
    t2_st = 0;
  } else {

  }
  {
  tmp___2 = is_transmit3_triggered();
  }
  if (tmp___2) {
    t3_st = 0;
  } else {

  }
  {
  tmp___3 = is_transmit4_triggered();
  }
  if (tmp___3) {
    t4_st = 0;
  } else {

  }
  {
  tmp___4 = is_transmit5_triggered();
  }
  if (tmp___4) {
    t5_st = 0;
  } else {

  }
  {
  tmp___5 = is_transmit6_triggered();
  }
  if (tmp___5) {
    t6_st = 0;
  } else {

  }
  {
  tmp___6 = is_transmit7_triggered();
  }
  if (tmp___6) {
    t7_st = 0;
  } else {

  }
  {
  tmp___7 = is_transmit8_triggered();
  }
  if (tmp___7) {
    t8_st = 0;
  } else {

  }
  {
  tmp___8 = is_transmit9_triggered();
  }
  if (tmp___8) {
    t9_st = 0;
  } else {

  }
  {
  tmp___9 = is_transmit10_triggered();
  }
  if (tmp___9) {
    t10_st = 0;
  } else {

  }

  return;
}
}
void immediate_notify(void)
{

  {
  {
  activate_threads();
  }

  return;
}
}

void transmit3(void)
{

  {
  if (t3_pc == 0) {
    goto T3_ENTRY;
  } else {
    if (t3_pc == 1) {
      goto T3_WAIT;
    } else {

    }
  }
  T3_ENTRY: ;
  {
  while (1) {
    while_3_continue: /* CIL Label */ ;
    t3_pc = 1;
    t3_st = 2;

    goto return_label;
    T3_WAIT:
    {
    token += 1;
    E_4 = 1;
    immediate_notify();
    E_4 = 2;
    }
  }
  while_3_break: /* CIL Label */ ;
  }

  return_label: /* CIL Label */
  return;
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
