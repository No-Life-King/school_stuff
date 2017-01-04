import random


def initialize():
    """
    This function initializes the card game, prompting the user to find out
    whether he or she wants to go first. It returns "True" if the user wants
    to go first, "False" if the user does not want to go first. The program
    will keep prompting the user until it recieves a valid response of "y"
    or "n".
    """
    print("New Game Start")
    while (True):
        user_goes_first = input("Do you want to go first? (y/n):  ")
        if (user_goes_first == 'y'):
            return True
        elif (user_goes_first == 'n'):
            return False
        else:
            print(" \"" + user_goes_first + "\" is not a valid option")
            
            
def print_dashes():
    """
    This is the function that does the super important job of printing the
    dashed line between moves during the gameplay. It provides plenty of dashes
    as well as line returns before and after the dashes to make things nice and
    readable.
    """
    print("\n-----------------------------------------\n")


def prompt_user(cards_left):
    """
    This function informs the user about how many cards are left, and then prompts
    the user to choose the number of cards that he or she wants to draw. It will
    loop until it receives a valid response. Once it does, it will return that
    response. 
    """
    print("There are", cards_left, "cards left\n")
    while (True):
        cards_drawn = int(input("How many cards do you pick? (1-3): "))
        if (cards_drawn in [1, 2, 3]):
            if (cards_drawn <= cards_left):
                return cards_drawn
            else:
                print("There are only " + str(cards_left) + " cards left. Choose again")
        else:
            print("Cannot pick " + str(cards_drawn) + " cards")


def the_game(users_move):
    """
    This function controls the gameplay. It starts out with 21 cards, determines
    who goes first, and provides alternating turns to the user and the AI. The
    AI is programmed to always defeat the user unless the user chooses moves that
    correspond with the winning strategy. Once all the cards have been drawn, the
    function returns "True" if the AI wins, or "False" if the user wins.
    """
    cards_left = 21
    while (cards_left != 0):
        if (users_move):
            cards_drawn = prompt_user(cards_left)
            users_move = False
        else:
            cards_drawn = cards_left % 4
            if (cards_drawn == 0):
                cards_drawn = random.randint(1,3)
            users_move = True
            text = "There are " + str(cards_left) + " cards left \n\n\t\tI pick "  + str(cards_drawn) + " cards"
            if (cards_drawn == 1):
                print(text[0:len(text)-1])
            else:
                print(text)
        cards_left -= cards_drawn
        print_dashes()
        
    return users_move

# this stuff down here uses the functions to generate the game
user_goes_first = initialize()
print_dashes()

if (the_game(user_goes_first)):
    print("I win! Game over")
    
    # prints this WarGames reference if the user chooses not to go first
    # the computer always wins under these circumstances
    if (user_goes_first == False):
        print("\nGreetings Professor Narayans\n\n")
        print("A Strange Game.")
        print("The only winning move is\nnot to play.")
        
else:
    print("You win! Game over")