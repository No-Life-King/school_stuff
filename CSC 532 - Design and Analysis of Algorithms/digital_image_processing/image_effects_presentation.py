import cv2
import numpy as np

from process_image import ProcessImage


def opener():
    return just_display_it('pics/seattle_skyline.jpg', 0.4)


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
    return just_display_it('pics/denny_park.jpg', 0.75)


def negatize():
    tha_park = cv2.imread('pics/denny_park.jpg', cv2.COLOR_BGR2RGB)
    tha_park = cv2.resize(tha_park, (0, 0), 0, .75, .75)
    negative_img = ProcessImage.negative(tha_park)
    cv2.imshow('Digital Image Processing', negative_img)
    key_code = cv2.waitKey(0)

    return key_code


def washington_cc():
    return just_display_it('pics/washington_convention_center.jpg', 0.4)


def eighth_filter():
    return just_display_it('pre-rendered/eighth_filter.png', 1.0)


def eighth_filter2():
    return just_display_it('pre-rendered/eighth_filter_(take-two).png', 1.0)


def ninth_filter():
    return just_display_it('pre-rendered/ninth_filter.png', 1.0)


def distance_weighted():
    return just_display_it('pre-rendered/distance_weighted_filter.png', 1.0)


def distance_weighted_5x5():
    return just_display_it('pre-rendered/distance_weighted_filter_5x5.png', 1.0)


def prewitts_gradient():
    return just_display_it('pre-rendered/prewitts_gradient.png', 1.0)


def prewitts_gradient_smoothed():
    return just_display_it('pre-rendered/prewitts_gradient_smoothed.png', 1.0)


def prewitts_gradient_smoothed_5x5():
    return just_display_it('pre-rendered/prewitts_gradient_smoothed_5x5.png', 1.0)


def just_display_it(rel_path, ratio):
    convention_center = cv2.imread(rel_path, cv2.COLOR_BGR2RGB)
    convention_center = cv2.resize(convention_center, (0, 0), 0, ratio, ratio)
    cv2.imshow('Digital Image Processing', convention_center)
    key_code = cv2.waitKey(0)

    return key_code

def elliot_bay():
    bay = cv2.imread('pre-rendered/elliot_bay_shoop.png')
    bay = ProcessImage.composite_images(bay)
    bay = cv2.resize(bay, (0, 0), 0, .4, .4)
    bay = ProcessImage.conv2d(bay)
    cv2.imshow('Digital Image Processing', bay)
    cv2.imwrite('pre-rendered/elliot_bay5.png', bay)
    key_code = cv2.waitKey(0)

    return key_code



effects = [opener, grayscale_intensity, grayscale_adjusted, denny_park, negatize, washington_cc,
           eighth_filter, eighth_filter2, ninth_filter, distance_weighted, distance_weighted_5x5,
           prewitts_gradient, prewitts_gradient_smoothed, prewitts_gradient_smoothed_5x5]

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




