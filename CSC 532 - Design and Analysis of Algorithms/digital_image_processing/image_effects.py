import cv2
import numpy as np

from process_image import ProcessImage


def opener():
    seattle_skyline = cv2.imread('pics/seattle_skyline.jpg', cv2.COLOR_BGR2RGB)
    seattle_skyline = cv2.resize(seattle_skyline, (0, 0), 0, .4, .4)
    cv2.imshow('Digital Image Processing', seattle_skyline)
    key_code = cv2.waitKey(0)

    return key_code


def grayscale_intensity():
    seattle_skyline = cv2.imread('pics/seattle_skyline.jpg', cv2.COLOR_BGR2RGB)
    seattle_skyline = cv2.resize(seattle_skyline, (0, 0), 0, .4, .4)
    gray_img = ProcessImage.grayscale_avg(seattle_skyline)
    cv2.imshow('Digital Image Processing', gray_img)
    key_code = cv2.waitKey(0)

    return key_code


def grayscale_adjusted():
    seattle_skyline = cv2.imread('pics/seattle_skyline.jpg', cv2.COLOR_BGR2RGB)
    seattle_skyline = cv2.resize(seattle_skyline, (0, 0), 0, .4, .4)
    gray_img = ProcessImage.grayscale_weighted(seattle_skyline)
    cv2.imshow('Digital Image Processing', gray_img)
    key_code = cv2.waitKey(0)

    return key_code


def denny_park():
    tha_park = cv2.imread('pics/denny_park.jpg', cv2.COLOR_BGR2RGB)
    tha_park = cv2.resize(tha_park, (0, 0), 0, .75, .75)
    cv2.imshow('Digital Image Processing', tha_park)
    key_code = cv2.waitKey(0)

    return key_code


def negatize():
    tha_park = cv2.imread('pics/denny_park.jpg', cv2.COLOR_BGR2RGB)
    tha_park = cv2.resize(tha_park, (0, 0), 0, .75, .75)
    negative_img = ProcessImage.negative(tha_park)
    cv2.imshow('Digital Image Processing', negative_img)
    key_code = cv2.waitKey(0)

    return key_code


def solarize():
    tha_park = cv2.imread('pics/denny_park.jpg', cv2.COLOR_BGR2RGB)
    tha_park = cv2.resize(tha_park, (0, 0), 0, .75, .75)
    solarized_img = ProcessImage.solarize(tha_park)
    cv2.imshow('Digital Image Processing', solarized_img)
    key_code = cv2.waitKey(0)

    return key_code


effects = [opener, grayscale_intensity, grayscale_adjusted, denny_park, negatize]
effect_index = 0
key_code = 0
while key_code != 113:
    effect = effects[effect_index]
    key_code = effect()

    if key_code == 100 or key_code == 32 or key_code == 83:
        effect_index += 1

    elif key_code == 97 or key_code == 81:
        effect_index -= 1

    effect_index = max((0, effect_index))
    effect_index = min((len(effects)-1, effect_index))








