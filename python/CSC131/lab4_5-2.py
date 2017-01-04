import turtle

t = turtle.Turtle()

# this function draws a square based on the (x,y) coordinates 
# that are passed into it, and the side length that is specified
# this function is taken from the drawSquare file of my previous asssignment
def draw_square(t, center_x, center_y, side_length):
    t.up()
    half = side_length//2
    t.goto(center_x-half, center_y-half)
    t.down()
    for x in range(4):
        t.forward(side_length)
        t.left(90)

# this function draws a series of nested squares, increasing the side length by
# 20 pixels after each iteration. Since during the first iteration, x is zero,
# the side length of the first square is the length specified by the user.  
def nested_squares(t, center_x, center_y, side_length, number_of_squares):
    for x in range(number_of_squares):
        draw_square(t, center_x, center_y, side_length+20*x)

# draws 5 nested squares at the origin, the first being 20x20
nested_squares(t, 0, 0, 20, 5)