#include <stdio.h>
#include <mem.h>
#include <stdlib.h>
#include <windows.h>
#include <stdbool.h>

#include "user.h"

/*
 * In a typical UNCW user name, there are three lowercase letters (the user's initials)
 * followed by four random numbers. As such, there are 10^4 * 26^3 possible username
 * combinations.
 */
#define USERNAMES   (175760000/4)

int perfectHashFunction(const char username[]);
int power(int base, int exponent);
double getTime();
void authenticate(char username[], char password[], const struct user *users);

void main() {

    double start = getTime();

    struct user phil;
    strcpy(phil.name, "pws1994");
    strcpy(phil.password, "password");

    struct user *users;
    static struct user test_login[1001];

    users = malloc(USERNAMES * sizeof(phil));

    users[perfectHashFunction(phil.name)] = phil;
    test_login[1000] = phil;

    FILE *fp;
    fp = fopen("D:\\Devel\\school_stuff\\CSC 380 - Design and Analysis of Algorithms\\username_generator\\100k", "r");
    char *line = NULL;
    size_t len = 0;
    ssize_t read;
    int lineNumber = 0;

    while ((read = getline(&line, &len, fp)) != EOF) {

        struct user account;
        int x;

        for (x=0; x<8; x++) {
            account.name[x] = line[x];
        }

        account.name[7] = '\0';
        int y = 0;

        while (line[x] != '\n') {
            account.password[y] = line[x];
            x++;
            y++;
        }

        account.password[y] = '\0';

        if (lineNumber < 1000) {
            test_login[lineNumber] = account;
            lineNumber++;
        }

        users[perfectHashFunction(account.name)] = account;
    }

    double authStartTime = getTime();

    for (int x=0; x<1001; x++) {
        authenticate(test_login[x].name, test_login[x].password, users);
    }

    double authEndTime = getTime();
    printf("Authenticated 1001 users in %f ms.\n", (authEndTime - authStartTime) * 1000);

    double end = getTime();
    printf("Took %f ms total.", (end - start) * 1000);
    free(users);
}

void authenticate(char username[], char password[], const struct user *users) {
    bool logged_in = true;
    int x = 0;
    char user_pass[30];
    int user_hash = perfectHashFunction(username);
    strcpy(user_pass, users[user_hash].password);

    if (strcmp(username, users[user_hash].name) != 0) {
        fprintf(stderr, "Error: Wrong user retrieved.\nWanted: %s, Got: %s", username, users[user_hash].name);
        return;
    }

    while (password[x] != '\0') {
        if (user_pass[x] != password[x]) {
            logged_in = false;
            break;
        }
        x++;
    }

    if (logged_in) {
        // printf("%s has logged in!\n", username);
    } else {
        printf("Login failed for %s.\nIn Table: %s, Received: %s\n", username, users[user_hash].password, password);
    }
}

int perfectHashFunction(const char username[]) {
    int letterPortion = 0;
    int exp = 2;

    for (int x=0; x<3; x++) {
        int val = username[x] - 97;
        letterPortion += power(26, exp) * val;
        exp--;
    }

    char numberPortion[4];

    for (int x=0; x<=3; x++) {
        numberPortion[x] = username[x+3];
    }

    return (letterPortion*10000 + atoi(numberPortion))/4;
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

double getTime() {
    LARGE_INTEGER t, f;
    QueryPerformanceCounter(&t);
    QueryPerformanceFrequency(&f);
    return (double)t.QuadPart/(double)f.QuadPart;
}