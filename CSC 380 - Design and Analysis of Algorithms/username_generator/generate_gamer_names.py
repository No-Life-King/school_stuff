import random

charset = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@.$%^&*()_+-"

minecraft_users = open("source_lists/270k_runescape_usernames.txt", 'r')
password_file = open("source_lists/14.3M_passwords.txt", "r", encoding="latin1")
word_file = open("source_lists/1.6M_words.txt", 'r')
user_file = open("10M_logins.txt", "a")
user_set = set()
password_list = []
word_set = set()
char_count = 0

def word_pad(username):
    return username + random.choice(word_list)

def number_pad(username):
    number_of_numbers = random.choice((1, 2, 2, 3, 3, 4, 5, 6, 7))

    for x in range(number_of_numbers):
        username += str(x)

    return username

for word in word_file:
    valid_word = ""
    for char in word:
        if char in charset:
            valid_word += char

    word_set.add(valid_word)

word_list = [word for word in word_set]

for password in password_file:
    valid = True
    for char in password:
        if ord(char) > 126 and ord(char) < 160:
            valid = False
            break

    num_chars = len(password)
    if num_chars > 8 and num_chars < 30 and valid:
        password_list.append(password)

for user in minecraft_users:
    username = ""
    for letter in user:
        if letter in charset:
            username += letter

    while len(username) < 8:
        username = random.choice([word_pad(username), word_pad(username), number_pad(username)])

    user_set.add(username)

for user in user_set:
    login = user + "\t"
    login += random.choice(password_list)
    user_file.write(login)
