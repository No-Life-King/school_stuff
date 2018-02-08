import username_generator.functions as f
import random

user_file = open("1M", "w")

userlist = []
user_set = set()

password_file = open("password_list.txt", "r", encoding="latin1")
password_list = []
char_count = 0

for line in password_file:

    valid = True

    for char in line:
        if ord(char) > 126 and ord(char) < 160:
            valid = False
            break

    num_chars = len(line)
    if num_chars < 30 and valid:
        char_count += num_chars
        password_list.append(line)

print("Average Password Length: " + str(char_count / len(password_list)))

while len(user_set) < 1_000_000:
    user = ""

    for y in range(2):
        user += f.get_random_letter()

    for y in range(4):
        user += f.get_random_number()

    if user not in user_set:
        user_set.add(user)
    else:
        continue

    user += "\t"
    user += random.choice(password_list)
    userlist.append(user)

for x in range(1_000_000):
    user_file.write(userlist[x])