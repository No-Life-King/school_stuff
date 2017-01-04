#displays a welcome message
print("Welcome to the MPG Calculator!")

#gets the users mileage and assigns its float value to a variable called "miles"
miles = float(input("Enter the number of miles you've driven: "))
#gets the amount of gasoline consumed by the user and assigns its float value to a variable called "gallons"
gallons = float(input("Enter the number of gallons of gasoline you've consumed: "))

#calculates the miles per gallon
mpg = miles/gallons
#displays the mpg to the user
print("You've been getting", mpg, "miles per gallon.")
