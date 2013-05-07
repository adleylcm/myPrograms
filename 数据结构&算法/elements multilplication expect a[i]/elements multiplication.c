#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
	int n,
		i;
	int* source_numbers = NULL;
	long* target_numbers = NULL;
	long* prefix_numbers = NULL;
	long* suffix_numbers = NULL;

	printf("Please input a integer number n(n > 1): \n");

	while(EOF != (scanf("%d", &n)))
	{
		source_numbers = (int*)malloc(n * sizeof(int));
		target_numbers = (long*)malloc(n * sizeof(long));
		prefix_numbers = (long*)malloc(n * sizeof(long));
		suffix_numbers = (long*)malloc(n * sizeof(long));

		void print_result(long*, int);

		printf("Please enter %d integers\n", n);

		i = 0;
		while(i++ < n)
			scanf("%d", &source_numbers[i - 1]);

		/*
		*the first method
		*/
		int j,
			k;
		for(i = 0; i < n; i++)
		{
			target_numbers[i] = 1;
			for(j = 0; j < i; j++)
				target_numbers[i] *= source_numbers[j];
			for(k = n - 1; k > i; k--)
				target_numbers[i] *= source_numbers[k];
		}

		printf("the result of first method:\n");
		print_result(target_numbers, n);

		//reset the target_numbers
		free(target_numbers);
		target_numbers = (long*)malloc(n * sizeof(long));

		/*
		* the second method
		*/
		prefix_numbers[0] = 1;
		for(i = 1; i < n; i++)
			prefix_numbers[i] = prefix_numbers[i - 1] * source_numbers[i - 1];

		suffix_numbers[0] = 1;
		for(i = 1; i < n; i++)
			suffix_numbers[i] = suffix_numbers[i - 1] * source_numbers[n - i]; 

		for(i = 0; i < n; i++)
			target_numbers[i] = prefix_numbers[i] * suffix_numbers[n - i - 1];

		printf("the result of second method:\n");
		print_result(target_numbers, n);

		free(source_numbers);
		free(target_numbers);
		free(prefix_numbers);
		free(suffix_numbers);

		printf("Please input a integer number n(n > 1): \n");
	}

	return 0;
}
void print_result(long* target_numbers, int n)
{
	int i;

	printf("%ld...", target_numbers[0]);
	for(i = 1; i < n - 1; i++)
		printf("%ld...", target_numbers[i]);
	printf("%ld\n", target_numbers[n - 1]);
	printf("\n");
}