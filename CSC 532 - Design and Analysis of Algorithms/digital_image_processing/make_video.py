import os
import cv2

root_dir = 'video_source/fade/'
fourcc = cv2.VideoWriter_fourcc(*'DIVX')
height, width = cv2.imread(root_dir + '0.png').shape[:2]

files = os.listdir(root_dir)
#files.sort()

out = cv2.VideoWriter('output.avi', fourcc, 24, (width, height))

for x in range(256):
    out.write(cv2.imread(root_dir + str(x) + '.png'))
