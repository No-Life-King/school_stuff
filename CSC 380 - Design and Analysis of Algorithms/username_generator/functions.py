def get_password_list():
    password_file = open("source_lists/14.3M_passwords.txt", "r", encoding="latin1")
    password_list = []

    for password in password_file:
        valid = True
        for char in password:
            if 126 < ord(char) < 160:
                valid = False
                break

        num_chars = len(password)
        if 8 < num_chars < 30 and valid:
            password_list.append(password)

    return password_list