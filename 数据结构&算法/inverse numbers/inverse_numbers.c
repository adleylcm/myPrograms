#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//#define MAX_SIZE 10
#define MAX_SIZE 100000
#define MAX  0x7FFFFFFF

int numbers[MAX_SIZE];
long long totalCount;


int 
main(int argc, char const *argv[])
{
	int read_numbers(char * sourceFile);
	int merge_count(int *numbers, int begin, int end);

	totalCount = 0;
	//IntegerArray test
	char * source = "/home/lzl/test/IntegerArray.txt";
	int totalNumbers = read_numbers(source);

	merge_count(numbers, 0, totalNumbers - 1);


	printf("%d\n", totalNumbers);

	printf("\n\n%lld \n", totalCount);

	return 0;
}

int 
read_numbers(char * sourceFile)
{
	FILE *fptr = NULL;
	char oneLine[9];
	char charNumebr[9];
	int index = 0;

	if(NULL == (fptr = fopen(sourceFile, "r")))
	{
		fprintf(stderr, "Cannot open the input file\n");
		exit(0);
	}	

	fgets(oneLine, 9, fptr);
	while(!feof(fptr))
	{
		strncpy(charNumebr, oneLine, strlen(oneLine) - 1);
		charNumebr[strlen(oneLine) - 1] = 0;
		numbers[index] = atoi(charNumebr);
		index++;

		fgets(oneLine, 9, fptr);
	}

	if(-1 == fclose(fptr))
	{
		printf("The input file failure to close\n");
		exit(0);
	}

	return index;
}


int
merge_count(int *numbers, int begin, int end)
{
	int merge(int *, int, int, int);
	int middle;

	if(begin < end)
	{
		middle = (end + begin) / 2;
		merge_count(numbers, begin, middle);
		merge_count(numbers, middle + 1, end);
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
			if(i < n1)
			{
				totalCount += (middle - begin + 1 - i);
			}
			continue;
		}
	}

	free(numbers1);
	free(numbers2);

	return 0;
}