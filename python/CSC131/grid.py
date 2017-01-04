import turtle

#create a window (named wn) and a turtle (named jeff)
wn = turtle.Screen()
jeff = turtle.Turtle()

#record window geometry
ymin = -wn.window_height()//2
ymax = -ymin
xmin = -wn.window_width()//2
xmax = -xmin

#set jeff's speed
jeff.speed(10)

#point jeff to the right
heading = 0


for y in range(ymin, ymax, 10):

    #pick up pen
    jeff.up()
    
    #re-position turtle
    jeff.goto(xmin, y)

    #put pen down
    jeff.down()
    
    #draw horizontal line across the window
    jeff.forward(wn.window_width())

    #move jeff back to where he started
    jeff.backward(wn.window_width())

#point jeff to the left  
jeff.left(90)
for x in range(xmin, xmax, 10):
    
    #pick up pen
    jeff.up()
    
    #re-position turtle
    jeff.goto(x, ymin)

    #put pen down
    jeff.down()
    
    #draw vertical line across the window
    jeff.forward(wn.window_height())

    #move jeff back to where he started
    jeff.backward(wn.window_height())
    