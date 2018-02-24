import random

"""
This script employs the Knuth shuffle algorithm to randomize the line order of a file.
It will destroy the input file so make sure to only run it on a copy of the original
incase the script crashes for some reason.
"""


# specify the file path, this should be a plain-text file
file_path = "/path/to/file"
file_handle = open(file_path, 'r')

# read the lines into an array for swapping to occur in O(n) time
lines = [line for line in file_handle]

# randomly choose a line and remove it from the pool of available lines until there are no more
pivot = len(lines) - 1
while pivot > 0:
    # pick a random index to be the next element of the array
    random_index = random.randint(0, pivot)

    # do a swap python style
    lines[pivot], lines[random_index] = lines[random_index], lines[pivot]
    pivot -= 1

# close the file and reopen it in write mode
file_handle.close()
file_handle = open(file_path, 'w')

for line in lines:
    file_handle.write(line)
