#displays a line of informative text
print("How much will you pay in social security and medicare taxes this year?")

#gets the user's annual salary and stores it as a float in the "annualSalary" variable
annualSalary = float(input("Enter your annual salary (if you're not too shy): "))

#I'm sorry, I couldn't think of how to solve this problem without using 'if' statements
#Social Security tax is 6.7% on the first $118,500 dollars of income, after that it remains constant
if annualSalary >= 118500:
    socialSecurityTax = 7939.5
else:
    socialSecurityTax = .067 * annualSalary

#Calculates the medicare tax on the user's income
medicareTax = .0145 * annualSalary

#Calculates the user's final earnings after taxes
finalEarnings = annualSalary - socialSecurityTax - medicareTax

#Formats the results and displays them to the user
print("Final Earnings:\t\t$" + format(finalEarnings, "0,.2f") +
      "\nSocial Security Tax:\t$" + format(socialSecurityTax, "0,.2f") +
      "\nMedicare Tax:\t\t$" + format(medicareTax, "0,.2f"))
