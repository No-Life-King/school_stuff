#include <stdio.h>
#include <malloc.h>


typedef struct tree_node {
    char *username;
    char *password;
    struct tree_node *left_child;
    struct tree_node *right_child;
} TreeNode;

typedef struct {
    int count;
    TreeNode *root;
} AvlTree;

void init_tree(AvlTree *);
TreeNode *init_node(char *usn, char *pass);
void add(AvlTree *, char *usn, char *pass);
void print_tree(AvlTree *);

void main() {
    AvlTree users;

    init_tree(&users);


    TreeNode ten;
    ten = *init_node("phil", "is awesome");

    printf("%s", ten.password);
}

void init_tree(AvlTree *tree) {
    tree->count = 0;
    tree->root = NULL;
};

TreeNode *init_node(char *usn, char *pass) {
    TreeNode *node = malloc(sizeof(TreeNode));
    node->username = usn;
    node->password = pass;
    node->left_child = NULL;
    node->right_child = NULL;

    return node;
}



void print_tree(AvlTree *tree) {
    if (tree->root != NULL) {

    } else {
        puts("AVL tree empty.");
    }
}