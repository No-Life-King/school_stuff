class Student:
    """
    An object that represents a student.
    """
    def __init__(self, firstname, lastname, gpa):
        self.firstname = firstname
        self.lastname = lastname
        self.gpa = gpa
        
    def __str__(self):
        return "Name:\t" + self.firstname + " " + self.lastname + "\nGPA:\t" + self.gpa