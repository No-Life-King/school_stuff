#displays a line of welcome text
print("Welcome to the Fahrenheit to Celsius converter!")

#gets the user's fahrenheit temperature and stores it as a float in the "fahrenheit" variable
fahrenheit = float(input("Enter a temperature in Fahrenheit: "))
#calculates the temperature in celsius
celsius = (fahrenheit - 32)* 5/9

#displays the temperature conversion results
print(fahrenheit, "degrees fahrenheit is", celsius, "degrees celsius.")