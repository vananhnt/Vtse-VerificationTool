
int drift_tenth_true_unreach_call()
{
  float tick = 1 / 10;
  float time = 0;
  int i;

  for (i = 0; i < 10; i++) {
    time += tick;
  }
  //__VERIFIER_assert(time != 1.0);
  return time;
}
