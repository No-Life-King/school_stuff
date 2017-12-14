from BankAccount import *

# open the accounts file
accountsFile = open("accounts.txt", 'r')

# read the accounts in from the file and create a list of 'BankAccount' objects
accounts = []
sumOfBalances = 0
for line in accountsFile:
    firstName, lastName, balance = line.split()
    balance = float(balance)
    sumOfBalances += balance
    account = BankAccount(firstName, lastName, balance)
    accounts.append(account)

# get the number of accounts to be used throughout the program
numAccounts = len(accounts)

# sort the accounts by balance
accountsSorted = []
while len(accounts) != 0:
    highest = accounts[0]
    for account in accounts:
        if account.balance > highest.balance:
            highest = account
            
    accounts.remove(highest)
    accountsSorted.append(highest)

# record the highest and lowest accounts by balance
largestBalance = accountsSorted[0].__str__()
smallestBalance = accountsSorted[numAccounts-1].__str__()


# find the median
median = 0
if numAccounts % 2 == 1:
    median = accountsSorted[numAccounts//2].balance
else:
    median = (accountsSorted[numAccounts//2].balance + accountsSorted[numAccounts//2 - 1].balance) / 2
    

# add interest to all of the accounts
newSumOfBalances = 0
for account in accountsSorted:
    account.deposit(account.calculateInterest())
    newSumOfBalances += account.balance
    

# sort alphabetically by first name 
sortedByFirst = []
while len(accountsSorted) != 0:
    highest = accountsSorted[0]
    for account in accountsSorted:
        if account.firstName > highest.firstName:
            highest = account
            
    accountsSorted.remove(highest)
    sortedByFirst.append(highest)
            
lowestFirstname = sortedByFirst[numAccounts-1]
    

# sort alphabetically by last name
sortedByLast = []
while len(sortedByFirst) != 0:
    highest = sortedByFirst[0]
    for account in sortedByFirst:
        if account.lastName > highest.lastName:
            highest = account
            
    sortedByFirst.remove(highest)
    sortedByLast.append(highest)
            
lowestLastname = sortedByLast[numAccounts-1]


# print out the results    
print("The average balance of all the accounts is", sumOfBalances/numAccounts)
print("The account with the largest balance is", largestBalance)
print("The account with the smallest balance is", smallestBalance)
print("The median balance is", median)
print("The average balance of all the accounts after adding interest is", newSumOfBalances/numAccounts)
print("The account with the lowest first name is", lowestFirstname)
print("The account with the lowest first name is", lowestLastname)



