import random
import username_generator.functions as f

firstname_file = open("source_lists/10k_multinational_first_names.txt", 'r')
lastname_file = open("source_lists/1000_surnames.txt", 'r')
password_file = open("source_lists/empty")
user_file = open("10M_logins.txt", "a")

firstnames = [line.strip("\n") for line in firstname_file]
lastnames = [line.strip("\n").lower() for line in lastname_file]

username_set = set()
count = 0

while count < 1000000:
    concat = random.choice((".", ".", "_", "", "-"))
    capitalize = random.choice((True, False))
    trailing_numbers = random.choice((True, False))
    firstname = random.choice(firstnames)
    lastname = random.choice(lastnames)

    if concat == "" or capitalize:
        firstname = firstname.capitalize()
        lastname = lastname.capitalize()

    if trailing_numbers:
        number = ""

        for x in range(random.choice((0, 1, 1, 2, 2, 3, 3, 4))):
            number += random.choice("0123456789")

        lastname += number

    username = firstname + concat + lastname + "\t"

    if len(username) < 30:
        username_set.add(username)

    count += 1

passwords = f.get_password_list()

for user in username_set:
    user_file.write(user + random.choice(passwords))
    