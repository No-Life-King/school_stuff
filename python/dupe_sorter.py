#!/usr/bin/env python3
import hashlib
import os
import shutil


def hash_file(path):
    file_hash = hashlib.md5()
    with open(path, 'rb') as hashme:
        while True:
            chunk = hashme.read(1024)
            if not chunk:
                break
            file_hash.update(chunk)
    return file_hash.hexdigest()


pic_dict = {}
for x in range(18, 99):
    filenames = os.listdir(str(x))
    for picture in filenames:
        path = str(x) + '/' + picture
        if x not in pic_dict:
            pic_dict[x] = {}
        folder = pic_dict[x]
        folder[path] = hash_file(path)


multiple_folders = 0
same_folder = 0
done = {}
for directory in pic_dict:
    other_dirs = pic_dict.copy()
    current_dir = other_dirs.pop(directory)
    dir_list = list(current_dir.keys())
    for picture in dir_list:
        current_pic = current_dir.pop(picture)
        for other_folder in other_dirs:
            if current_pic in other_dirs[other_folder].values():
                multiple_folders += 1
                try:
                    shutil.move(picture, 'found in multiple folders')
                except Exception as e:
                    print(e)

        if current_pic in done:
            try:
                same_folder += 1
                shutil.move(picture, 'duplicate in same folder')
            except Exception as e:
                print(e)
        else:
            done[current_pic] = None

print('Duplicates found in multiple folders:', multiple_folders)
print('Duplicates found in the same folder:', same_folder)