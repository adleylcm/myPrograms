#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAX_BUF 1024

#define MIN(a, b) ((a)<(b)?(a):(b))

int main(int argc, char *argv[])
{
	char ori_str[MAX_BUF];

	while(NULL != gets(ori_str))
	{
    		int i = 0;
		int ori_strlen = 0;

    		while('\0' != ori_str[ori_strlen++]);

		const int trans_strlen = 2 * (ori_strlen - 1);
		char* str = (char*)malloc(trans_strlen + 1);

		if(NULL == str)
			continue;

    		//init string str[]
		str[0] = '$';
		str[1] = ori_str[0];
		for(i = 1; i < ori_strlen; i++)
		{
			str[2 * i] = '#';
			str[2 * i + 1] = ori_str[i];
		}
		str[trans_strlen] = '\0';


  		int* P = (int*) malloc(trans_strlen * sizeof(int));
  		if(NULL == P)
  			continue;
  		P[0] = 0;

  		int id = 0;
  		int mx = 0;

    		//update P[]
  		for(i = 1; i < strlen(str); i++)
  		{
  			if(i < mx)
  				P[i] = MIN(P[2 * id - i], mx - i);
  			else
  				P[i] = 0;
  				
  			while(str[i + P[i] + 1] == str[i - P[i] - 1])
  				P[i]++;

  			if (P[i] + i > mx)
  			{
  				id = i;
  				mx = P[i] + id;
  			}
  		}

    		//find the longest palindromic string
    		int max_len = 0;
    		int max_index = 0;

    		for(i = 1; i < trans_strlen; i++)
    		{
      			if(P[i] > max_len)
      			{
        			max_len = P[i];
        			max_index = i;
      			}
    		}

    		char* longest_palindrome = (char*)malloc(max_len + 1);

    		if(NULL == longest_palindrome)
      			continue;

    		if('#' == str[max_index - max_len])
      			max_len--;

    		if(0 == max_len)
    		{
      			printf("There is no palindromic\n");
      			continue;
    		}

    		int longest_palindrome_pos = (max_index - max_len)/2;

    		for (i = 0; i < max_len + 1; i++)
      			longest_palindrome[i] = ori_str[longest_palindrome_pos++];
    		longest_palindrome[++max_len] = '\0';
    		printf("The length of the longest palindromic substring is: %d\n", max_len);
    		printf("The longest palindromic substring: %s\n", longest_palindrome);

		free(str);
		free(P);
    		free(longest_palindrome);
	}

	return 0;
}
