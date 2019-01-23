from markov_chain import markov_chain


griechischer_fruhling = open('Griechischer_Fruhling.txt', 'r')
griechischer_fruhling_words = []

for line in griechischer_fruhling:
    line = line.rstrip('\n')
    for word in line.split(' '):
        if len(word) > 0:
            griechischer_fruhling_words.append(word)

typee = open('Typee.txt', 'r')
typee_words = []

for line in typee:
    line = line.rstrip('\n')
    for word in line.split(' '):
        if len(word) > 0:
            typee_words.append(word)

german_markov_chain = markov_chain(griechischer_fruhling_words)
english_markov_chain = markov_chain(typee_words)

print(english_markov_chain.character_labels)
print(german_markov_chain.character_labels)

english_az_matrix = []

for x in range(english_markov_chain.index_table['a'], english_markov_chain.index_table['z'] + 1):
    row = []
    for y in range(english_markov_chain.index_table['a']-1, english_markov_chain.index_table['z']):
        row.append(english_markov_chain.state_transition_probabilities[x][y])
        #print(english_markov_chain.state_transition_probabilities[x][y], end=' ')

    #print()
    english_az_matrix.append(row)

german_az_matrix = []

for x in range(german_markov_chain.index_table['a'], german_markov_chain.index_table['z'] + 1):
    row = []
    for y in range(german_markov_chain.index_table['a']-1, german_markov_chain.index_table['z']):
        row.append(german_markov_chain.state_transition_probabilities[x][y])

    german_az_matrix.append(row)

row_total = 0
for row in german_az_matrix:
    row_total += sum(row)

print(row_total/26)

error = 0
for x in range(len(english_az_matrix)):
    for y in range(len(english_az_matrix)):
        error += abs(english_az_matrix[x][y] - german_az_matrix[x][y])

print(error/26**2)

word_set = set()
repeated_words = []
longest_word = ''
shortest_words = []
char_total = 0

count = 0
for word in german_markov_chain.generate(100):
    proba = word[1]
    word = word[0]

    print(word, '\t\t\t\t\t\t', proba)

    char_total += len(word)

    if len(word) > len(longest_word):
        longest_word = word

    if len(word) == 1:
        shortest_words.append(word)

    if word in word_set and word not in repeated_words:
        repeated_words.append(word)

    word_set.add(word)

#print(float(char_total)/1000)
#
#
# print("\nRepeats:")
# for repeat in repeated_words:
#     print(repeat)
#
print("\nLongest Word:")
print(longest_word)

# print("\nShortest Words:")
# for word in shortest_words:
#     print(word)
