#!/usr/bin/env python3

ftpservers = open('5-2-17', 'r')
new_servers = open("bobby's_servers", 'w')

ftpservers = list(ftpservers)
lastMillion = ftpservers[-2000000:]

for line in lastMillion:
    new_servers.write(line)

