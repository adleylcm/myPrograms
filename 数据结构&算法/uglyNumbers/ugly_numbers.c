#include <stdio.h>

#define MAX_NUMBER 2048

long min(long a, long b, long c)
{
	long temp = a < b ? a : b;
	return temp < c ? temp : c;
}

int main(int argc, char *argv[])
{
	long ugly_numbers[MAX_NUMBER];
	ugly_numbers[0] = 1;
	int i = 1;
	int index1 = 0;
	int	index2 = 0;
	int	index3 = 0;
	int position;

	long min(long, long, long);

	for(; i < MAX_NUMBER; i++)
	{
		ugly_numbers[i] = min(2 * ugly_numbers[index1],
							  3 * ugly_numbers[index2],
							  5 * ugly_numbers[index3]);

		if(ugly_numbers[i] == 2 * ugly_numbers[index1])
			index1++;
		if(ugly_numbers[i] == 3 * ugly_numbers[index2])
			index2++;
		if(ugly_numbers[i] == 5 * ugly_numbers[index3])
			index3++;
	}

	printf("Please input a integer number, 
		   we'll find the ugly number in that position(number < 2048)\n");
	
	while(EOF != (scanf("%d", &position)))
	{
		printf("the ugly number is %ld\n", ugly_numbers[position]);
		printf("Please input a integer number, 
			   we'll find the ugly number in that position(number < 2048)\n");
	}
	
	return 0;
}