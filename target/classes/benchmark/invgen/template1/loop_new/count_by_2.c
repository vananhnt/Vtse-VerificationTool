//true
int count_by_2 {
	int i;
	i = 0;
	while (i < 100) {
     invariant: +0) <= 0;
		i = i + 2;
	}
	//assert: i == 100;
	return i;
}
