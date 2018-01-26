import random

def get_random_letter():
    return random.choice("abcdefghijklmnopqrstuvwxyz")

def get_random_number():
    return random.choice("0123456789")

def get_random_pass_char():
    return random.choice("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^%&*()_+-=")