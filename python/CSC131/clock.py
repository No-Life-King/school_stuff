#clock.py

import turtle
import datetime

#make no changes to this function
def createClock(radius, label = "turtleX", ticking=False):
    """Draw a clock with specified radius. If ticking is True also show current time

    Args:
        radius (int): The radius of the clock face
        label (string): The label to use at the center of the clock, defaults to "turtleX"
        ticking (boolean): if True, clock is repeatedly updated and redrawn. Defaults to False.
        
    Returns:
        Nothing. 
    """
    
    wn = turtle.Screen()
    wn.tracer(10) #speeds up drawing of clock fixed parts
    
    #Create four turtles for the various parts of the clock
    hourHand = turtle.Turtle()
    minuteHand = turtle.Turtle()
    secondHand = turtle.Turtle()
    outline = turtle.Turtle()
        
    #hide all the turtles. Speeds up drawing considerably
    hourHand.ht()
    minuteHand.ht()
    secondHand.ht()
    outline.ht()

    #Set turtle animation speed 
    hourHand.speed(10)
    minuteHand.speed(10)
    secondHand.speed(10)
    outline.speed(10)

    #Draw the face of the clock
    drawClockFace(outline, radius, label)

    
    if ticking == False: #simply show the current time once
        #get current time 
        now = datetime.datetime.now()
        showTime(radius, hourHand, minuteHand, secondHand, now.hour, now.minute, now.second)
        wn.exitonclick()
    else: #ticking is True, so update time continuously 
        while True:
            now = datetime.datetime.now()
            showTime(radius, hourHand, minuteHand, secondHand, now.hour, now.minute, now.second)
    


#make no changes to this function    
def drawClockFace(t,radius, label):
    """Draws the face of the clock using the specified radius

    Args:
    t (Turtle): a reference to a Turtle
    radius (int): The radius of the clock face
    label (string): The label to show on the clock face
        
        
    Returns:
        Nothing. 

    """
    writeName(t, label)
        
    drawDots(t, radius)
    
    drawTickMarks(t, radius)
    
    drawLabels(t, radius)

#make no changes to this function
def writeName(t, label):
    """Writes the label at the center of the clock face

    Args:
        t (Turtle): a reference to a Turtle
        label (string): the label to show at the center of the clock
        
    Returns:
        Nothing. 
    """
    t.up()
    t.goto(-15,-10)
    t.write(label)

#make no changes to this function
def showTime(radius, hh,mh,sh,hour,minute,second):
    """Using the specified turtles, draws the clock hands to reflect hour, minute, and second
       Note: hour is in 24-hour format

       Args:
        radius (int): The radius of the clock face
        hh (Turtle): reference to the turtle used for drawing hour hand
        mh (Turtle): reference to the turtle used for drawing minute hand
        sh (Turtle): reference to the turtle used for drawing second hand
        hour (int): value for minute
        minute (int): value for minute
        second (int) : value for second

    Returns:
        Nothing. 

       
    """
    drawHourHand(hh, radius,hour, minute)

    drawMinuteHand(mh, radius, minute)
       
    drawSecondHand(sh, radius, second)

##
## 
## Add code to the functions below so that the code performs the task specified for that function
##
    
def drawDots(t, radius):
    """Draws the dots on the clock face, one dot per minute

    Args:
        t (Turtle): a reference to a turtle
        radius (int): The radius of the clock face

    Returns:
        Nothing. 
    """
    t.seth(0)
    for x in range(61):
        t.goto(0, 0)
        t.forward(radius)
        t.dot()
        t.left(6)

        
def drawTickMarks(t, radius):
    """Draws the tick marks on the clock face, one tick every five minutes

    Args:
        t (Turtle): a reference to a turtle
        radius (int): The radius of the clock face

    Returns:
        Nothing. 

    """
    t.seth(0)
    for x in range(13):
        t.goto(0, 0)
        t.forward(radius-15)
        t.down()
        t.forward(15)
        t.up()
        t.left(30)

def drawLabels(t, radius):
    """Draws the labels 0..11 on the clock face, one label every 5 minutes

    Args:
        t (Turtle): a reference to a turtle
        radius (int): The radius of the clock face

    Returns:
        Nothing. 
    """
    t.goto(0, 0)
    t.seth(60)
    for x in range(1, 13):
        t.forward(radius+20)
        t.write(x)
        t.goto(0,0)
        t.right(30)
        

def drawHourHand(t, radius, h, m, color="red"):
    """Draws the hour hand corresponding to specified hour and minute in specified color. Note: hour value is in 24-hour format

    Args:
        t (Turtle): a reference to a turtle
        radius (int): The radius of the clock face
        h (int): value for hour in 24-hour format
        m (int): value for minute
        color (string): optional. Specifies color of hour hand, defaults to red.
        

    Returns:
        Nothing. 
    """
    t.clear()
    t.goto(0,0)
    t.seth(90)
    t.color(color)
    t.right(h * 30)
    t.down()
    t.forward(radius - 65)
    t.up()

def drawMinuteHand(t, radius, m, color="green"):
    """Draws the minute hand corresponding to specified minute in specified color. Default color is green.

    Args:
        t (Turtle): a reference to a turtle
        radius (int): The radius of the clock face
        m (int): value for minute
        color (string): optional. Specifies color of minute hand, defaults to green.
        

    Returns:
        Nothing. 
    """
    t.clear()
    t.goto(0,0)
    t.seth(90)
    t.color(color)
    t.right(m * 6)
    t.down()
    t.forward(radius - 40)
    t.up()
    
def drawSecondHand(t, radius, s, color="blue"):
    """Draws the second hand corresponding to specified second in specified color. Default color is blue

    Args:
        t (Turtle): a reference to a turtle
        radius (int): The radius of the clock face
        s (int): value for seconds
        color (string): optional. Specifies color of second hand, defaults to blue.
        

    Returns:
        Nothing. 
    """
    t.clear()
    t.goto(0,0)
    t.seth(90)
    t.color(color)
    t.right(s * 6)
    t.down()
    t.forward(radius - 25)
    t.up()

#create a clock of radius 200, with default label. Clock is not ticking
createClock(300, "TurtleX", True)