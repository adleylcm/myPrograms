#include <stdio.h>
#include <stdarg.h>

int
main(int argc, char const *argv[])
{
	int average_salary(int count_months, ...);

	int first_result = average_salary(3, 9000, 9200, 9700);
	int second_result = average_salary(1, 9700);

	printf("Three-month average wage is : %d.\n", first_result);
	printf("One-month salary is : %d.\n", second_result);

	return 0;
}

int
average_salary(int count_months, ...)
{
	va_list my_args;
	int count= count_months;
	int sum_salary = 0;

	va_start(my_args, count_months);

	while(count-- > 0)
		sum_salary += va_arg(my_args, int );

	va_end(my_args);

	return sum_salary / count_months;
} 