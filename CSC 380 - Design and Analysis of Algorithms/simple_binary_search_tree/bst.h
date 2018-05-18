/**
 * This is an implementation of a binary search tree that adds nodes in the
 * order that they're received, without performing any sort of balancing.
 */

#include <malloc.h>
#include <stdbool.h>

#pragma once

typedef struct tree_node {
    char *username;
    char *password;

    struct tree_node *left_child;
    struct tree_node *right_child;
} TreeNode;

typedef struct {
    int count;
    int height;
    int bytes;
    TreeNode *root;
} BST;


BST *init_tree(char *usn, char *pass);
TreeNode *make_node(char *usn, char *pass);
bool add(BST *tree, char *usn, char *pass);
TreeNode *lookup(BST *tree, char *usn);
int compare(const char *usn, const char *node_usn);
void print_tree(BST *tree);
void print_node(TreeNode *node);

/**
 * Initialize a binary search tree with a root node containing the provided username and password.
 * @param usn A string containing the user's username.
 * @param pass A string containing the user's password.
 * @return A pointer to the binary search tree.
 */
BST *init_tree(char *usn, char *pass) {
    BST *tree = malloc(sizeof(BST));
    tree->count = 1;
    tree->height = 0;
    tree->bytes = 32;
    tree->root = make_node(usn, pass);
    return tree;
}

/**
 * Create a new node to be inserted into the binary search tree.
 * @param usn A string containing the user's username.
 * @param pass A string containing the user's password.
 * @return A pointer to the tree node.
 */
TreeNode *make_node(char *usn, char *pass) {
    TreeNode *node = malloc(sizeof(TreeNode));

    // if malloc ends up failing because of too many tree nodes or not enough memory, let me know
    if (node == NULL) {
        puts("malloc failed\n");
        return NULL;
    }

    // initialize node values
    node->username = usn;
    node->password = pass;
    node->left_child = NULL;
    node->right_child = NULL;

    return node;
}

/**
 * Create a new user and add him or her to the tree.
 * @param tree A pointer to the tree to which the user should be added.
 * @param usn A string containing the user's username.
 * @param pass A string containing the user's password.
 * @return True if the new user has chosen a unique username. Otherwise false.
 */
bool add(BST *tree, char *usn, char *pass) {
    TreeNode *current = tree->root;

    // find the user's place in the tree
    while (true) {
        // compare the username with the username at each node
        int comparison = compare(usn, current->username);

        // return false if the username is already in the tree
        if (comparison == 0) {
            return false;
        }

        // if the username appears before the username of the current node in alphabetical order, either descend the
        // left tree of the node or insert it as a child of the node if it doesn't have one already
        if (comparison == -1) {
            if (current->left_child == NULL) {
                current->left_child = make_node(usn, pass);

                tree->count++;
				tree->bytes += str_len(usn);
				tree->bytes += str_len(pass);
				tree->bytes += 16;

                // watch the insertion operations if necessary
                //printf("%s is now the left child of %s\n", usn, current->username);

                return true;
            } else {
                current = current->left_child;
            }
        }
            // if the username appears after the username of the current node in alphabetical order, either descend the
            // right tree of the node or insert it as a child of the node if it doesn't have one already
        else if (comparison == 1) {
            if (current->right_child == NULL) {
                current->right_child = make_node(usn, pass);

                tree->count++;
				tree->bytes += str_len(usn);
				tree->bytes += str_len(pass);
				tree->bytes += 16;

                // watch the insertion operations if necessary
                //printf("%s is now the right child of %s\n", usn, current->username);

                return true;
            } else {
                current = current->right_child;
            }
        }
    }
}

TreeNode *lookup(BST *tree, char *usn) {
	TreeNode *node = tree->root;
	bool found = false;

	while (!found && node != NULL) {
		int comparison = compare(usn, node->username);
		if (comparison == -1) {
			node = node->left_child;
		} else if (comparison == 1) {
			node = node->right_child;
		} else {
			found = true;
		}
	}

	return node;
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

/**
 * Print every username in the binary search tree.
 * @param tree A pointer to the tree that should be printed.
 */
void print_tree(BST *tree) {

    // check if the tree is empty. If not, pass the root node to the print_node function
    if (tree->root != NULL) {
        print_node(tree->root);
    } else {
        puts("Binary search tree empty.");
    }
}

/**
 * Given a starting node, use a recursive in-order method to print the tree. Amazingly, the usernames will be
 * printed in alphabetical order.
 * @param node The node to be printed.
 */
void print_node(TreeNode *node) {

    // when a node has no left and/or right child, NULL will be passed into this function which is where it stops
    // recursing
    if (node != NULL) {
        print_node(node->left_child);
        printf("%30s  %-30s\n", node->username, node->password);
        print_node(node->right_child);
    }
}
