import random


class markov_chain:

    def __init__(self, words):
        self.state_transition_table = []
        self.state_transition_probabilities = []
        self.character_labels = []
        self.index_table = {}
        self.train(words)


    def train(self, words):
        self.preprocess(words)

        for x in range(len(self.character_labels) - 1):
            self.state_transition_table.append([0] * (len(self.character_labels) - 1))

        for word in words:
            self.state_transition_table[0][self.index_table[word[0]]-1] += 1

            if len(word) > 1:
                for y in range(len(word)-1):
                    self.state_transition_table[self.index_table[word[y]]][self.index_table[word[y+1]]-1] += 1

                self.state_transition_table[self.index_table[word[y+1]]][self.index_table['end']-1] += 1

        for position in self.state_transition_table:
            probabilities = []
            total = sum(position)

            if total > 0:
                for count in position:
                    probabilities.append(float(count)/total)
            else:
                probabilities = [0] * (len(self.character_labels) - 1)

            self.state_transition_probabilities.append(probabilities)


    def preprocess(self, words):
        for word in words:
            for char in word:
                if char not in self.character_labels:
                    self.character_labels.append(char)

        self.character_labels.sort()
        self.character_labels.insert(0, 'start')
        self.character_labels.append('end')

        for x in range(1, len(self.character_labels)):
            self.index_table[self.character_labels[x]] = x



    def generate(self, num_words):
        words = []

        while len(words) < num_words:
            roll = random.random()
            idx = 0
            state = 0
            word = ''
            word_proba = 1

            while True:
                total = self.state_transition_probabilities[state][idx]
                while total < roll:
                    idx += 1
                    try:
                        total += self.state_transition_probabilities[state][idx]
                    except:
                        print(total, state, idx, roll)
                        break

                word_proba *= self.state_transition_probabilities[state][idx]
                char = self.character_labels[idx+1]
                if char == 'end':
                    break

                total = 0
                roll = random.random()
                word += char
                state = idx+1
                idx = 0

            words.append((word, word_proba))
            #words.append(word)

        return words
