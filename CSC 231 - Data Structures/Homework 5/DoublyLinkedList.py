__author__ = 'guinnc'

class DoublyLinkedNode:
    """
    A single node in a doubly-linked list.
    Contains 3 instance variables:
        data: The value stored at the node.
        prev: A pointer to the previous node in the linked list.
        next: A pointer to the next node in the linked list.
    """

    def __init__(self, value):
        """
        Initializes a node by setting its data to value and
        prev and next to None.
        """
        self.data = value
        self.prev = None
        self.next = None


class DoublyLinkedList:
    """
    The doubly-linked list class has 3 instance variables:
        head: The first node in the list.
        tail: The last node in the list.
        size: How many nodes are in the list.
    """

    def __init__(self):
        """
        The constructor sets head and tail to None and the size to zero.
        """
        self.head = None
        self.tail = None
        self.size = 0

    def addFront(self, value):
        """
        Creates a new node (with data = value) and puts it in the
        front of the list.
        """
        newNode = DoublyLinkedNode(value)
        if (self.size == 0):
            self.head = newNode
            self.tail = newNode
            self.size = 1
        else:
            newNode.next = self.head
            self.head.prev = newNode
            self.head = newNode
            self.size += 1

    def addRear(self, value):
        """
        Creates a new node (with data = value) and puts it in the
        rear of the list.
        """
        newNode = DoublyLinkedNode(value)
        if (self.size == 0):
            self.head = newNode
            self.tail = newNode
            self.size = 1
        else:
            newNode.prev = self.tail
            self.tail.next = newNode
            self.tail = newNode
            self.size += 1

    def removeFront(self):
        """
        Removes the node in the front of the list.
        @return Returns the data in the deleted node.
        """
        value = self.head.data
        self.head = self.head.next
        if self.head != None:
            self.head.prev = None
        self.size -= 1
        return value

    def removeRear(self):
        """
        Removes the node in the rear of the list.
        @return Returns the data in the deleted node.
        """
        value = self.tail.data
        self.tail = self.tail.prev
        if self.tail != None:
            self.tail.next = None
        self.size -= 1
        return value

    def printItOut(self):
        """
        Prints out the list from head to tail all on one line.
        """
        temp = self.head
        while temp != None:
            print(temp.data,end=" ")
            temp = temp.next
        print()

    def printInReverse(self):
        """
        Prints out the list from tail to head all on one line.
        """
        temp = self.tail
        while temp != None:
            print(temp.data, end=" ")
            temp = temp.prev
        print()

    def atIndex(self, index):
        """
        Retrieves the data of the item at index.
        @param index The index of the item to retrieve.
        @return Returns the data of the item.
        """
        count = 0
        temp = self.head
        while count < index:
            count += 1
            temp = temp.next
        return temp.data

    def removeAt(self, index):
        '''
        Removes the value at 'index' and returns the removed value.
        '''
        temp = self.head
        for x in range(index):
            temp = temp.next
            
        if temp.prev == None and temp.next == None:
            self.head = None
            self.tail = None
        elif temp.prev == None:
            self.head = temp.next
            temp.next.prev = None
        elif temp.next == None:
            self.tail = temp.prev
            temp.prev.next = None
        else:
            temp.prev.next = temp.next
            temp.next.prev = temp.prev
            
        self.size -= 1
            
        return temp.data
    
    
doublyLinkedList = DoublyLinkedList()
doublyLinkedList.addRear(5)
doublyLinkedList.addFront(4)
doublyLinkedList.addRear(2)
doublyLinkedList.addRear(25)
doublyLinkedList.addRear(-4)

doublyLinkedList.printItOut()
print("The item removed was", doublyLinkedList.removeAt(3))
doublyLinkedList.printItOut()
print("The item removed was", doublyLinkedList.removeAt(3))
doublyLinkedList.printItOut()
print("The item removed was", doublyLinkedList.removeAt(0))
doublyLinkedList.printItOut()
print("The item removed was", doublyLinkedList.removeAt(1))
doublyLinkedList.printItOut()
print("The item removed was", doublyLinkedList.removeAt(0))
doublyLinkedList.printItOut()
print('Size of List:', doublyLinkedList.size)
