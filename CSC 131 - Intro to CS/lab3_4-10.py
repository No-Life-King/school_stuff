#imports the turtle module
import turtle

#creates a turtle object
clockFaceDrawer = turtle.Turtle()
#sets the shape to "turtle"
clockFaceDrawer.shape("turtle")
#picks up the turtle
clockFaceDrawer.up()
#moves the turtle forward
clockFaceDrawer.forward(125)
#sets the line color to sea green
clockFaceDrawer.color('#90EE90')
#sets the fill color to sea green
clockFaceDrawer.fillcolor('#90EE90')
#starts the fill
clockFaceDrawer.begin_fill()
#turns the turtle north
clockFaceDrawer.left(90)
#sets the turtle down
clockFaceDrawer.down()
#draws the first half side of the clock face
clockFaceDrawer.forward(125)
#draws 3 sides of the clock face
for x in range(3):
    #turns the turtle 90 degrees
    clockFaceDrawer.left(90)
    #draws a 250 px line
    clockFaceDrawer.forward(250)
#turns the turtle north again
clockFaceDrawer.left(90)
#draws the final half of the side
clockFaceDrawer.forward(125)
#fills the clock face with sea green
clockFaceDrawer.end_fill()

#changes the color to blue
clockFaceDrawer.color("blue")
#picks the turtle up
clockFaceDrawer.up()
#turns the turtle toward the center of the clock
clockFaceDrawer.left(90)
#moves the turtle into the center of the clock
clockFaceDrawer.forward(125)

#draws the clock face
for x in range(12):
    #picks the turtle up
    clockFaceDrawer.up()
    #moves the turtle forward
    clockFaceDrawer.forward(75)
    #sets the turtle down
    clockFaceDrawer.down()
    #draws the tick in front of the turtle
    clockFaceDrawer.forward(10)
    #picks the turtle up again
    clockFaceDrawer.up()
    #moves the turtle foward
    clockFaceDrawer.forward(10)
    #stamps an impression of the turtle above the tick
    clockFaceDrawer.stamp()
    #moves the turtle back to the center of the clock
    clockFaceDrawer.backward(95)
    #turns the turtle preparing it to loop through the rest of the prongs
    clockFaceDrawer.left(30)
    

