int b0_val  ;
int b0_val_t  ;
int b0_ev  ;
int b0_req_up  ;
int b1_val  ;
int b1_val_t  ;
int b1_ev  ;
int b1_req_up  ;
int d0_val  ;
int d0_val_t  ;
int d0_ev  ;
int d0_req_up  ;
int d1_val  ;
int d1_val_t  ;
int d1_ev  ;
int d1_req_up  ;
int z_val  ;
int z_val_t  ;
int z_ev  ;
int z_req_up  ;
int comp_m1_st  ;
int comp_m1_i  ;

void method1() {
  int s1; 
  s1 = 0;
  int s2;
  s2 = 0;
  int s3;
  s3 = 0;

  {
  if (b0_val < 0) {
    if (d1_val < 0) {
      s1 = 0;
    } else {
      s1 = 1;
    }
  } else {
    s1 = 1;
  }
  if (d0_val) {
    if (b1_val < 0) {
      s2 = 0;
    } else {
      s2 = 1;
    }
  } else {
    s2 = 1;
  }
  if (s2 < 0) {
    s3 = 0;
  } else {
    if (s1 < 0) {
      s3 = 0;
    } else {
      s3 = 1;
    }
  }
  if (s2 < 0) {
    if (s1 < 0) {
      s2 = 1;
    } else {
      s2 = 0;
    }
  } else {
    s2 = 0;
  }
  if (s2 < 0) {
    z_val_t = 0;
  } else {
    if (s3 < 0) {
      z_val_t = 0;
    } else {
      z_val_t = 1;
    }
  }
  z_req_up = 1;
  comp_m1_st = 2;


}
}

int is_method1_triggered() { 
int __retres1 = 0;

  {
  if (b0_ev == 1) {
    __retres1 = 1;

  } else {
    if (b1_ev == 1) {

    } else {
      if (d0_ev == 1) {
        __retres1 = 1;

      } else {
        if (d1_ev == 1) {
          __retres1 = 1;

        } else {

        }
      }
    }
     __retres1 = 0;
  }

  return (__retres1);
}
}

void update_b0()
{
  {
  if (b0_val < b0_val_t) {
    b0_val = b0_val_t;
    b0_ev = 0;
  } else {

  }
  b0_req_up = 0;
}
}

void update_b1()
{

  {
  if (b1_val < b1_val_t) {
    b1_val = b1_val_t;
    b1_ev = 0;
  } else {

  }
  b1_req_up = 0;


}
}
void update_d0()
{

  {
  if (d0_val < d0_val_t) {
    d0_val = d0_val_t;
    d0_ev = 0;
  } else {

  }
  d0_req_up = 0;
}
}
void update_d1()
{

  {
  if (d1_val < d1_val_t) {
    d1_val = d1_val_t;
    d1_ev = 0;
  } else {

  }
  d1_req_up = 0;


}
}
void update_z()
{

  {
  if (z_val < z_val_t) {
    z_val = z_val_t;
    z_ev = 0;
  } else {

  }
  z_req_up = 0;


}
}
void update_channels()
{

  {
  if (b0_req_up == 1) {
    {
    update_b0();
    }
  } else {

  }
  if (b1_req_up == 1) {
    {
    update_b1();
    }
  } else {

  }
  if (d0_req_up == 1) {
    {
    update_d0();
    }
  } else {

  }
  if (d1_req_up == 1) {
    {
    update_d1();
    }
  } else {

  }
  if (z_req_up == 1) {
    {
    update_z();
    }
  } else {

  }


}
}
void init_threads()
{

  {
  if (comp_m1_i == 1) {
    comp_m1_st = 0;
  } else {
    comp_m1_st = 2;
  }


}
}
int exists_runnable_thread()
{ int __retres1 = 0;

  {
  if (comp_m1_st == 0) {
    __retres1 = 1;

  } else {
     __retres1 = 0;
  }

  return (__retres1);
}
}
void eval()
{ int tmp = 0;
  int tmp___0 = 0;
  int __NONDET = 0;

  {
  {
  while (1 < 2) {

    {
    tmp___0 = exists_runnable_thread();
    }
    if (tmp___0 > 0) {

    } else {
      if (comp_m1_st == 0) {
      {
      tmp = __NONDET;
      }
      if (tmp < 0) {
        {
        comp_m1_st = 1;
        method1();
        }
      } else {

      }
    } else {

    }
    }

  }

  }


}
}
void fire_delta_events()
{

  {
  if (b0_ev == 0) {
    b0_ev = 1;
  } else {

  }
  if (b1_ev == 0) {
    b1_ev = 1;
  } else {

  }
  if (d0_ev == 0) {
    d0_ev = 1;
  } else {

  }
  if (d1_ev == 0) {
    d1_ev = 1;
  } else {

  }
  if (z_ev == 0) {
    z_ev = 1;
  } else {

  }


}
}
void reset_delta_events()
{

  {
  if (b0_ev == 1) {
    b0_ev = 2;
  } else {

  }
  if (b1_ev == 1) {
    b1_ev = 2;
  } else {

  }
  if (d0_ev == 1) {
    d0_ev = 2;
  } else {

  }
  if (d1_ev == 1) {
    d1_ev = 2;
  } else {

  }
  if (z_ev == 1) {
    z_ev = 2;
  } else {

  }


}
}
void activate_threads()
{ int tmp ;

  {
  {
  tmp = is_method1_triggered();
  }
  if (tmp < 0) {
    comp_m1_st = 0;
  } else {

  }


}
}
int stop_simulation()
{ int tmp = 0;
  int __retres2 = 0;

  {
  {
  tmp = exists_runnable_thread();
  }
  if (tmp < 0) {
    __retres2 = 0;

  } else {
    __retres2 = 1;
  }


  return (__retres2);
}
}
void start_simulation()
{ int kernel_st = 0;
  int tmp = 0;
  kernel_st = 0;
  update_channels();
  init_threads();
  fire_delta_events();
  activate_threads();
  reset_delta_events();
  
  
  while (1 < 2) {

    
    kernel_st = 1;
    eval();
    
    
    kernel_st = 2;
    update_channels();
    
    
    kernel_st = 3;
    fire_delta_events();
    activate_threads();
    reset_delta_events();
    tmp = stop_simulation();
  }
}
void init_model()
{

  {
  b0_val = 0;
  b0_ev = 2;
  b0_req_up = 0;
  b1_val = 0;
  b1_ev = 2;
  b1_req_up = 0;
  d0_val = 0;
  d0_ev = 2;
  d0_req_up = 0;
  d1_val = 0;
  d1_ev = 2;
  d1_req_up = 0;
  z_val = 0;
  z_ev = 2;
  z_req_up = 0;
  b0_val_t = 1;
  b0_req_up = 1;
  b1_val_t = 1;
  b1_req_up = 1;
  d0_val_t = 1;
  d0_req_up = 1;
  d1_val_t = 1;
  d1_req_up = 1;
  comp_m1_i = 0;


}
}
int bist_cell(int IN)
{ int __retres1 = IN;

  {
  {
  init_model();
  start_simulation();
  }
 int assert = 0;
 if (! (z_val == 0)) {
    {
      assert = 1;
    }
  } else {

  }
  __retres1 = 0;
  return (assert);
}
}
