users = open("10M_logins.txt", 'r')

bins = [0]*37
user_count = 0

try:
    for user in users:
        value = ord(user[0])

        if value > 64 and value < 91:
            value -= 65
        elif value > 96 and value < 123:
            value -= 97
        elif value > 47 and value < 58:
            value -= 22
        else:
            continue

        bins[value] += 1
        user_count += 1

except Exception as e:
    print(e)


biggest_bins = []

for x in range(26):
    biggest_bins.append((chr(x+65), bins[x]/user_count*100))

for y in range(26, 36):
    biggest_bins.append((chr(y+22), bins[y]/user_count*100))

fifty_percent = 0

while fifty_percent < 50:
    biggest = biggest_bins[0]
    for top10 in biggest_bins:
        if biggest[1] < top10[1]:
            biggest = top10

    print(biggest)
    fifty_percent += biggest[1]
    biggest_bins.remove(biggest)

print(user_count)

