#include <stdio.h>
#include "bst.h"

void main() {
    BST *tree = init_tree("c", "pass");

    add(tree, "b", "pass");
    add(tree, "d", "pass");
    add(tree, "a", "pass");
    add(tree, "b1", "pass");
    add(tree, "b2", "pass");
    add(tree, "ba", "pass");

    print_tree(tree);

    printf("\n%i\n", tree->count);
}