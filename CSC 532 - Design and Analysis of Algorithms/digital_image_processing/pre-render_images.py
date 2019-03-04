import cv2
import numpy as np

from process_image import ProcessImage


img1 = cv2.imread('pics/neko.jpg', cv2.COLOR_BGR2RGB)
img2 = cv2.imread('pics/phil.jpg', cv2.COLOR_BGR2RGB)

for x in range(256):
    faded = ProcessImage.fade(img1, img2, x/256)
    cv2.imwrite('video_source/fade/' + str(x) + '.png', faded)

cv2.imshow('Digital Image Processing', faded)

cv2.waitKey(0)