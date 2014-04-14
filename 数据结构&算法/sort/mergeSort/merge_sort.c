#include <stdio.h>
#include <stdlib.h>

#define MAX  0x7FFFFFFF

int
main(int argc, char const *argv[])
{
	int* numbers;
	int length = 0;
	int i = 0;

	int merge_sort(int *, int, int);
	int merge(int *, int, int, int);

	while(EOF != scanf("%d", &length))
	{
		numbers = (int*)malloc(length * sizeof(int));
		for(i = 0; i < length; i++)
			scanf("%d", &numbers[i]);

		merge_sort(numbers, 0, length - 1);
		
		printf("%d", numbers[0]);
		for(i = 1; i < length; i++)
			printf(" <= %d", numbers[i]);
		printf("\n");

		free(numbers);
	}

	return 0;
}

int
merge_sort(int *numbers, int begin, int end)
{
	int merge(int *, int, int, int);
	int middle;

	if(begin < end)
	{
		middle = (end + begin) / 2;
		merge_sort(numbers, begin, middle);
		merge_sort(numbers, middle + 1, end);
		merge(numbers, begin, middle, end);
	}

	return 0;
}

int
merge(int *numbers, int begin, int middle, int end)
{
	int n1 = middle - begin + 1;
	int n2 = end - middle;
	int* numbers1 = (int*)malloc((n1 + 1) * sizeof(int));
	int* numbers2 = (int*)malloc((n2 + 1) * sizeof(int));
	int i,
		j,
		k;

	for(i = 0; i < n1; i++)
		numbers1[i] = numbers[begin + i];
	numbers1[i] = MAX;
	for(i = 0; i < n2; i++)
		numbers2[i] = numbers[middle + i + 1];
	numbers2[i] = MAX;
	
	i = 0;
	j = 0;
	for(k = begin; k < end + 1; k++)
	{
		if(numbers1[i] < numbers2[j])
		{
			numbers[k] = numbers1[i];
			i++;
			continue;
		}
		else
		{
			numbers[k] = numbers2[j];
			j++;
			continue;
		}
	}

	free(numbers1);
	free(numbers2);

	return 0;
}