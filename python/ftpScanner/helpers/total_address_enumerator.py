import os

"""
Finds the total number of unique addresses that have hosted a server 
on port 21 during the duration of the specified port scans.
"""

relPath = '../ftpPortScans'
port_scans = os.listdir(relPath)
port_scans.sort()
port_scans.reverse()

master_set = set()

for filename in port_scans:

    scan_file = open(relPath + '/' + filename, 'r')
    for address in scan_file:
        master_set.add(address.strip())

    print(len(master_set))