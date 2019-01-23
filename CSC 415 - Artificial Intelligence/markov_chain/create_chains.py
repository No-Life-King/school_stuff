from markov_chain import markov_chain

small_language = ['spare', 'spear', 'pares', 'peers',
                  'reaps', 'peaks', 'speaker', 'keeper',
                  'pester', 'paste', 'tapas', 'pasta',
                  'past', 'straps', 'tears', 'terse',
                  'steer', 'street', 'stare', 'rates',
                  'streak', 'taste', 'tapa', 'peat',
                  'eat', 'ate', 'tea', 'seat']

small_word_chain = markov_chain(small_language)

print(small_word_chain.character_labels)
# print(small_word_chain.index_table)
# print(small_word_chain.state_transition_table)


hundred_random_words = small_word_chain.generate(100)
hundred_word_chain = markov_chain(hundred_random_words)
print(hundred_word_chain.character_labels)
print(hundred_word_chain.index_table)
print(hundred_random_words)
for row in hundred_word_chain.state_transition_probabilities:
    for prob in row:
        print(str(prob) + '\t', end='')

    print()

error = 0
for x in range(len(hundred_word_chain.state_transition_probabilities)):
    for y in range(len(hundred_word_chain.state_transition_probabilities)):
        error += abs(hundred_word_chain.state_transition_probabilities[x][y] - small_word_chain.state_transition_probabilities[x][y])

print(error/64)



# word_set = set()
# repeated_words = []
# longest_word = ''
# shortest_words = []
#
# for word in hundred_random_words:
#     proba = word[1]
#     word = word[0]
#     if word == 'a' or word == 'e' or word == 'p' or word == 'k' or word == 's' or word == 't' or word == 'r':
#         print(word, '\t\t\t\t\t\t', proba)
#     if len(word) > len(longest_word):
#         longest_word = word
#
#     if len(word) == 1:
#         shortest_words.append(word)
#
#     if word in word_set and word not in repeated_words:
#         repeated_words.append(word)
#
#     word_set.add(word)
#
#
# print("\nRepeats:")
# for repeat in repeated_words:
#     print(repeat)
#
# print("\nLongest Word:")
# print(longest_word)
#
# print("\nShortest Words:")
# for word in shortest_words:
#     print(word)
