#include <stdio.h>
#include <stdlib.h>

int
main(int argc, char const *argv[])
{
	int* numbers;
	int length = 0;
	int i = 0;

	int bubble_sort(int *numbers, int length);

	while(EOF != scanf("%d", &length))
	{
		numbers = (int*)malloc(length * sizeof(int));
		for(i = 0; i < length; i++)
			scanf("%d", &numbers[i]);
		
		bubble_sort(numbers, length);
		
		printf("%d", numbers[0]);
		for(i = 1; i < length; i++)
			printf(" <= %d", numbers[i]);
		printf("\n");

		free(numbers);
	}

	return 0;
}

int
bubble_sort(int *numbers, int length)
{
	int i,
		j,
		temp,
		flag;

	for(i = 0; i < length; i++)
	{
		flag = 0;
		for(j = length - 1; j > i; j--)
		{
			if(numbers[j] < numbers[j - 1])
			{
				temp = numbers[j];
				numbers[j] = numbers[j - 1];
				numbers[j - 1] = temp;
				flag = 1;
			}
		}
		if(0 == flag)
			break;
	}

	return 0;
}