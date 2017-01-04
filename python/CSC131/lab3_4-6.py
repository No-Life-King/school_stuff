#imports the turtle module
import turtle

#displays a line of introductory text
print("Welcome to the polygon drawer")
#gets the desired number of sides from the user
sides = int(input("How many sides would you like your turtle to draw? "))
#gets the desired side length from the user
length = int(input("How long would you like each side to be (in pixels)? "))
#gets the desired side color from the user
color = input("What color would you like the sides to be? ")
#gets the desire fill color from the user
fillColor = input("What color would you like to fill the polygon with? ")

#creates a turtle object
artist = turtle.Turtle()
#sets the object shape to "turtle"
artist.shape("turtle")
#sets the line color to the color that the user specified
artist.color(color)
#sets the fill color to the color that the user specified
artist.fillcolor(fillColor)

#starts the fill process
artist.begin_fill()
#creates a loop that will repeat until all sides have been drawn
for x in range(sides):
    #draws each side
    artist.forward(length)
    #rotates the turtle the proper number of degrees to draw the specified number of sides
    artist.left(360/sides)
#ends the fill process, filling the polygon
artist.end_fill()
