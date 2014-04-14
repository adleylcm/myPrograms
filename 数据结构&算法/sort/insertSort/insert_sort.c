#include <stdio.h>
#include <stdlib.h>

int 
main(int argc, char const *argv[])
{
	int length = 0;
	int i = 0;

	int insert_sort(int *, int);

	while(EOF != scanf("%d", &length))
	{
		int* numbers = (int*)malloc(length * sizeof(int));

		for(i = 0; i < length; i++)
			scanf("%d", &numbers[i]);
		
		insert_sort(numbers, length);

		printf("%d", numbers[0]);
		for(i = 1; i < length; i++)
			printf(" <= %d", numbers[i]);
		printf("\n");

		free(numbers);
	}

	return 0;
}

int 
insert_sort(int *numbers, int length)
{
	int i = 0;
	int j = 0;
	int key = 0;

	for(i = 1; i < length; i++)
	{
		key = numbers[i];
		for(j = i - 1; j >= 0; j--)
		{
			if(key < numbers[j])
				numbers[j + 1] = numbers[j];
			else
				break;
		}
		numbers[j + 1] = key;
	}

	return 0;
}