import urllib.request
import time, os

quit = False
start = int(os.listdir("pages")[-1][:-5]) + 1

for x in range(start, 79548):
    text = urllib.request.urlopen("http://services.runescape.com/m=hiscore/ranking?category_type=0&table=0&time_filter=0&date=1518247853159&page=" + str(x)).read()
    file = open("pages/" + str(x) + ".html", 'wb')
    file.write(text)

    reopen = open("pages/" + str(x) + ".html", 'r')
    count = 0
    for line in reopen:
        if "IP Block" in line:
            quit = True
            break

        if count > 300:
            break

    if quit:
        file.close()
        reopen.close()
        os.remove("pages/" + str(x) + ".html")
        break

    file.close()
    reopen.close()
    time.sleep(1)
    print("Ripped page number " + str(x))
