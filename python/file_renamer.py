#!/usr/bin/env python3
import sys
import os


def rename_all(target_directory):
    count = 0
    for path, dirs, files in os.walk(target_directory):
        for filename in files:
            if "female" in filename:
                new_filename = filename.replace("female", "male", 1)
            else:
                new_filename = filename.replace("male", "female", 1)

            if filename != new_filename:
                os.rename(path + "/" + filename, path + "/" + new_filename)
                count += 1

    print("Finished! Renamed", count, "files.")


if len(sys.argv) == 1:
    no_args_prompt = input("Would you like to rename the files in this directory? (y/n): ")
    if no_args_prompt.lower() != 'y':
        print("Did nothing.")
        exit()
    else:
        rename_all(os.getcwd())
elif len(sys.argv) == 2:
    if os.path.isdir(sys.argv[1]):
        rename_all(sys.argv[1])
    else:
        print("Error: " + sys.argv[1] + " is not a valid directory.")
else:
    print("Too many arguments. Got:", sys.argv)
