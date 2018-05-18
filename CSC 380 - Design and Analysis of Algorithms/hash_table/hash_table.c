/**
* Bobby Palmer, Hash-Table
* Chaining, Load-Factor 0.8
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "functions.h"

// Data Types:
typedef struct Entry Entry;
struct Entry {
    char *username;
    char *password;
    Entry *next;
};

typedef struct {
    int size;
    int numberEntries;
    Entry **table;
} Table;

// Function prototypes:
Table *initTable(int size);
void *put(Table *table, char *username, char *password);
void resizeTable(Table *table);
unsigned long hash(char* username);
int authenticate(Table *table, char *username, char *password);
int compareString(char *s1, char *s2);

// GLobal Variables:
int startingSize = 10000;
double loadFactor = 0.8;


/**
* Initializes and returns pointer to a table of given size.
*/
Table *initTable(int size) {
    Table * newTable = (Table *) malloc(sizeof(Table));
    newTable -> size = size;
    newTable -> numberEntries = 0;
    newTable -> table = calloc(size, sizeof(Entry *));
    return newTable;
}

/**
* Adds new key-value pair to hash-table.
*/
void *put(Table *table, char *username, char *password) {
    Entry *newEntry = (Entry *) malloc(sizeof(Entry));
    newEntry -> username = username;
    newEntry -> password = password;
    newEntry -> next = NULL;

    int hashIndex = hash(username) % table -> size;

    if (table -> table[hashIndex] == NULL) {
        table -> table[hashIndex] = newEntry;
        table -> numberEntries++;
    } else {
        Entry *currentEntry = table -> table[hashIndex];
        while (currentEntry -> next != NULL) {
            currentEntry = currentEntry -> next;
        }
        currentEntry -> next = newEntry;
        table -> numberEntries++;
    }

    if ((double) table -> numberEntries / table -> size > loadFactor) {
        resizeTable(table);
    }
}


/**
* Sets pointer to table used in all functions to new resized Table
* which has had all old values rehashed into it.
*/
void resizeTable(Table *table) {
    int oldSize = table -> size;
    int newSize = oldSize * 2;

    Entry **oldTable = table -> table;
    Entry **newTable = calloc(newSize, sizeof(Entry *));

    table -> table = newTable;
    table -> size = newSize;
    table -> numberEntries = 0;

    for (int i = 0; i < oldSize; i++) {
        if (oldTable[i] != NULL) {
            Entry *entry = oldTable[i];
            put(table, entry -> username, entry -> password);

            while (entry -> next != NULL) {
                Entry *oldEntry = entry;
                entry = entry -> next;
                put(table, entry -> username, entry -> password);
                free(oldEntry);
            }
            free(entry);
        }
    }
    free(oldTable);
}


/**
* Implementation of DJB hash for strings.
*/
unsigned long hash(char* username) {
    unsigned long hash = 5381;
    int c;

    while (c = *username++) {
        hash = ((hash << 5) + hash) + c;
    }
    return hash;
}


/**
* Returns 1 if username and password are in table, if else, -1.
*/
int authenticate(Table *table, char *username, char *password) {
    int hashIndex = hash(username) % table -> size;

    Entry *currentEntry = table -> table[hashIndex];

    while (currentEntry != NULL) {
        if (compareString(currentEntry -> password, password) == 1 &&
                compareString(currentEntry -> username, username)) {
                    return 1;
        }
        currentEntry = currentEntry -> next;
    }

    return -1;
}


/**
* Compares char arrays stored at given pointers.
*/
int compareString(char *s1, char *s2) {
    int index = 0;
    while (s1[index] != '\0' && s2[index] != '\0') {
        if (s1[index] != s2[index]) {
            return -1;
        }
        index++;
	}

	if (s1[index] == s2[index]) {
		return 1;
	} else {
		return -1;
	}
}



// Tester
int main() {
    Table *table = initTable(startingSize);

    // Experiment 1:
    int experimentalSize = 10000000;
    char *filename = "C:\\Users\\ps1994\\Dropbox\\Bobby-Phil\\380 Project\\FINAL TEST CODE\\10-MIL-RANDOM.txt";
    char **data = read_in_accounts(experimentalSize, filename);
    char *line = data[0];

    int bytes = 0;
    double insertionTime = 0.0;
    double authenticationTime = 0.0;
    int step = 10000;

    printf("n\t Insertion Time (us)\t Authentication Time (us)\t Memory(MB)\n");

    for (int i = 0; i < experimentalSize; i++) {
        line = data[i];
        char *username = lowercase(line);
        char *password = get_password_pointer(line);

        bytes += (strlen(username) + strlen(password));

        double startTime = getTime() * 1000000;
        put(table, username, password);
        double endTime = getTime() * 1000000;

        insertionTime += (endTime - startTime);

        if (i % step == 0 && i > 0) {

            for (int j = (i - step); j < (i + step); j++) {
                line = data[j];
                char *username = lowercase(line);
                char *password = get_password_pointer(line);

                double startTime = getTime() * 1000000;
                authenticate(table, username, password);
                double endTime = getTime() * 1000000;

                authenticationTime += (endTime - startTime);
                free(username);
                free(password);
            }

            //printf("%d\t %f\t %f\t %d\n", i, insertionTime/step, authenticationTime/step, (table -> size * sizeof(Entry) + bytes)/1024/1024);
            printf("%i\n", (table -> size * sizeof(Entry) + bytes)/1024/1024);
            insertionTime = 0;
            authenticationTime = 0;
        }
    }
}
