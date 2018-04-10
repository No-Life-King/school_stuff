#include <stdio.h>
#include "functions.h"

#define TBL_SIZE	182520000

int hash(char *username);
int power(int base, int exponent);

void main(int argc, char **argv) {
	int num_users = 10000000;
	char **accts = read_in_accounts(num_users,
		 "D:\\Devel\\school_stuff\\CSC 380 - Design and Analysis of Algorithms\\username_generator\\10M_UNCW.txt");

	User **table = malloc(sizeof(User *) * TBL_SIZE);
	if (table == NULL) {
		puts("table malloc failed");
		exit(1);
	}

	for (int x=0; x<TBL_SIZE; x++) {
		table[x] = NULL;
	}

	char *usn;
	char *pass;

	double start, finish;
	start = getTime();
	for (int x=0; x<num_users; x++) {
		usn = accts[x];
		pass = get_password_pointer(usn);
		User *user = malloc(sizeof(User));
		user->name = lowercase(usn);
		user->password = pass;
		table[hash(user->name)] = user;
	}

	finish = getTime();
	printf("Took %f seconds to insert %i users.\n", finish-start, num_users);

	start = getTime();
	for (int x=0; x<num_users; x++) {
		usn = accts[x];
		int i=0;
		while (usn[i] != '\t') {
			i++;
		}

		pass = &usn[i+1];
		usn[i] = '\0';

		User *user = table[hash(accts[x])];
		if (!compare_passwords(pass, user->password)) {
			printf("Password mismatch. Got %s. Expected %s.\n", user->password, pass);
		}
	}

	finish = getTime();
	printf("Took %f seconds to authenticate %i users.\n", finish-start, num_users);


}

int hash(char *username) {
	int y = 3;
	int letterPortion = 0;
	int exp = 2;

	if (str_len(username) == 6) {
		y--;
		exp--;
		letterPortion = 17576;
	}

	for (int x=0; x<y; x++) {
		int val = username[x] - 97;
		letterPortion += power(26, exp) * val;
		exp--;
	}

	return letterPortion*10000 + atoi(&username[y]);
}

int power(int base, int exponent) {
    int power = base;

    if (exponent == 0) {
        return 1;
    }

    while (exponent > 1) {
        power *= base;
        exponent--;
    }

    return power;
}

