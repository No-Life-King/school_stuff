#imports the math module to be used throughout this program 
import math

#displays a line of welcome text
print("Welcome to the circle area calculator!")

#gets the user's radius and assigns its float value to a variable named "radius"
radius = float(input("Enter a Radius: "))

#calculates the area of the circle
area = math.pow(radius, 2) * math.pi

#displays the result to the user
print("The area of your circle is", area, "=)")
