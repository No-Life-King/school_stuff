#include <stdio.h>
#include <stdbool.h>
#include <windows.h>

#pragma once

int str_len(const char *string);
char **read_in_accounts(int num_to_read, char *file_loc);
char *lowercase(const char *username);
int compare(const char *usn, const char *node_usn);
bool compare_passwords(char *p1, char *p2);
char *get_password_pointer(char *line);
double getTime();

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
 * Compare two usernames to see which appears first in alphabetical order.
 * @param usn A string containing the passed-in username.
 * @param node_usn A string containing the username of the current node.
 * @return 1 if the username is lower than the node username. 0 if the usernames are equal. -1 if the username is
 * higher than the node username.
 */
int compare(const char *usn, const char *node_usn) {
    int index = 0;

    // compare until one of the ends is reached
    while (usn[index] != '\0' && node_usn[index] != '\0') {
        if (usn[index] < node_usn[index]) {
            return -1;
        } else if (usn[index] > node_usn[index]) {
            return 1;
        }
        index++;
    }

    // if both ends are reached at the the same time, the usernames are equal
    if (usn[index] == '\0' && node_usn[index] == '\0') {
        return 0;
    }

    // if the username contains the node username but is longer, it should appear after the node
    if (node_usn[index] == '\0') {
        return 1;
    }

    // if the node username contains the username but is longer, the username should appear before the node
    if (usn[index] == '\0') {
        return -1;
    }
}

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
 * Returns the current system up-time in seconds to the nearest microsecond.
 */
double getTime() {
    LARGE_INTEGER t, f;
    QueryPerformanceCounter(&t);
    QueryPerformanceFrequency(&f);
    return (double)t.QuadPart/(double)f.QuadPart;
}
