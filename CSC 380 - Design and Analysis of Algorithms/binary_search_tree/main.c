#include <stdio.h>
#include "bst.h"

#define NUM_ACCTS   500000

int str_len(const char *string);
char **read_in_accounts(int num_to_read);
char *lowercase(const char *username);

void main() {

    char **accts = read_in_accounts(NUM_ACCTS);

    printf("file read finished");

    BST *tree = init_tree(lowercase(accts[0]), accts[1]);

    for (int x=1; x<NUM_ACCTS; x++) {
        add(tree, lowercase(accts[2*x]), accts[2*x+1]);
        free(accts[2*x]);
        free(accts[2*x+1]);
    }


    //print_tree(tree);

    printf("\n%i\n", tree->count);
    printf("\n%i\n", tree->height);
}

char **read_in_accounts(int num_to_read) {
    FILE *fp;
    fp = fopen("D:\\Devel\\school_stuff\\CSC 380 - Design and Analysis of Algorithms\\username_generator\\10M_logins.txt", "r");
    char *line = NULL;
    char **lines;
    lines = (char **)malloc(sizeof(char *) * num_to_read * 2);
    size_t len = 0;
    ssize_t read;
    int lineNumber = 0;

    while ((read = getline(&line, &len, fp)) != EOF && lineNumber < num_to_read) {

        int len_usn = 0;
        while (line[len_usn] != '\t') {
            len_usn++;
        }

        lines[lineNumber * 2] = (char *) malloc((size_t) len_usn+1);
        lines[lineNumber * 2][len_usn] = '\0';

        int i = 0;
        while (i < len_usn) {
            lines[lineNumber * 2][i] = line[i];
            i++;
        }

        int len_pass = read - len_usn - 1;
        lines[lineNumber * 2 + 1] = (char *) malloc((size_t) len_pass);
        int j = 0;
        len_usn++;
        while (len_usn < read-1) {
            lines[lineNumber * 2 + 1][j] = line[len_usn];
            len_usn++;
            j++;
        }

        lines[lineNumber*2+1][len_pass-1] = '\0';
        lineNumber++;
    }

    return lines;
}

int str_len(const char *string) {
    int i = 0;
    while (string[i] != '\0') {
        i++;
    }

    return i;
}

char *lowercase(const char *username) {
    char *lc = malloc((size_t) str_len(username));
    int i = 0;
    char current = username[i];
    while (current != '\0') {
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