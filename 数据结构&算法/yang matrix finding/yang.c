#include <stdio.h>

#define MAX_BUF 1024

int main(int argc, char *argv[])
{
	int row,
		column;
	long find_number = 0;
	int numbers[MAX_BUF][MAX_BUF];
	int i,
		j;

	while(EOF != (scanf("%d %d", &row, &column)))
	{
		scanf("%ld", &find_number);
		for(i = 0; i < row; i++)
			for(j = 0; j < column; j++)
				scanf("%d", &numbers[i][j]);

		int flag = 0;
		i = 0;
		j = column - 1;

		while(row > i && 0 <= j)
		{
			if(find_number == numbers[i][j])
			{
				flag = 1;
				break;
			}
			else if(find_number < numbers[i][j])
			{
				j--;
				continue;
			}
			else
			{
				i++;
				continue;
			}
		}
		flag == 1?
			(printf("find %ld,the row index: %d,the column index %d\n", find_number, i + 1, j + 1)):
			(printf("not find %ld\n", find_number));		
	}

	return 0;
}