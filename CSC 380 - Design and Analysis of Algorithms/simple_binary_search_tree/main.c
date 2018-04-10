#include <stdio.h>
#include <windows.h>

#include "bst.h"
#include "functions.h"

void case_insensitive(int num_users);
void case_sensitive(int num_users);

void main() {

	int num_users = 10000000;
	case_insensitive(num_users);


}

void case_insensitive(int num_users) {
	char **accts = read_in_accounts(num_users,
	     //"C:\\Users\\ps1994\\Dropbox\\Bobby-Phil\\380 Project\\10-MIL-RANDOM.txt"
		 "D:\\Devel\\school_stuff\\CSC 380 - Design and Analysis of Algorithms\\username_generator\\10M_UNCW.txt"
			);

	// initialize the binary search tree
	char *usn = accts[0];
	char *pass = get_password_pointer(usn);
	BST *tree = init_tree(lowercase(usn), pass);

	double start, finish;
	start = getTime();
	// add the specified number of accounts to the binary search tree
	for (int x=1; x < num_users; x++) {
		usn = accts[x];
		add(tree, lowercase(usn), get_password_pointer(usn));


		if (x%100000 == 0) {
			/*
			int step = x/100;
			start = getTime();
			for (int y=0; y<x; y+= step) {
				usn = accts[y];
				char *lc = lowercase(usn);
				TreeNode *node = lookup(tree, lc);


				if (node != NULL) {
					printf("Found %s. His/her password is %s\n", node->username, node->password);
				} else {
					printf("Lookup Failed. Expected %s.\n", usn);
				}

				if (compare_passwords(get_password_pointer(usn), node->password)) {
					//printf("Authenticated: %s", node->username);
				} else {
					printf("Authentication failure for %s: Expected %s. Got %s.", node->username, node->password, get_password_pointer(usn));
				}

			}
			*/
			printf("%f\n", (getTime()-start)*1000000/x);

		}
	}

}

void case_sensitive(int num_users) {
	char **accts = read_in_accounts(num_users,
			"C:\\Users\\ps1994\\Dropbox\\Bobby-Phil\\380 Project\\10-MIL-RANDOM.txt");

	char *usn;
	char *pass;
	int i = 0;
	while (accts[0][i] != '\t') {
		i++;
	}

	accts[0][i] = '\0';
	pass = &accts[0][i+1];
	usn = accts[0];
	BST *tree = init_tree(usn, pass);

	for (int x=1; x < num_users; x++) {
		i = 0;
		while (accts[x][i] != '\t') {
			i++;
		}

		accts[x][i] = '\0';
		pass = &accts[x][i+1];
		usn = accts[x];
		add(tree, usn, pass);

	}

}

