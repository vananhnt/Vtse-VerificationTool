
/*void error() 
{ 

  {
  goto ERROR;
  ERROR: ;
  
}
}*/
int assert_1 = 0;
int assert_2 = 0;
int assert_3 = 0;
int assert_4 = 0;
int assert_5 = 0;
int assert_6 = 0;
int assert_7 = 0;
int assert_8 = 0;
int assert_9 = 0;
int assert_10 = 0;
int q_buf_0  ;
int q_free  ;
int q_read_ev  ;
int q_write_ev  ;
int p_num_write  ;
int p_last_write  ;
int p_dw_st  ;
int p_dw_pc  ;
int p_dw_i  ;
int c_num_read  ;
int c_last_read  ;
int c_dr_st  ;
int c_dr_pc  ;
int c_dr_i  ;

int is_do_write_p_triggered() 
{ int __retres1 ;

  {
  if (p_dw_pc == 1) {
    if (q_read_ev == 1) {
      __retres1 = 1;
      goto return_label;
    } else {

    }
  } else {

  }
  __retres1 = 0;
  return_label: ;/* CIL Label */ 
  return (__retres1);
}
}
int is_do_read_c_triggered() 
{ int __retres1 ;

  {
  if (c_dr_pc == 1) {
    if (q_write_ev == 1) {
      __retres1 = 1;
      goto return_label;
    } else {

    }
  } else {

  }
  __retres1 = 0;
  return_label: ;/* CIL Label */ 
  return (__retres1);
}
}
void immediate_notify_threads() 
{ int tmp ;
  int tmp___0 ;

  {
  {
  tmp = is_do_write_p_triggered();
  }
  if (tmp > 0) {
    p_dw_st = 0;
  } else {

  }
  {
  tmp___0 = is_do_read_c_triggered();
  }
  if (tmp___0) {
    c_dr_st = 0;
  } else {

  }

  
}
}
void do_write_p() 
{ 

  int __NONDET;

  {
  if (p_dw_pc == 0) {
    goto DW_ENTRY;
  } else {
    if (p_dw_pc == 1) {
      goto DW_WAIT_READ;
    } else {

    }
  }
  DW_ENTRY: 
  {
  while (1 < 2) {
    while_0_continue: /* CIL Label */ ;
    if (q_free == 0) {
      p_dw_st = 2;
      p_dw_pc = 1;

      goto return_label;
      DW_WAIT_READ: ;
    } else {

    }
    {
    q_buf_0 = __NONDET;
    p_last_write = q_buf_0;
    p_num_write += 1;
    q_free = 0;
    q_write_ev = 1;
    immediate_notify_threads();
    q_write_ev = 2;
    }
  }
  {
    while_0_continue: /* CIL Label */ ;
    if (q_free == 0) {
      p_dw_st = 2;
      p_dw_pc = 1;

      goto return_label;
      DW_WAIT_READ: ;
    } else {

    }
    {
    q_buf_0 = __NONDET;
    p_last_write = q_buf_0;
    p_num_write += 1;
    q_free = 0;
    q_write_ev = 1;
    immediate_notify_threads();
    q_write_ev = 2;
    }
  }
  {
    while_0_continue: /* CIL Label */ ;
    if (q_free == 0) {
      p_dw_st = 2;
      p_dw_pc = 1;

      goto return_label;
      DW_WAIT_READ: ;
    } else {

    }
    {
    q_buf_0 = __NONDET;
    p_last_write = q_buf_0;
    p_num_write += 1;
    q_free = 0;
    q_write_ev = 1;
    immediate_notify_threads();
    q_write_ev = 2;
    }
  }
  {
    while_0_continue: /* CIL Label */ ;
    if (q_free == 0) {
      p_dw_st = 2;
      p_dw_pc = 1;

      goto return_label;
      DW_WAIT_READ: ;
    } else {

    }
    {
    q_buf_0 = __NONDET;
    p_last_write = q_buf_0;
    p_num_write += 1;
    q_free = 0;
    q_write_ev = 1;
    immediate_notify_threads();
    q_write_ev = 2;
    }
  }
  {
    while_0_continue: /* CIL Label */ ;
    if (q_free == 0) {
      p_dw_st = 2;
      p_dw_pc = 1;

      goto return_label;
      DW_WAIT_READ: ;
    } else {

    }
    {
    q_buf_0 = __NONDET;
    p_last_write = q_buf_0;
    p_num_write += 1;
    q_free = 0;
    q_write_ev = 1;
    immediate_notify_threads();
    q_write_ev = 2;
    }
  }
  while_0_break: /* CIL Label */ ;
  }
  return_label: ;/* CIL Label */ 
  
}
}
static int a_t  ;
void do_read_c() 
{ int a ;

  {
  if (c_dr_pc == 0) {
    goto DR_ENTRY;
  } else {
    if (c_dr_pc == 1) {
      goto DR_WAIT_WRITE;
    } else {

    }
  }
  DR_ENTRY: 
  {
  while (1 < 2) {
    while_1_continue: /* CIL Label */ ;
    if (q_free == 1) {
      c_dr_st = 2;
      c_dr_pc = 1;
      a_t = a;

      goto return_label;
      DR_WAIT_WRITE: 
      a = a_t;
    } else {

    }
    {
    a = q_buf_0;
    c_last_read = a;
    c_num_read += 1;
    q_free = 1;
    q_read_ev = 1;
    immediate_notify_threads();
    q_read_ev = 2;
    }
    if (p_last_write == c_last_read) {
      if (p_num_write == c_num_read) {

      } else {
        {
        assert_1 = 1;
        }
      }
    } else {
      {
      assert_2 = 1;
      }
    }
  }
  {
    while_1_continue: /* CIL Label */ ;
    if (q_free == 1) {
      c_dr_st = 2;
      c_dr_pc = 1;
      a_t = a;

      goto return_label;
      DR_WAIT_WRITE: 
      a = a_t;
    } else {

    }
    {
    a = q_buf_0;
    c_last_read = a;
    c_num_read += 1;
    q_free = 1;
    q_read_ev = 1;
    immediate_notify_threads();
    q_read_ev = 2;
    }
    if (p_last_write == c_last_read) {
      if (p_num_write == c_num_read) {

      } else {
        {
        assert_3 = 1;
        }
      }
    } else {
      {
      assert_4 = 1;
      }
    }
  }
  {
    while_1_continue: /* CIL Label */ ;
    if (q_free == 1) {
      c_dr_st = 2;
      c_dr_pc = 1;
      a_t = a;

      goto return_label;
      DR_WAIT_WRITE: 
      a = a_t;
    } else {

    }
    {
    a = q_buf_0;
    c_last_read = a;
    c_num_read += 1;
    q_free = 1;
    q_read_ev = 1;
    immediate_notify_threads();
    q_read_ev = 2;
    }
    if (p_last_write == c_last_read) {
      if (p_num_write == c_num_read) {

      } else {
        {
        assert_5 = 1;
        }
      }
    } else {
      {
      assert_6 = 1;
      }
    }
  }
  {
    while_1_continue: /* CIL Label */ ;
    if (q_free == 1) {
      c_dr_st = 2;
      c_dr_pc = 1;
      a_t = a;

      goto return_label;
      DR_WAIT_WRITE: 
      a = a_t;
    } else {

    }
    {
    a = q_buf_0;
    c_last_read = a;
    c_num_read += 1;
    q_free = 1;
    q_read_ev = 1;
    immediate_notify_threads();
    q_read_ev = 2;
    }
    if (p_last_write == c_last_read) {
      if (p_num_write == c_num_read) {

      } else {
        {
        assert_7 = 1;
        }
      }
    } else {
      {
      assert_8 = 1;
      }
    }
  }
  {
    while_1_continue: /* CIL Label */ ;
    if (q_free == 1) {
      c_dr_st = 2;
      c_dr_pc = 1;
      a_t = a;

      goto return_label;
      DR_WAIT_WRITE: 
      a = a_t;
    } else {

    }
    {
    a = q_buf_0;
    c_last_read = a;
    c_num_read += 1;
    q_free = 1;
    q_read_ev = 1;
    immediate_notify_threads();
    q_read_ev = 2;
    }
    if (p_last_write == c_last_read) {
      if (p_num_write == c_num_read) {

      } else {
        {
        assert_9 = 1;
        }
      }
    } else {
      {
      assert_10 = 1;
      }
    }
  }
  while_1_break: /* CIL Label */ ;
  }
  return_label: ;/* CIL Label */ 
  
}
}
void init_threads() 
{ 

  {
  if (p_dw_i == 1) {
    p_dw_st = 0;
  } else {
    p_dw_st = 2;
  }
  if (c_dr_i == 1) {
    c_dr_st = 0;
  } else {
    c_dr_st = 2;
  }

  
}
}
int exists_runnable_thread() 
{ int __retres1 ;

  {
  if (p_dw_st == 0) {
    __retres1 = 1;
    goto return_label;
  } else {
    if (c_dr_st == 0) {
      __retres1 = 1;
      goto return_label;
    } else {

    }
  }
  __retres1 = 0;
  return_label: ;/* CIL Label */ 
  return (__retres1);
}
}
void eval() 
{ int tmp ;
  int tmp___0 ;
  int tmp___1 ;
  int __NONDET;

  {
  {
  while (1 < 2) {
    while_2_continue: /* CIL Label */ ;
    {
    tmp___1 = exists_runnable_thread();
    }
    if (tmp___1) {

    } else {
      goto while_2_break;
    }
    if (p_dw_st == 0) {
      {
      tmp = __NONDET;
      }
      if (tmp) {
        {
        p_dw_st = 1;
        do_write_p();
        }
      } else {

      }
    } else {

    }
    if (c_dr_st == 0) {
      {
      tmp___0 = __NONDET;
      }
      if (tmp___0) {
        {
        c_dr_st = 1;
        do_read_c();
        }
      } else {

      }
    } else {

    }
  }
  {
    while_2_continue: /* CIL Label */ ;
    {
    tmp___1 = exists_runnable_thread();
    }
    if (tmp___1) {

    } else {
      goto while_2_break;
    }
    if (p_dw_st == 0) {
      {
      tmp = __NONDET;
      }
      if (tmp) {
        {
        p_dw_st = 1;
        do_write_p();
        }
      } else {

      }
    } else {

    }
    if (c_dr_st == 0) {
      {
      tmp___0 = __NONDET;
      }
      if (tmp___0) {
        {
        c_dr_st = 1;
        do_read_c();
        }
      } else {

      }
    } else {

    }
  }
  {
    while_2_continue: /* CIL Label */ ;
    {
    tmp___1 = exists_runnable_thread();
    }
    if (tmp___1) {

    } else {
      goto while_2_break;
    }
    if (p_dw_st == 0) {
      {
      tmp = __NONDET;
      }
      if (tmp) {
        {
        p_dw_st = 1;
        do_write_p();
        }
      } else {

      }
    } else {

    }
    if (c_dr_st == 0) {
      {
      tmp___0 = __NONDET;
      }
      if (tmp___0) {
        {
        c_dr_st = 1;
        do_read_c();
        }
      } else {

      }
    } else {

    }
  }
  {
    while_2_continue: /* CIL Label */ ;
    {
    tmp___1 = exists_runnable_thread();
    }
    if (tmp___1) {

    } else {
      goto while_2_break;
    }
    if (p_dw_st == 0) {
      {
      tmp = __NONDET;
      }
      if (tmp) {
        {
        p_dw_st = 1;
        do_write_p();
        }
      } else {

      }
    } else {

    }
    if (c_dr_st == 0) {
      {
      tmp___0 = __NONDET;
      }
      if (tmp___0) {
        {
        c_dr_st = 1;
        do_read_c();
        }
      } else {

      }
    } else {

    }
  }
  {
    while_2_continue: /* CIL Label */ ;
    {
    tmp___1 = exists_runnable_thread();
    }
    if (tmp___1) {

    } else {
      goto while_2_break;
    }
    if (p_dw_st == 0) {
      {
      tmp = __NONDET;
      }
      if (tmp) {
        {
        p_dw_st = 1;
        do_write_p();
        }
      } else {

      }
    } else {

    }
    if (c_dr_st == 0) {
      {
      tmp___0 = __NONDET;
      }
      if (tmp___0) {
        {
        c_dr_st = 1;
        do_read_c();
        }
      } else {

      }
    } else {

    }
  }
  while_2_break: /* CIL Label */ ;
  }

  
}
}
int stop_simulation() 
{ int tmp ;
  int __retres2 ;

  {
  {
  tmp = exists_runnable_thread();
  }
  if (tmp) {
    __retres2 = 0;
    goto return_label;
  } else {

  }
  __retres2 = 1;
  return_label: ;/* CIL Label */ 
  return (__retres2);
}
}
void start_simulation() 
{ int kernel_st ;
  int tmp ;

  {
  {
  kernel_st = 0;
  init_threads();
  }
  {
  while (1 < 2) {
    while_3_continue: /* CIL Label */ ;
    {
    kernel_st = 1;
    eval();
    tmp = stop_simulation();
    }
    if (tmp) {
      goto while_3_break;
    } else {

    }
  }
  {
    while_3_continue: /* CIL Label */ ;
    {
    kernel_st = 1;
    eval();
    tmp = stop_simulation();
    }
    if (tmp) {
      goto while_3_break;
    } else {

    }
  }
  {
    while_3_continue: /* CIL Label */ ;
    {
    kernel_st = 1;
    eval();
    tmp = stop_simulation();
    }
    if (tmp) {
      goto while_3_break;
    } else {

    }
  }
  {
    while_3_continue: /* CIL Label */ ;
    {
    kernel_st = 1;
    eval();
    tmp = stop_simulation();
    }
    if (tmp) {
      goto while_3_break;
    } else {

    }
  }
  {
    while_3_continue: /* CIL Label */ ;
    {
    kernel_st = 1;
    eval();
    tmp = stop_simulation();
    }
    if (tmp) {
      goto while_3_break;
    } else {

    }
  }
  while_3_break: /* CIL Label */ ;
  }

  
}
}
void init_model() 
{ 

  {
  q_free = 1;
  q_write_ev = 2;
  q_read_ev = q_write_ev;
  p_num_write = 0;
  p_dw_pc = 0;
  p_dw_i = 1;
  c_num_read = 0;
  c_dr_pc = 0;
  c_dr_i = 1;

  
}
}
int pc_sfifo_1() 
{ int __retres1 ;

  {
  {
  init_model();
  start_simulation();
  }
  if (assert_9 == 1 || assert_10 == 1) {
    __retres1 = 1;
  } else {
    __retres1 = 0;
  }
  return (__retres1);
}
}
