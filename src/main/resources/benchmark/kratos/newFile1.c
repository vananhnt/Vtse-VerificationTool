int assert_1 = 0;
int m_pc  =    0;
int t1_pc  =    0;
int m_st  ;
int t1_st  ;
int m_i  ;
int t1_i  ;
int M_E  =    2;
int T1_E  =    2;
int E_1  =    2;
void transmit5()
{
  {
  if (t5_pc == 0) {
T5_ENTRY: ;
  {
  while (1 < 2){
    //while_6_continue: /* CIL Label */ ;
    t5_pc = 1;
    t5_st = 2;
return_label:; /* CIL Label */
   }
   }
  } else {
    if (t5_pc == 1) {
T5_WAIT:
    {
    assert_1 = 1;//assert 0
    }
  while_6_break: /* CIL Label */ ;
  return_label:; /* CIL Label */
    }
  }
  T5_ENTRY: ;
  {
  while (1 < 2){
    //while_6_continue: /* CIL Label */ ;
    t5_pc = 1;
    t5_st = 2;
    return_label:; /* CIL Label */
    T5_WAIT:
    {
    assert_1 = 1;//assert 0
    }
  }
  while_6_break: /* CIL Label */ ;
  }
  return_label:; /* CIL Label */
}
}

int main() {
  int __retres1 ;
  transmit5();
    __retres1 = assert_1;
  return (__retres1);
}

