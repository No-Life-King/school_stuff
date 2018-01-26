import functions as f
import random

file1 = open("1k", "w")
file2 = open("10k", "w")
file3 = open("100k", "w")
file4 = open("1M", "w")
file5 = open("10M", "w")

userlist = []

for x in range(10_000_000):
    user = ""

    for y in range(3):
        user += f.get_random_letter()

    for y in range(4):
        user += f.get_random_number()

    user += "\t"

    z = random.randint(8, 29)
    for y in range(z):
        user += f.get_random_pass_char()

    user += "\n"
    userlist.append(user)

# South of here is just some sloppy code for writing the same accounts to multiple files while
# cutting off files at their appropriate user count.

for x in range(1_000):
    file1.write(userlist[x])
    file2.write(userlist[x])
    file3.write(userlist[x])
    file4.write(userlist[x])
    file5.write(userlist[x])

for x in range(1_000, 10_000):
    file2.write(userlist[x])
    file3.write(userlist[x])
    file4.write(userlist[x])
    file5.write(userlist[x])

for x in range(10_000, 100_000):
    file3.write(userlist[x])
    file4.write(userlist[x])
    file5.write(userlist[x])

for x in range(100_000, 1_000_000):
    file4.write(userlist[x])
    file5.write(userlist[x])

for x in range(1_000_000, 10_000_000):
    file5.write(userlist[x])