import turtle

t = turtle.Turtle()

# this function draws a square based on the (x,y) coordinates 
# that are passed into it, and the side length that is specified
def draw_square(t, center_x, center_y, side_length):
    t.up()
    half = side_length//2
    t.goto(center_x-half, center_y-half)
    t.down()
    for x in range(4):
        t.forward(side_length)
        t.left(90)

# test square   
#draw_square(t, 300, 300, 200)

