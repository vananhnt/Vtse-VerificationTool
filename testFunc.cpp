int f(int a, int b) {
	return a + b;
}
int main(){
	int sum = 0;
	int a = 0;
	int b = 1;
	sum = sum + f(a, b);

	a = 9;
	b = 8;
	sum = sum + f(a, b);
	return sum;
}
