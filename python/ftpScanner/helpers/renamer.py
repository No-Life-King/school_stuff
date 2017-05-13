import os


def pad(num):
    if len(num) == 1:
        num = '0' + num
    return num

relPath = '../ftpPortScans'
port_scans = os.listdir(relPath)

for filename in port_scans:
    date, extension = filename.split('.')
    if extension == 'csv':
        m, d, y = date.split('-')
        y = '20' + y

        m = pad(m)
        d = pad(d)
        new_filename = y + '-' + m + '-' + d

        os.rename(relPath + '/' + filename, relPath + '/' + new_filename)