#include <stdio.h>
#include "functions.h"

int fast_hash(char *string, int array_size);

void main(int argc, char **argv) {

	printf("%i\n", fast_hash("string", 5000));
	char **accts = read_in_accounts(5000,
	    	 "D:\\Devel\\school_stuff\\CSC 380 - Design and Analysis of Algorithms\\username_generator\\10M_logins.txt");

	char *usn;
	for (int x=0; x<5000; x++) {
		usn = accts[x];
		printf("%i\n", fast_hash(usn, 10000));

	}

}

int fast_hash(char *string, int array_size) {

	int hash = 1333731337;
	int bytes = 0;
	int index = 0;
	int c = string[index];
	bool not = false;

	while (c) {
		if (index % 4 == 0) {
			if (not) {
				bytes = ~bytes;
				not = false;
			} else {
				not = true;
			}

			hash = bytes ^ hash;
			bytes = 0;
		}

		bytes += c;
		bytes = bytes << 8;
		index++;
		c = string[index];
	}

	if (bytes > 0) {
		hash = bytes ^ hash;
	}

	if (hash < 0) {
		hash *= -1;
	}

	return hash%array_size;
}

