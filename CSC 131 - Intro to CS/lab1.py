#!/usr/bin/env python3
name = input("What is your name? ")
age = input("Hello, " + name + ". How are old are you? ")
yearBorn = 2016 - int(age)
retirementAge = input("So you were born in " + str(yearBorn) + "! At what age do you expect to retire? ")
yearsToRetirement = int(retirementAge)-int(age)
retirementYear = 2016+yearsToRetirement
print("Ah, " + str(yearsToRetirement) + " years from now. You'll be retiring in " + str(retirementYear) + " then.")
