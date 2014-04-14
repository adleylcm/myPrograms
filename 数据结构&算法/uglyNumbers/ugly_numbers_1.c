#include <stdio.h>
#include <time.h>

#define MAX_NUMBER 2048

int main(int argc, char *argv[])
{
	long ugly_numbers[MAX_NUMBER];
	ugly_numbers[0] = 1;
	int i = 1;
	int index1 = 0;
	int	index2 = 0;
	int	index3 = 0;
	int position;
	long temp;
	struct timespec tpStart,
					tpEnd;

	void count_time(struct timespec, struct timespec);

	clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &tpStart);
	for(; i < MAX_NUMBER; i++)
	{
		temp = (2 * ugly_numbers[index1]) < (3 * ugly_numbers[index2]) ?
			   (2 * ugly_numbers[index1]) : (3 * ugly_numbers[index2]);
		temp = temp < (5 * ugly_numbers[index3]) ?
			   temp : (5 * ugly_numbers[index3]);

		if(ugly_numbers[i] == 2 * ugly_numbers[index1])
			index1++;
		if(ugly_numbers[i] == 3 * ugly_numbers[index2])
			index2++;
		if(ugly_numbers[i] == 5 * ugly_numbers[index3])
			index3++;
	}
	clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &tpEnd);
	count_time(tpStart, tpEnd);

	//printf("Please input a integer number, 
	//		 we'll find the ugly number in that position(number < 2048)\n");
	/*
	while(EOF != (scanf("%d", &position)))
	{
		printf("the ugly number is %ld\n", ugly_numbers[position]);
		printf("Please input a integer number, 
		we'll find the ugly number in that position(number < 2048)\n");
	}
	*/
	return 0;
}

void count_time(struct timespec start, struct timespec end)
{
	struct timespec countTime;
	long duration;
	const long NANOSECOND = 1000000000l;

	if(0 > (end.tv_nsec - start.tv_nsec))
	{
		countTime.tv_sec = end.tv_sec - start.tv_sec - 1;
		countTime.tv_nsec = NANOSECOND + end.tv_nsec - start.tv_nsec;
	}
	else
	{
		countTime.tv_sec = end.tv_sec - start.tv_sec;
		countTime.tv_nsec = end.tv_nsec - start.tv_nsec;
	}
	duration = NANOSECOND * (int)countTime.tv_sec + countTime.tv_nsec;
	printf("compute the ugly numbers took %ld nsec \n",duration); 
}