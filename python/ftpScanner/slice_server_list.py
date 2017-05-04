#!/usr/bin/env python3

ftpservers = open('unscanned1', 'r')
new_servers = open('new_servers', 'w')

ftpservers = list(ftpservers)
lastMillion = ftpservers[-630000:]

for line in lastMillion:
    new_servers.write(line)

