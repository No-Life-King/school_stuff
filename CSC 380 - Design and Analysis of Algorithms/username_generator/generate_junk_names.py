import username_generator.functions as f
import random

login_file = open("10M_logins.txt", 'r')
username_list = []
junk_set = set()

for login in login_file:
    username_list.append(login.split()[0])

passwords = f.get_password_list()

count = len(username_list)
while count < 10000000:
        index = 0
        junk_name = random.choice(username_list)[0]
        try:
            while True:
                index += 1
                junk_name += random.choice(username_list)[index]

        except:
            if junk_name not in junk_set:
                junk_set.add(junk_name)
                count += 1

login_file.close()
login_file = open("10M_logins.txt", "a")
for name in junk_set:
    login_file.write(name + '\t' + random.choice(passwords))