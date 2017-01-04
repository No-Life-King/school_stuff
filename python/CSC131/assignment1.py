import turtle

#creates the turtle that is used for drawing all the houses and sets its speed to slow
t = turtle.Turtle()
t.speed("slow")

#the function that is called repeatedly to draw each house
def drawHouse(x, y, houseColor, roofColor, width, height):
    #set the position of the house without drawing any uncessary lines
    t.up()
    t.goto(x, y)
    t.down()
    
    #draws the house
    t.fillcolor(houseColor)
    t.begin_fill()
    for side in range(2):
        t.forward(width)
        t.left(90)
        t.forward(height)
        t.left(90)
    t.end_fill()
    
    #moves the pointer back to the top left corner of the house
    t.up()
    t.goto(x, y+height)
    t.down()
    
    #draws the roof
    t.fillcolor(roofColor)
    t.begin_fill()
    t.left(60)
    t.forward(width)
    t.right(120)
    t.forward(width)
    t.end_fill()
    
    #draws the house label
    t.up()
    t.goto(x, y-20)
    t.down()
    t.write(houseColor + " house")
    t.up()
    
    #resets the pointer
    t.home()

#draws the green house
drawHouse(10, 10, "green", "red", 100, 200)
#draws the blue house
drawHouse(-150, -130, "blue", "orange", 100, 50)
#draws the red house
drawHouse(-150, 230, "red", "yellow", 50, 75)
#draws the pink house
drawHouse(180, -200, "pink", "blue", 75, 50)