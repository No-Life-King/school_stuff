# adds a few words to the "words" dictionary to clean up the spell checker
words = {'a': None, 'to': None, 'i': None, '': None, 'irene': None, 'adler': None,
         'holmes': None, 'atkinson': None, '1888': None, 'men\'s': None, "extraordinary": None}

# opens the dictionary file and fills a python dictionary with all of the words 
dict_file = open('dictionary.txt', 'r')
for line in dict_file:
    line = line.strip()
    words[line] = None


story = open('story.txt', 'r')
line_number = 0
error_count = 0

# goes through each word in the story checking for spelling accuracy and keeps
# track of the position of misspelled words by line number and word number
for line in story:
    line_number += 1
    line = line.replace('--', ' ')
    line = line.replace('-', ' ')
    word_list = line.split(' ')
    word_number = 0
    for word in word_list:
        word_number += 1
        word = word.strip(" ,.\n():;")
        word = word.lower()
        if word not in words:
            error_count += 1
            print("Spelling error found. \"" + word + "\" is misspelled. It is word #" + str(word_number) + " on line #" + str(line_number))

print("Total spelling errors found: " + str(error_count))