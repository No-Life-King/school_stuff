#include <stdio.h>
#include <windows.h>
#include "bst.h"

// set the number of accounts to be read in and inserted into the tree here
#define NUM_ACCTS   5000000
#define STEP		10000

int str_len(const char *string);
char **read_in_accounts(int num_to_read);
char *lowercase(const char *username);
char *get_password_pointer(char *usn);
double getTime();

void main() {
    // read in the specified number of accounts from a file
    char **accts = read_in_accounts(NUM_ACCTS);

	// initialize the binary search tree
	char *usn = accts[0];
	char *pass = get_password_pointer(usn);
	BST *tree = init_tree(lowercase(usn), pass);

	double start, finish;
	start = getTime();
	// add the specified number of accounts to the binary search tree
	for (int x=1; x < NUM_ACCTS; x++) {
		usn = accts[x];
		add(tree, lowercase(usn), get_password_pointer(usn));
	}

	finish = getTime();
	// you can print the tree (in sorted order) if the node count is low enough to be printed
	//print_tree(tree);

	printf("%i\t%i\n", tree->count, tree->height);
	//printf("\nTotal Insertion Time: %f s\n", finish-start);


    /*
    char name[] = "Jemimah.Lara";
    TreeNode *node = lookup(tree, lowercase(name));
    if (node != NULL) {
    	printf("Found %s. His/her password is %s", node->username, node->password);
    } else {
    	puts("Lookup Failed.");
    }
    */

}

/**
 * Accepts a line from the 'accts' array and returns a pointer to the password.
 * @param usn A tab separated line that contains a username and password pair.
 * @return A pointer to the password.
 */
char *get_password_pointer(char *usn) {
    int i = 0;
    while (usn[i] != '\t') {
        i++;
    }
    return &usn[i+1];
}

/**
 * Read lines containing login information into a jagged 2D array. This should use about the smallest amount of memory
 * possible without compressing data.
 * @param num_to_read The number of accounts to read in from the file.
 * @return A pointer to an array of char array pointers.
 */
char **read_in_accounts(int num_to_read) {

    // open file
    FILE *fp;
    fp = fopen("D:\\Devel\\school_stuff\\CSC 380 - Design and Analysis of Algorithms\\username_generator\\10M_logins.txt", "r");

    // initialize array of accounts
    char **lines;
    lines = (char **) malloc(sizeof(char *) * num_to_read);

    // set up line reading variables
    char *line = NULL;
    size_t len = 0;
    ssize_t read;
    int lineNumber = 0;

    // read lines into the accounts array until you have enough or hit the end of the file
    while ((read = getline(&line, &len, fp)) != EOF && lineNumber < num_to_read) {

        // define the size of the line
        lines[lineNumber] = (char *) malloc((size_t) read);

        // copy the contents of the line into the array
        int i=0;
        while (i<read-1) {
            lines[lineNumber][i] = line[i];
            i++;
        }

        // replace newline with null character
        lines[lineNumber][i] = '\0';
        lineNumber++;
    }

    return lines;
}

/**
 * Count the number of characters in a string, excluding the null character.
 * @param string A string whose characters should be counted.
 * @return The number of characters in the string.
 */
int str_len(const char *string) {
    int i = 0;
    while (string[i] != '\0') {
        i++;
    }

    return i;
}

/**
 * Accepts a string and converts all uppercase letters to lowercase. Allocates space for a new string rather than
 * modifying it in-place.
 * @param username The string to be converted to lowercase.
 * @return A lowercase version of the string.
 */
char *lowercase(const char *username) {

    // allocate space just large enough for the lowercased username
    char *lc = malloc((size_t) str_len(username));

    // iterate through the username copying the lowercased version to the new username string
    int i = 0;
    char current = username[i];
    while (current != '\t' && current != '\0') {

        // if capital letter, convert to lower before copying
        if (64 < current && current < 91) {
            current += 32;
        }

        lc[i] = current;
        i++;
        current = username[i];
    }

    lc[i++] = '\0';
    return lc;
}

/**
 * Return the current system up-time in seconds to the nearest microsecond.
 */
double getTime() {
    LARGE_INTEGER t, f;
    QueryPerformanceCounter(&t);
    QueryPerformanceFrequency(&f);
    return (double)t.QuadPart/(double)f.QuadPart;
}
