def reverse(string):
    """
    This function accepts a string as an argument and returns
    the string with its characters in reverse order.
    """
    i = len(string) - 1
    result = ""
    while (i >= 0):
        result += string[i]
        i -= 1
        
    return result


def strip_char(string, char):
    """
    This function removes all occurences of the character 'char'
    from the string 'string'.
    """
    result = ""
    for c in string:
        if (c != char):
            result += c
    
    return result


def is_palindrome(string):
    """
    Returns 'True' if the string is a palindrome or 'False'
    if it is not.
    """
    if (string == reverse(string)):
        return True
    return False


def substr_count(s1, s2):
    """
    Counts the number of times a substring 's2' occurs in a
    string 's1' and returns that value.
    """
    count = 0
    for x in range(len(s1)):
        if (s1[x:x+len(s2)] == s2):
            count += 1
    return count
    
    
def substr_strip(s1, s2):
    """
    Strips all occurrences of the string 's2' from the string
    's1'.
    """
    result = s1.replace(s2, "")
    return result


def substitution_cipher(message, cipher):
    """
    Applies a substitution cipher to 'message' according to the
    specified cipher 'cipher'.
    """
    # return an error if the cipher is of improper length
    if (len(cipher) != 26):
        return "Cipher Length Error: your cipher must be 26 characters long."
    
    alphabet = 'abcdefghijklmnopqrstuvwxyz'
    lowercase_message = message.lower()
    encrypted_message = ''
    for char in lowercase_message:
        if (char in alphabet):
            encrypted_message += cipher[alphabet.index(char)]
        else:
            encrypted_message += char
    
    return encrypted_message


def substitution_cipher_decrypter(encrypted_message, cipher):
    """
    Decrypts a message that is encrypted by the simple substitution
    cipher in the above function.
    """
    # return an error if the cipher is of improper length
    if (len(cipher) != 26):
        return "Cipher Length Error: your cipher must be 26 characters long."
    
    alphabet = 'abcdefghijklmnopqrstuvwxyz'
    lowercase_message = encrypted_message.lower()
    decrypted_message = ''
    for char in lowercase_message:
        if (char in alphabet):
            decrypted_message += alphabet[cipher.index(char)]
        else:
            decrypted_message += char
    
    return decrypted_message
    
    
# Some code that tests encryption and decryption:
# print(substitution_cipher("This is the message to be encrypted. It should contain a decent \
# amount of sample text to make sure everything's working properly. ", "qwuiocyphertxzasdfgjklvbnm"))
# 
# print(substitution_cipher_decrypter("jphg hg jpo xoggqyo ja wo ozufnsjoi. hj gpakti uazjqhz \
# q iouozj qxakzj ac gqxsto jobj ja xqro gkfo olofnjphzy'g vafrhzy sfasoftn." , "qwuiocyphertxzasdfgjklvbnm"))


