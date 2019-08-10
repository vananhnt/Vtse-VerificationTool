
int n = 0;
int m_pc = 0;
int E_1;
int T_1 = 0;

void start_simulations();
void transmit_1();
int isTransmit_1();

void start_simulations() {

      if (m_pc == 0) {
        goto M_ENTRY;
      } else {
        if (m_pc == 1) {
          goto M_WAIT;
        } else {

        }
      }
      M_ENTRY: ;
      {
      while (1 < 2) {
        {
        E_1 = 1;
        transmit_1();
        E_1 = 2;
        m_pc = 1;
        }
        goto return_label;
        M_WAIT: ;
          assert_1 = 1;
        } else {
        }
      }
      return_label:; /* CIL Label */
    }

void transmit_1() {
    int local;

    local = 0;
    T_1 = 1;
}

int example() {
    int m;
    start_simulations();
    m = 0;
    return m;
}