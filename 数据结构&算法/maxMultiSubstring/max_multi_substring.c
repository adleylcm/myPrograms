#include <stdio.h>

#define MAX_BUF 1024

//#define MAX(a, b) (((a)>(b))?(a):(b))

int main(int argc, char *argv[])
{
	double str[MAX_BUF];
	int n,
	    i;

	double max_multi_substring(double *, const int);

	while(EOF != (scanf("%d", &n)))
	{
		for(i = 0; i < n; i++)
			scanf("%lf", &str[i]);
		printf("%lf\n", max_multi_substring(str, n));
		printf("end\n");
	}

	return 0;
}

double max_multi_substring(double *str, const int length)
{
	//double max = 1;
	//double min = 1;
	double max = str[0];
	double min = str[0];
	double current_max = str[0];
	double current_min = str[0];
	//double current_less_one = str[0];
	int i = 1;

	for (; i < length; i++)
	{
		current_max *= str[i];
		current_min *= str[i];

		if(current_max > max)
			max = current_max;
		if(current_min > max)
			max = current_min;
		if(current_max < min)
			min = current_max;
		if(current_min < min)
			min = current_min;
		if(current_max < current_min)
		{
			double temp = current_max;
			current_max = current_min;
			current_min = temp;
		}
		if(current_max < 1)
		{
			//current_less_one = MAX(current_less_one, current_max);
			//current_max = 1;
			current_max = str[i];
			if(current_max > max)
				max = current_max;
		}
			
	}
	/*
	if(max == 1)
	{
		if(length == 1)
			return current_less_one;
		else if(1 > str[0])
			return MAX(str[1], current_less_one);
	}
	*/
	return max;
}