from Student import Student

rosterFile = open("roster.txt", 'r')

students = []
for line in rosterFile:
    lastname, firstname, gpa = line.split()
    student = Student(firstname, lastname, gpa)
    students.append(student)
    
slacker = students[0]
for student in students:
    if student.gpa < slacker.gpa:
        slacker = student
        
print("\t~LOWEST GPA~")
print(slacker)

greatestLastName = slacker
for student in students:
    if student.lastname > greatestLastName.lastname:
        greatestLastName = student
        
print("\n\t~GREATEST LASTNAME~")
print(greatestLastName)