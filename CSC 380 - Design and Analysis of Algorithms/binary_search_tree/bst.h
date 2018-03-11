/**
 * This is an implementation of a binary search tree that adds nodes in the
 * order that they're received, without performing any sort of balancing.
 */

#include <malloc.h>
#include <stdbool.h>

#ifndef BINARY_SEARCH_TREE_BST_H
#define BINARY_SEARCH_TREE_BST_H

#endif //BINARY_SEARCH_TREE_BST_H

typedef struct tree_node {
    char *username;
    char *password;

    struct tree_node *left_child;
    struct tree_node *right_child;
} TreeNode;

typedef struct {
    int count;
    int height;
    TreeNode *root;
} BST;

BST *init_tree(char *usn, char *pass);
TreeNode *make_node(char *usn, char *pass);
bool add(BST *tree, char *usn, char *pass);
int compare(const char *u1, const char *u2);
void print_tree(BST *tree);
void print_node(TreeNode *node);

BST *init_tree(char *usn, char *pass) {
    BST *tree = malloc(sizeof(BST));
    tree->count = 1;
    tree->height = 0;
    tree->root = make_node(usn, pass);
    return tree;
}

TreeNode *make_node(char *usn, char *pass) {
    TreeNode *node = malloc(sizeof(TreeNode));
    node->username = usn;
    node->password = pass;
    node->left_child = NULL;
    node->right_child = NULL;

    return node;
}

bool add(BST *tree, char *usn, char *pass) {
    TreeNode *current = tree->root;
    int current_height = 0;

    while (true) {

        int comparison = compare(usn, current->username);
        if (comparison > 0 || comparison < 0) {
            current_height++;
            if (current_height > tree->height) {
                tree->height++;
            }
        }

        if (comparison == -1) {
            if (current->left_child == NULL) {
                current->left_child = make_node(usn, pass);
                tree->count++;

                //printf("%s is now the left child of %s\n", usn, current->username);

                return true;
            } else {
                current = current->left_child;
            }
        } else if (comparison == 1) {
            if (current->right_child == NULL) {
                current->right_child = make_node(usn, pass);
                tree->count++;

                //printf("%s is now the right child of %s\n", usn, current->username);

                return true;
            } else {
                current = current->right_child;
            }
        } else {
            return false;
        }

    }
}

int compare(const char *u1, const char *u2) {
    int index = 0;

    while (u1[index] != '\0' && u2[index] != '\0') {
        if (u1[index] < u2[index]) {
            return -1;
        } else if (u1[index] > u2[index]) {
            return 1;
        }
        index++;
    }

    if (u1[index] == '\0' && u2[index] == '\0') {
        return 0;
    }
    if (u2[index] == '\0') {
        return 1;
    }
    if (u1[index] == '\0') {
        return -1;
    }
}

void print_tree(BST *tree) {
    if (tree->root != NULL) {
        print_node(tree->root);
    } else {
        puts("Binary search tree empty.");
    }
}

void print_node(TreeNode *node) {
    if (node != NULL) {
        print_node(node->left_child);
        printf("%s\n", node->username);
        print_node(node->right_child);
    }
}

