int count_by_3 {
	int i;
	i = 0;
	while (i < 100) {
		i = i + 2;
	}
	assert: i == 100;
	return i;
}