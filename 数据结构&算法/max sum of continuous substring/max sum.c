#include <stdio.h>
#include <stdlib.h>

#define MAX(a, b) ((a)>(b)?(a):(b))

int main(int argc, char const *argv[])
{
	int n,
		i,
		current_max,
		max;
	int* numbers;

	printf("Please input the integer n(n > 0):\n");
	while(EOF != (scanf("%d", &n)))
	{
		numbers = (int*)malloc(n * sizeof(int));
		i = 0;

		while(i++ < n)
			scanf("%d", &numbers[i - 1]);

		printf("The computing numbers are : %d", numbers[0]);
		for(i = 1; i < n; i++)
			printf(",%d", numbers[i]);
		printf("\n");

		current_max = numbers[0];
		max = current_max;

		/*
		*the first method: sum first
		*/
		for(i = 1; i < n; i++)
		{
			current_max += numbers[i];
			max = MAX(max, current_max);
			max = MAX(max, numbers[i]);

			if(0 > current_max)
			{
				if((i + 1) != (n - 1))
					current_max = 0;
				else
					current_max = numbers[i];
			}
		}
		printf("the restult of the first method is : %d\n", max);

		/*
		*the second method: compare first
		*/
		max=numbers[0];       
		current_max=0;  
		for(i=0;i<n;i++)  
		{  
    		if(current_max>=0)   
        		current_max+=numbers[i];  
    		else     
        		current_max=numbers[i];  
    		if(current_max>max)  
        		max=current_max;  
		}  

		printf("the restult of the second method is : %d\n", max);

		free(numbers);

		printf("Please input the integer n(n > 0):\n");
	}

	return 0;
}