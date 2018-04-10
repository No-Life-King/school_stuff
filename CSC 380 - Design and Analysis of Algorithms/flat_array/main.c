#include <stdio.h>
#include <stdbool.h>

#include "functions.h"

int main(int argc, char **argv) {
	char **accts = read_in_accounts(2000000,
		 "D:\\Devel\\school_stuff\\CSC 380 - Design and Analysis of Algorithms\\username_generator\\10M_logins.txt");

	char *usn;
	int num_accts = 10000;
	int array_size = 2;

	for (int a=1; a<100; a++) {

		User **users = malloc(sizeof(User *) * array_size);
		for (int x=0; x<num_accts; x++) {
			if (x == array_size) {
				array_size *= 2;
				User **new_users = malloc(sizeof(User *) * array_size);
				if (new_users == NULL) {
					fprintf(stderr, "Error: Failed to malloc new_users array of size %u", sizeof(User *) * array_size);
					return 0;
				}

				for (int y=0; y<x; y++) {
					new_users[y] = users[y];
				}
				free(users);
				users = new_users;
			}
			User *new_user = malloc(sizeof(User));
			if (new_user == NULL) {
				fprintf(stderr, "Error: Failed to malloc new_user");
				return 0;
			}
			usn = accts[x];
			new_user->name = lowercase(usn);
			new_user->password = get_password_pointer(usn);
			users[x] = new_user;
		}

		double start = getTime();
		char *username = lowercase(accts[num_accts/2]);
		char *password = get_password_pointer(accts[num_accts/2]);
		bool authenticated = false;
		for (int w=0; w<num_accts; w++) {
			if (compare(username, users[w]->name) == 0) {
				if (compare(password, users[w]->password) == 0) {
					authenticated = true;
				} else {
					fprintf(stderr, "Info mismatch for %s\n", username);
				}
			}

		}
		double finish = getTime();
		printf("%i\t%f\n", num_accts, finish-start);

		free(users);
		num_accts += 10000;
	}


}


