import os
import datetime

"""
Uses a couple years worth of port 21 scan results to generate a table.
Calculates how long it's been since the scan, how many servers have been
stable since the scan was performed, and how many servers are generally
stable.
"""

# the directory containing the port scan results
relPath = '../ftpPortScans'

# get the file names and sort them
port_scans = os.listdir(relPath)
port_scans.sort()
port_scans.reverse()
today = datetime.date.today()

# initiate some empty sets
stable_servers = set()
last_scan = set()
next_to_last_scan = set()
count = 0


for filename in port_scans:

    # create a date out of the file name
    y, m, d = filename.split('-')
    scan_date = datetime.date(int(y), int(m), int(d))

    # calculate how many days it's been since the scan
    days_ago = (today - scan_date).days

    # make a set out of the ip addresses in the file
    current_scan = set()
    scan_file = open(relPath + '/' + filename, 'r')
    for address in scan_file:
        current_scan.add(address.strip())

    # calculate and print the results appropriately
    if count == 0:
        stable_servers = current_scan.copy()
        next_to_last_scan = stable_servers.copy()
        print(str(days_ago) + '\t' + str(len(stable_servers)))
    elif (count == 1):
        last_scan = current_scan.copy()
        stable_servers.intersection_update(current_scan)
        print(str(days_ago) + '\t' + str(len(stable_servers)))
    else:
        stable_servers.intersection_update(current_scan)
        stable_last_two_scans = current_scan.intersection(last_scan.intersection(next_to_last_scan))
        print(str(days_ago) + '\t' + str(len(stable_servers)) + '\t' + str(len(stable_last_two_scans)))

        next_to_last_scan = last_scan.copy()
        last_scan = current_scan.copy()

    count += 1


# write the ip addresses of the consistently stable servers to a file for later use
stable_servers_file = open('stable_servers', 'w')
for server in stable_servers:
    stable_servers_file.write(server + '\n')
stable_servers_file.close()