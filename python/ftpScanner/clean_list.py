#!/usr/bin/env python3
"""
This file removes all of the already scanned IP addresses
in the old list from the new list. In pseudocode, it is
simply "if line is in file1: delete line from file2".
"""


old = open('old_ftpservers', 'r')
new = open('ftpservers', 'r')
results = open('ftpservers2', 'w')

d = {}
d2 = {}

for x in old:
    d[x.strip()] = None
    
count = 0
for y in new:
    if y.strip() not in d:
        results.write(y)
    else:
        count += 1
        
        
print('Done. ' + str(count) + ' IPs cleaned.')
        

        
