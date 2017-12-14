from cImage import *


def flip_horizontal(img):
    """
    Flip the specified image left to right and return the modified image
    """
    w = img.getWidth()
    h = img.getHeight()
    newimg = EmptyImage(w, h)
    
    for y in range(h):
        for  x in range(w):
           p = img.getPixel(x, y)
           newimg.setPixel(w - x - 1, y, p)

    return newimg


def sepia_tone(img):
    """
    Apply the sepia tone transformation to the specified image, and return the modified image
    """
    w = img.getWidth()
    h = img.getHeight()
    newimg = EmptyImage(w, h)
    
    for y in range(h):
        for  x in range(w):
            p = img.getPixel(x, y)
            pRed = p.getRed()
            pGreen = p.getGreen()
            pBlue = p.getBlue()
            red = dampen(int(pRed * .393 + pGreen * .769 + pBlue * .189))
            green = dampen(int(pRed * .349 + pGreen * .686 + pBlue * .168))
            blue = dampen(int(pRed * .272 + pGreen * .534 + pBlue * .131))
            newpixel = Pixel(red, green, blue)
            newimg.setPixel(x, y, newpixel)

    return newimg
    

def brighten_image(img, amount):
    """
    Apply the brighten image transformation to the specified image, and return the modified image
    """
    w = img.getWidth()
    h = img.getHeight()
    newimg = EmptyImage(w, h)
    
    for y in range(h):
        for  x in range(w):
            p = img.getPixel(x, y)
            pRed = p.getRed()
            pGreen = p.getGreen()
            pBlue = p.getBlue()
            red = dampen(pRed + amount)
            green = dampen(pGreen + amount)
            blue = dampen(pBlue + amount)
            newpixel = Pixel(red, green, blue)
            newimg.setPixel(x, y, newpixel)

    return newimg
    
def dampen(color):
    """
    This helper function is used by functions where the new color values might exceed their appropriate range
    """
    if (color > 255):
        color = 255
    return color


def convert_to_gray_scale(img):
    """
    Apply the gray scale transformation to the specified image, and return the modified image
    """
    w = img.getWidth()
    h = img.getHeight()
    newimg = EmptyImage(w, h)
    
    for y in range(h):
        for  x in range(w):
            p = img.getPixel(x, y)
            pRed = p.getRed()
            pGreen = p.getGreen()
            pBlue = p.getBlue()
            red = pRed * .21 + pGreen * .71 + pBlue * .07
            green = pRed * .21 + pGreen * .71 + pBlue * .07
            blue = pRed * .21 + pGreen * .71 + pBlue * .07
            newpixel = Pixel(int(red), int(green), int(blue))
            newimg.setPixel(x, y, newpixel)

    return newimg


def flip_vertical(img):
    """
    Flip the specified image upside down and return the modified image
    """
    w = img.getWidth()
    h = img.getHeight()
    newimg = EmptyImage(w, h)
    
    for y in range(h):
        for  x in range(w):
            p = img.getPixel(x, y)
            newimg.setPixel(x, h - y - 1, p)

    return newimg
    

def rotate_right_90(img):
    """
    Rotate the specified image 90 degrees to the right and return the modified image
    """
    w = img.getWidth()
    h = img.getHeight()
    newimg = EmptyImage(h, w)
    
    for y in range(h):
        for  x in range(w):
            p = img.getPixel(x, y)
            newimg.setPixel(h - y - 1, x, p)

    return newimg
    

#construct a new image that
#is a copy of img in which
#each pixel retains all the red
#and loses the green and blue components.
#the function returns the modified image
def red_filter(img):
    """
    Apply the red filter transformation to the specified image, and return the modified image
    """
    w = img.getWidth()
    h = img.getHeight()
    newimg = EmptyImage(w, h)
    
    for y in range(h):
        for  x in range(w):
            p = img.getPixel(x, y)
            newpixel = Pixel(p.getRed(), 0, 0)
            newimg.setPixel(x, y, newpixel)

    return newimg

def green_filter(img):
    """
    Apply the green filter transformation to the specified image and return the modified image
    """
    w = img.getWidth()
    h = img.getHeight()
    newimg = EmptyImage(w, h)
    
    for y in range(h):
        for  x in range(w):
            p = img.getPixel(x, y)
            newpixel = Pixel(0, p.getGreen(), 0)
            newimg.setPixel(x, y, newpixel)

    return newimg

#construct a new image that
#is a copy of img in which
#each pixel's red, green, and blue
#components are replaced with their complement with respect to 255
#the function returns the modified image
def negative(img):
    """
    Apply the negative transformation to the specified image, and return the modified image
    """
    w = img.getWidth()
    h = img.getHeight()
    newimg = EmptyImage(w, h)
    
    for y in range(h):
        for  x in range(w):
            p = img.getPixel(x, y)
            newpixel = Pixel(255-p.getRed(), 255-p.getGreen(), 255 - p.getBlue())
            newimg.setPixel(x, y, newpixel)

    return newimg


def display_image(img):
    """
    Display the specified image in a window
    """
    win = ImageWin(width = img.getWidth(), height = img.getHeight())
    img.draw(win)
    
    
"""
The following lines of code demonstrate the image manipulation functions of this file
"""

# prompt the user for a value to brighten an image by using the 'brighten_image' function
brightness = int(input("Brighten image by how much? (0-255): "))

#create an image corresponding to the file "flower.gif"
img = Image("flower.gif")
#show the image
display_image(img)
#apply the red_filter transformation to the image
img = red_filter(img)
#show the transformed image
display_image(img)

# apply the 'green_filter' to an image
display_image(green_filter(Image('flower.gif')))
# apply the 'sepia_tone' filter to an image
display_image(sepia_tone(Image("Penguins.gif")))
# apply the 'brighten_image' filter to an image
display_image(brighten_image(Image("Desert.gif"), brightness))
# apply the 'convert_to_gray_scale' filter to an image
display_image(convert_to_gray_scale(Image("flower.gif")))
# rotate an image 90 degrees to the right
display_image(rotate_right_90(Image("Penguins.gif")))
# flip an image up side down
display_image(flip_vertical(Image("Desert.gif")))
# flip an image from right to left
display_image(flip_horizontal(Image("flower.gif")))
# display the negative of an image
display_image(negative(Image("Penguins.gif")))

# input() added to keep the program from automatically exiting as soon as it's finished executing
input()