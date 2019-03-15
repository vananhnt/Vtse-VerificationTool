//false
int main() {
	int x, y;
    x = 0;
    y = 500000;
    while(x < 1000000) {
		if (x < 500000) {
		    x = x + 1;
		} else {
		    x = x + 1;
		    y = y + 1;
		}
    }
   // __VERIFIER_assert(y!=x);
    return x - y;
}