#include <stdio.h>
#include <stdbool.h>
#include <windows.h>

#pragma once

char **read_in_accounts(int num_to_read, char *file_loc);
char *get_password_pointer(char *line);
char *lowercase(const char *username);
bool compare_passwords(char *p1, char *p2);
int str_len(const char *string);
double getTime();


typedef struct user {
    char *name;
    char *password;
} User;


/**
 * Read lines containing login information into a jagged 2D array. This should use about the smallest amount of memory
 * possible without compressing data.
 * @param num_to_read The number of accounts to read in from the file.
 * @return A pointer to an array of char array pointers.
 */
char **read_in_accounts(int num_to_read, char *file_loc) {

    // open file
    FILE *fp;
    fp = fopen(file_loc, "r");

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
 * Take a tab separated line and return a pointer to the password.
 * @param line A pointer to a line consisting of a username followed by a password.
 * @return A pointer to the password.
 */
char *get_password_pointer(char *line) {
    int i = 0;
    while (line[i] != '\t') {
        i++;
    }

    return &line[i+1];
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
 * Compares two passwords returning true if they are identical. Otherwise returns false.
 */
bool compare_passwords(char *p1, char *p2) {
	int index = 0;
	while (p1[index] != '\0' && p2[index] != '\0') {
		if (p1[index] != p2[index]) {
			return false;
		}

		index++;
	}

	if (p1[index] == p2[index]) {
		return true;
	} else {
		return false;
	}
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
 * Returns the current system up-time in seconds to the nearest microsecond.
 */
double getTime() {
    LARGE_INTEGER t, f;
    QueryPerformanceCounter(&t);
    QueryPerformanceFrequency(&f);
    return (double)t.QuadPart/(double)f.QuadPart;
}
