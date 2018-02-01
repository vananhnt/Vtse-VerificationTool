int a = 11;

void sum () {
	int x = 9;
	if (x) {
		goto label;
	} else {
		x = x - 1;
	}
	a = a + 1;
	label:;
	a = a + 10;
}

int main () {
	sum();
	return 0;
}
