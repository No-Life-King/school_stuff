import cv2
import math
import numpy as np

from process_image import ProcessImage

orig = cv2.imread('pics/umbrella_fail.jpeg')
orig = cv2.resize(orig, (0, 0), 0, .75, .75)
radians = [(2 * math.pi * x) / 360 for x in range(361)]

for x in radians:
    img = np.copy(orig)
    img = ProcessImage.rotate(img, x)
    cv2.imwrite('video_source/rotation/' + str(x) + '.png', img)