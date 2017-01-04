import random


def separate_characters(word):
    """
    Returns a list containing all the characters of the string 'word'
    """
    array = []
    for c in word:
        array.append(c)
        
    return array


def build_dict(string):
    """
    A helper method for the 'is_anagram' function
    """
    char_count = {}
    for c in string:
        if char_count.get(c) == None:
            char_count.update({c: 1})
        else:
            char_count.update({c: char_count.get(c) + 1})
    return char_count


def is_anagram(s1, s2):
    """
    Returns true if s2 is an anagram of s1
    """
    if (len(s1) != len(s2)):
        return False
    
    s1 = s1.upper()
    s2 = s2.upper()
    
    char_count_s1 = build_dict(s1)
    char_count_s2 = build_dict(s2)
    
    if char_count_s1 == char_count_s2:
        return True
    else:
        return False


def separate_words(s):
    """
    Returns a list containing the words of a sentence, separated by spaces
    """
    word = ''
    word_list = []
    
    # For each character in the string, if the character is not a space, 
    # build a string out of the character. When a space is reached, append
    # the string to the list and clear the string. 
    for char in s:
        if char != ' ':
            word += char
        else:
            if word != '':
                word_list.append(word)
                word = ''
    
    # Catches the final word if there is one.
    if word != '':
        word_list.append(word)
    
    return word_list
    
    
def join_words(glue_symbol, aList):
    """
    Joins the words in a list into a single string with 'glue_symbol' as the
    delimiter.
    """
    sentence = ''
    last_word_index = len(aList)-1
    index_count = 0
    for word in aList:
        if index_count != last_word_index:
            sentence += word + glue_symbol
            index_count += 1
        else:
            sentence += word + '.'
    
    return sentence
    
    
def has_duplicates(aList):
    """
    Returns True if there is at least one duplicate in 'aList'. Otherwise returns
    False.
    """
    index = 0
    for num in aList:
        if num in aList[:index] + aList[index+1:]:
            return True
        index += 1
        
    return False


def remove_duplicates(aList):
    """
    Removes the duplicates from a list
    """
    dupe_free_list = []
    for x in aList:
        if x not in dupe_free_list:
            dupe_free_list.append(x)
            
    return dupe_free_list
    

def union(aList, bList):
    """
    Returns the union set of two lists without duplicates
    """
    return remove_duplicates(aList + bList)
    
    
def intersect(aList, bList):
    """
    Returns the intersection of two lists without duplicates
    """
    intersection = []
    aList = remove_duplicates(aList)
    for x in aList:
        if x in bList:
            intersection.append(x)
            
    return intersection


def difference(aList, bList):
    """
    Computes the logical difference of 'aList' minus 'bList'
    """
    return [x for x in remove_duplicates(aList) if x not in bList]
    
    
def gen_random_int_list():
    """
    Generates a list of 100 integers between 0 and 1000
    """
    int_list = []
    for x in range(100):
        int_list.append(random.randint(0, 1000))

    return int_list


def average(int_list):
    """
    Returns the average of a list of integers
    """
    total = 0
    for x in int_list:
        total += x

    return total / len(int_list)


def maximum(int_list):
    """
    Returns the maximum value for a list of integers
    """
    largest = int_list.pop()
    for x in int_list:
        if x > largest:
            largest = x
            
    return largest
    
    
def sum_of_squares(xs):
    """
    Adds the squares of the numbers in a list
    """
    total = 0
    for x in xs:
        total += x*x
        
    return total
    

def words_of_length_5(word_list):
    """
    Returns the number of words in a list that have a length of 5
    """
    count = 0
    for word in word_list:
        if len(word) == 5:
            count += 1
            
    return count


def replace(s, old, new):
    sentence = ''
    index = 0
    while index < len(s):
        fragment = s[index:index+len(old)]
        if fragment != old:
            sentence += s[index]
            index += 1
        else:
            sentence += new
            index += len(old)
            
    return sentence


#print(separate_characters("king kong"))
#print(is_anagram("Madam Curie", "Radium Came"))
#print(separate_words("   This short      sentence   has                 several words in it  "))
#print(join_words(":-:", ["This", "is", "a", "list","of", "words"]))
#print(has_duplicates([1,2,4,3,5,2]))
#print(remove_duplicates([1,1,1,2,2,2,3,3,3,4,5,6]))
#print(union([1,2,2,2,5,8,0], [1,4,5,6,7,8]))
#print(intersect([1,2,2,2,5,8,0], [1,4,5,5,5,0,6,7,8]))
#print(difference([1,2,2,2,5,8,0], [1,4,5,5,5,0,6,7,8]))
#print(average(gen_random_int_list()))
#print(maximum(gen_random_int_list()))
#print(sum_of_squares([2,3,4]))
#print(words_of_length_5(['apple', 'balls', 'cat', 'ducks', 'egg', 'fifth']))
#print(replace('i hate spam '*9, 'a', 'o'))
#print(replace('yams and trams spam america', 'am', 'ack'))
