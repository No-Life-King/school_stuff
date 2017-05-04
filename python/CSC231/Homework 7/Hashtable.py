import math

__author__ = 'guinnc'
''' HashTable class from book listings 5_5, 5_6, 5_7, and 5_8.
    Note that I've written a slightly more complicated hashfunction method
    that allows for the key to be either a string or an integer. '''


class HashTable:
    def __init__(self):
        '''
        Constructor sets the default number of slots to 11.
        '''
        self.size = 11
        self.slots = [None] * self.size
        self.data = [None] * self.size
        self.length = 0


    def put(self, key, data):
        '''
        Inserts data into the hash table using the key.
        :param key: An integer or string used as the hash value.
        :param data: The data to be inserted.
        :return: Nothing is returned.
        Table resizing is roughly O(n).
        '''

        hashvalue = self.hashfunction(key, len(self.slots))

        if self.slots[hashvalue] == None:
            self.slots[hashvalue] = key
            self.data[hashvalue] = data
            self.length += 1
        else:
            if self.slots[hashvalue] == key:
                self.data[hashvalue] = data  # replace
            else:
                nextslot = self.rehash(hashvalue, len(self.slots))
                while self.slots[nextslot] != None and \
                                self.slots[nextslot] != key:
                    nextslot = self.rehash(nextslot, len(self.slots))

                if self.slots[nextslot] == None:
                    self.slots[nextslot] = key
                    self.data[nextslot] = data
                    self.length += 1
                else:
                    self.data[nextslot] = data  # replace
                    
        load_factor = self.length / self.size
        if load_factor > .5:
            new_size = 2 * self.size
            while not self.is_prime(new_size):
                new_size += 1
                
            self.size = new_size
            new_slots = [None] * new_size
            new_data = [None] * new_size
            
            for i in range(len(self.slots)):
                key = self.slots[i]
                if key != None:
                    data = self.data[i]
                    hashvalue = self.hashfunction(key, self.size)
                    
                    if new_slots[hashvalue] == None:
                        new_slots[hashvalue] = key
                        new_data[hashvalue] = data
                    else:
                        nextslot = self.rehash(hashvalue, self.size)
                        while new_slots[nextslot] != None:
                            nextslot = self.rehash(nextslot, self.size)
                            
                        new_slots[nextslot] = key
                        new_data[nextslot] = data
                        
            self.slots = new_slots
            self.data = new_data


    def hashfunction(self, key, size):
        '''
        Computes the index given a particular key.
        :param key: An integer or string used as the key.
        :param size: The size of the hash table.
        :return: The computed index.  This method may thrown an exception
        if the key is not an integer or a string.
        '''
        if type(key) is int:
            return key % size
        elif type(key) is str:
            sum = 0
            for pos in range(len(key)):
                sum = sum + ord(key[pos])

            return sum % size
        else:
            raise Exception("Invalid key in hash function: " + str(key))


    def rehash(self, oldhash, size):
        '''
        Returns a new hash value.  Used in the event of a collision.  Linear probing
        is the resolution.
        :param oldhash: The old hash value.
        :param size: The size of the hash table.
        :return: The new hash value.
        '''
        return (oldhash + 1) % size


    def get(self, key):
        '''
        Retrieves the data from the hash table at a particular key.
        :param key: The key to use to retrieve the data.
        :return: The data at a particular key.
        '''
        startslot = self.hashfunction(key, len(self.slots))

        data = None
        stop = False
        found = False
        position = startslot
        while self.slots[position] != None and \
                not found and not stop:
            if self.slots[position] == key:
                found = True
                data = self.data[position]
            else:
                position = self.rehash(position, len(self.slots))
                if position == startslot:
                    stop = True
        return data
    
    
    def is_prime(self, num):
        '''
        Returns true if the number is prime. Otherwise, returns false. This method is O(sqrt(n)).
        '''
        if num == 2:
            return True
        
        if num % 2 == 0:
            return False
        
        for x in range(3, int(math.sqrt(num)) + 1, 2):
            if num % x == 0:
                return False
            
        return True


    def __getitem__(self, key):
        '''
        Overrides getitem so that a programmer can use the square bracket notation.
        :param key: The key in the hash table.
        :return: The value at that key.
        '''
        return self.get(key)


    def __setitem__(self, key, data):
        '''
        Overrides setitem so that a programmer can use the square bracket notation.
        :param key: The key to use.
        :param data: The data to store at that key.
        :return: Nothing returned.
        '''
        self.put(key, data)
        
        
    def __len__(self):
        '''
        Returns the number of items in the hash table. This method is O(1).
        '''
        return self.length





