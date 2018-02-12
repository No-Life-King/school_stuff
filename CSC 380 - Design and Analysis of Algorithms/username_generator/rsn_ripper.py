import os

start = int(os.listdir("pages")[0][:-5])
finish = int(os.listdir("pages")[-1][:-5])

for file in range(start, finish):
    raw_data = open("pages/" + str(file) + ".html", 'r', encoding="latin1")

    names = []
    rsnLine = False

    for line in raw_data:

        if rsnLine:
            names.append(line)
            rsnLine = False
        elif line.startswith("<img class=\"avatar\" src='http://services.runescape.com/m=avatar-rs/"):
            rsnLine = True


    for name in names:
        open("rs_usernames.txt", 'a').write(name)

    raw_data.close()
    os.remove("pages/" + str(file) + ".html")
