#include <stdio.h>
#include <stdlib.h>

int 
main(int argc, char const *argv[])
{
	int* numbers = NULL;
	int length = 0;
	int i = 0;
	int max_number = 0;

	int counting_sort(int *numbers, int length, int max_number);

	while(EOF != scanf("%d", &length))
	{
		scanf("%d", &max_number);
		if (NULL == (numbers = (int*)malloc(length * sizeof(int))))
		{
			return 0;
		}
		
		for(i = 0; i < length; i++)
			scanf("%d", &numbers[i]);
		
		counting_sort(numbers, length, max_number + 1);
		
		printf("%d", numbers[0]);
		for(i = 1; i < length; i++)
			printf(" <= %d", numbers[i]);
		printf("\n");

		free(numbers);
	}

	return 0;	
}

int
counting_sort(int *numbers, int length, int max_number)
{
	int *count = NULL;
	int *target = NULL;
	int i = 0;

	if (NULL == (count = (int *)calloc(max_number, sizeof(int))) ||
		NULL == (target = (int *)calloc(length, sizeof(int))))
	{
		exit(0);
	}

	for(i = 0; i < length; i++)
		count[numbers[i]] += 1;
	for(i = 1; i < max_number; i++)
		count[i] += count[i - 1];

	for(i = length - 1; i >= 0; i--)
	{
		target[count[numbers[i]] - 1] = numbers[i];
		count[numbers[i]]--;
	}

	for(i = 0; i < length; i++)
		numbers[i] = target[i];

	free(count);
	free(target);

	return 1;
}