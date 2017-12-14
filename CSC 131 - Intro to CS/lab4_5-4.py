import turtle

t = turtle.Turtle()

# this function draws a square based on the (x,y) coordinates 
# that are passed into it, and the side length that is specified
# this function is taken from the drawSquare file of my previous asssignment

# It is not possible to reuse this function to draw the pattern for this exercise
# because it does not allow for rotated squares. Furthermore, it becomes very
# impractical to calculate the center of the square relative to the center of
# the twisted squares pattern. Instead I wrote a method that makes much more sense.
def draw_square(t, center_x, center_y, side_length):
    t.up()
    half = side_length//2
    t.goto(center_x-half, center_y-half)
    t.down()
    for x in range(4):
        t.forward(side_length)
        t.left(90)

# Draws a twisted squares pattern given the center, length of the sides, and
# the total number of squares to draw.
def twisted_squares(t, center_x, center_y, side_length, number_of_squares):
    t.up()
    t.goto(center_x, center_y)
    t.down()
    for x in range(number_of_squares):
        for y in range(4):
            t.forward(side_length)
            t.left(90)
        t.left(360/number_of_squares)
        
twisted_squares(t, 0, 0, 150, 20)