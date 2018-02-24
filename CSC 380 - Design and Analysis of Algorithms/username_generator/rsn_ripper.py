import os

for file in os.listdir("pages"):
    raw_data = open("pages/" + file, 'r', encoding="latin1")

    names = []
    rsnLine = False

    for line in raw_data:

        if rsnLine:
            names.append(line)
            rsnLine = False
        elif line.startswith("<img class=\"avatar\" src='http://services.runescape.com/m=avatar-rs/"):
            rsnLine = True


    for name in names:
        open("270k_runescape_usernames.txt", 'a').write(name)

    raw_data.close()
    os.remove("pages/" + file)
