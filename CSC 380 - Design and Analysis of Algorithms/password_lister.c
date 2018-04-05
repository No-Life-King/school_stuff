#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Returns the size of f in bytes, or -1 on error.
static int GetFileSize(FILE *f) {
  int to_return;
  if (fseek(f, 0, SEEK_END) != 0) {
    printf("Failed getting file end.\n");
    return -1;
  }
  to_return = ftell(f);
  if (fseek(f, 0, SEEK_SET) != 0) {
    printf("Failed seeking file start.\n");
    return -1;
  }
  return to_return;
}

// Returns # of lines in the file, or -1 on an error.
static int CountFileLines(char *data) {
  int to_return = 0;
  int tab_seen = 0;

  for (int i = 0; i < file_size; i++) {
    if (*data == '\t') {
      tab_seen = 1;
      data++;
      continue;
    }
    if (*data != '\n') {
      data++;
      continue;
    }
    if (!tab_seen) {
      printf("Bad format.\n");
      return -1;
    }
    // At a newline, and have seen a tab this line.
    to_return++;
    tab_seen = 0;
    data++;
  }
  return to_return;
}

// Modify file_content to replace tabs and newlines with null characters. Also
// fills in username and password pointers in the usernames array.
static void SetUsernamesPointers(char **usernames, char *file_content,
    int user_count) {
  int i;
  for (i = 0; i < user_count; i++) {
    usernames[i * 2] = file_content;
    while (*file_content != '\t') file_content++;
    // Replace tab with null char
    *file_content = '\0';
    file_content++;
    usernames[(i * 2) + 1] = file_content;
    while (*file_content != '\n') file_content++;
    // Replace newline with null char
    *file_content = '\0';
    file_content++;
  }
}

int main(int argc, char **argv) {
  char **usernames = NULL;
  char *file_buffer = NULL;
  int file_size, i;
  int user_count = 0;
  FILE *f = NULL;
  f = fopen("./passwords.txt", "rb");
  if (!f) {
    printf("Failed opening.\n");
    return 1;
  }
  file_size = GetFileSize(f);
  if (file_size <= 0) {
    printf("Bad file size.\n");
    fclose(f);
    return 1;
  }
  // Allocate memory to hold the entire file.
  file_buffer = (char *) malloc(file_size);
  if (!file_buffer) {
    printf("Failed allocating file buffer.\n");
    fclose(f);
    return 1;
  }
  // Read the file into the memory.
  if (fread(file_buffer, 1, file_size, f) != file_size) {
    printf("Failed reading file.\n");
    free(file_buffer);
    return 1;
  }
  fclose(f);
  f = NULL;
  // Count the total number of usernames.
  user_count = CountFileLines(file_buffer);
  if (user_count <= 0) {
    printf("Bad username count.\n");
    free(file_buffer);
    return 1;
  }
  // Allocate the username/password pointers buffer.
  usernames = (char **) malloc(user_count * 2 * sizeof(char*));
  if (!usernames) {
    printf("Failed allocating user/password pointer buffer.\n");
    free(file_buffer);
    return 1;
  }
  // Make sure all pointers in the usernames array are set to NULL.
  memset(usernames, 0, user_count * 2 * sizeof(char*));
  SetUsernamesPointers(usernames, file_buffer, user_count);
  for (i = 0; i < 10; i++) {
    printf("User %d: %s, password %s\n", i, usernames[i * 2], usernames[(i *
      2) + 1]);
  }
  free(file_buffer);
  free(usernames);
  return 0;
}
