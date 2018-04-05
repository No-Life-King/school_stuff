

typedef struct user {
    char *name;
    char *password;
} User;

typedef struct user_array {
	User *users;
	int count;
} UserArray;

void init_array(UserArray *array);
void add(UserArray *users, char *usn, char *password);

void init_array(UserArray *array) {
		array->count = 0;
		array->users = NULL;
}

void add(UserArray *users, char *usn, char *password) {
	if (users->count == 0) {
		User **new_users = malloc(sizeof(User *));
		User *new_user = malloc(sizeof(User));
		new_user->name = usn;
		new_user->password = password;
		new_users[0] = new_user;
		users->users = *new_users;
		users->count++;
	}
	/*
	else {
		User **new_users = malloc(sizeof(User *) * (users->count + 1));
		if (new_users != NULL) {
			for (int x=0; x<users->count; x++) {
				new_users[x] = &users->users[x];
			}

			//free(users->users);
			users->users = *new_users;
			User *new_user = malloc(sizeof(User));
			if (new_user != NULL) {
				new_user->name = usn;
				new_user->password = password;
				new_users[users->count] = new_user;
				users->count++;
			} else {
				fprintf(stderr, "Error: malloc failed for new_user");
			}
		} else {
			fprintf(stderr, "Error: malloc failed for new_users");
		}
	}*/

}
