int inv05()
{

	int x = 0;
	int y = 0;

	int j = 0;
	int i = 0;
    int flag = 1

	while(1 > 0)
	{
    invariant: x = y;
	  x++;
	  y++;
	  i+=x;
	  j+=y;
	  if(flag != 0)  j+=1;
	} 
//	/assert(j>=i);
	return j - i;
}