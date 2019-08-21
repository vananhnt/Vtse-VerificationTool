
//pre condition (1 <= n && n <= 1000)
int gauss_sum(int n) {
    int sum, i;
    sum = 0;
    i = 1;
    while (i <= n) {
    	sum = sum + i;
    	i = i + 1;
    }
//  assert: 2*sum == n*(n+1);
    return 0;
}