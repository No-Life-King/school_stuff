import random

dataset = open("10M_logins.txt", 'r')

username_set = set();
lines = []
user_count = 0

for line in dataset:
    username, password = line.split('\t')
    lcase_usn = username.lower()
    invalid = lcase_usn in username_set
    
    while invalid:
        username += random.choice("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@.$%^&*()_+-")
        invalid = username.lower() in username_set
    
    username_set.add(username.lower())
    lines.append(username + "\t" + password)
    
    user_count += 1
    

print(len(lines))
new_dataset = open("10M_logins2.txt", 'w')
for line in lines:
    new_dataset.write(line)
    