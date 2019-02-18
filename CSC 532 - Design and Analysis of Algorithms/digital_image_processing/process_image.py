import numpy as np

class ProcessImage:

    def grayscale_avg(img):
        img = np.mean(img, 2, dtype=np.uint16)

        return img.astype(np.uint8)

    def grayscale_weighted(img):
        img = img.astype(np.float64)
        scalars = np.array([.2126, .7152, .0722])
        #scalars = np.array([.2126, .0722, .7152,])
        weighted_img = np.multiply(img, scalars)

        return np.sum(weighted_img, 2).astype(np.uint8)

    def negative(img):
        img = np.subtract(255, img)
        return img

    def solarize(img, threshold=192):

        for x in range(len(img)):
            for y in range(len(img[0])):
                for z in range(len(img[0][0])):
                    img[x][y][z] = 255 - img[x][y][z] if img[x][y][z] >= threshold else img[x][y][z]

        return img
