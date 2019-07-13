int count_by_k(int k) {
	int i;
	i = 0;
	while (i < 100*k) {
		i = i + k;
	}
	assert: i == 100*k;
	return i;
}