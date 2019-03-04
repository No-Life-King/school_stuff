import cv2
import math
import random
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

    def conv2d(img):
        # filter = np.array([[1/math.sqrt(2),1,1/math.sqrt(2)],
        #                    [1,0,1],
        #                    [1/math.sqrt(2),1,1/math.sqrt(2)]])

        # filter = np.array([[1 / (2 * math.sqrt(2)), 1 / math.sqrt(5), 1 / 2, 1 / math.sqrt(5), 1 / (2 * math.sqrt(2))],
        #                    [1 / math.sqrt(5), 1 / math.sqrt(2), 1, 1 / math.sqrt(2), 1 / math.sqrt(5)],
        #                    [1 / 2, 1, 0, 1, 1 / 2],
        #                    [1 / math.sqrt(5), 1 / math.sqrt(2), 1, 1 / math.sqrt(2), 1 / math.sqrt(5)],
        #                    [1 / (2 * math.sqrt(2)), 1 / math.sqrt(5), 1 / 2, 1 / math.sqrt(5), 1 / (2 * math.sqrt(2))]])

        filter = np.array([[0.35355339059327373, 0.4472135954999579, 0.5, 0.4472135954999579, 0.35355339059327373],
                           [0.4472135954999579, 0.7071067811865475, 1, 0.7071067811865475, 0.4472135954999579],
                           [0.5,                1,                  0,                  1,                  0.5],
                           [0.4472135954999579, 0.7071067811865475, 1, 0.7071067811865475, 0.4472135954999579],
                           [0.35355339059327373, 0.4472135954999579, 0.5, 0.4472135954999579, 0.35355339059327373]])

        # filter = np.array([[0.7071067811865475,1,0.7071067811865475],
        #                    [1,                 0,                 1],
        #                    [0.7071067811865475,1,0.7071067811865475]])

        filter_size = len(filter)
        height, width = img.shape[:2]
        convolved = np.empty([height - (filter_size - 1), width - (filter_size - 1), img.shape[2]])
        #convolved = np.empty([height - 2, width - 2])
        img = img.astype(np.float64)
        for x in range(width - (filter_size - 1)):
            for y in range(height - (filter_size - 1)):
                sum = [0, 0, 0]
                for z in range(img.shape[2]):
                    for a in range(filter_size):
                        for b in range(filter_size):
                            sum[z] += filter[b][a] * img[y+b][x+a][z]

                convolved[y][x] = np.divide(np.array(sum), np.sum(filter))

        return convolved.astype(np.uint8)

    def boundaries(img):
        filter1 = np.array([[-1, 0, 1],
                            [-1, 0, 1],
                            [-1, 0, 1]])

        filter2 = np.array([[-1, -1, -1],
                            [0, 0, 0],
                            [1, 1, 1]])

        filter_size = len(filter1)
        height, width = img.shape[:2]
        convolved = np.empty([height - (filter_size - 1), width - (filter_size - 1), img.shape[2]])
        #convolved = np.empty([height - 2, width - 2])
        img = img.astype(np.float64)
        for x in range(width - (filter_size - 1)):
            for y in range(height - (filter_size - 1)):
                sum1 = [0, 0, 0]
                sum2 = [0, 0, 0]
                for z in range(img.shape[2]):
                    for a in range(filter_size):
                        for b in range(filter_size):
                            sum1[z] += filter1[b][a] * img[y+b][x+a][z]
                            sum2[z] += filter2[b][a] * img[y+b][x+a][z]

                #convolved[y][x] = np.divide(np.array(sum), np.sum(filter))
                convolved[y][x] = np.add(np.absolute(sum1), np.absolute(sum2))

        return convolved.astype(np.uint8)

    def rotate(img, theta):
        height, width, depth = img.shape[:3]
        rotated = np.empty([height, width, depth])
        h, k = (int(round(width/2))-1, int(round(height/2))-1)

        for y in range(height):
            for x in range(width):
                i, j = x - h, y - k
                new_x = int(round(i * math.cos(-theta) + j * -math.sin(-theta))) + h
                new_y = int(round(i * math.sin(-theta) + j * math.cos(-theta))) + k

                if new_x < 0 or new_y < 0 or new_x >= width or new_y >= height:
                    rotated[y][x] = np.array([0, 0, 0])
                else:
                    rotated[y][x] = img[new_y][new_x]

        return rotated.astype(np.uint8)

    def composite_images(img):
        height, width, depth = img.shape[:3]
        blue_filter = np.random.normal(random.randint(-5, 10), random.randint(0, 20), (height, width, 1))
        green_filter = np.random.normal(random.randint(-5, 5), random.randint(0, 20), (height, width, 1))
        bgr_filter = np.append(blue_filter, green_filter, axis=2)
        red_filter = np.random.normal(random.randint(-5, 5), random.randint(0, 20), (height, width, 1))
        bgr_filter = np.append(red_filter, bgr_filter, axis=2)
        img = np.subtract(img, bgr_filter)
        img = np.clip(img, 0, 255)

        return img.astype(np.uint8)

    def fade(img1, img2, fade=0.5):
        faded = np.add(np.multiply(img1, fade), np.multiply(img2, 1 - fade))
        return faded.astype(np.uint8)

    def equalize(img):
        histo = [0]*256

        for x in range(img.shape[0]):
            for y in range(img.shape[1]):
                    histo[img[x][y]] += 1

        cum_histo = [0] * 256
        cum_histo[0] = histo[0]
        for x in range(1, 256):
            cum_histo[x] = histo[x] + cum_histo[x-1]

        l = 0
        for bin in histo:
            if bin > 0:
                l += 1

        equalized = np.empty([img.shape[0], img.shape[1]], dtype=np.uint8)
        cum_histo_min = 1234567489

        for bin in cum_histo:
            if bin < cum_histo_min and bin != 0:
                cum_histo_min = bin


        for x in range(img.shape[0]):
            for y in range(img.shape[1]):
                equalized[x][y] = ProcessImage.histo_equa_formula(img[x][y], cum_histo, cum_histo_min, l)

        new_histo = [0]*256
        for x in range(img.shape[0]):
            for y in range(img.shape[1]):
                    new_histo[equalized[x][y]] += 1

        new_cum_histo = [0] * 256
        new_cum_histo[0] = new_histo[0]
        for x in range(1, 256):
            new_cum_histo[x] = new_histo[x] + new_cum_histo[x - 1]

        for v in new_cum_histo:
            print(v/max(new_cum_histo))

        return equalized

    def rgb_equalize(img):
        r_histo = [0] * 256
        g_histo = [0] * 256
        b_histo = [0] * 256
        histos = (r_histo, g_histo, b_histo)

        for x in range(img.shape[0]):
            for y in range(img.shape[1]):
                for z in range(img.shape[2]):
                    histos[z][img[x][y][z]] += 1

        r_cum_histo = [0] * 256
        g_cum_histo = [0] * 256
        b_cum_histo = [0] * 256

        r_cum_histo[0] = r_histo[0]
        g_cum_histo[0] = g_histo[0]
        b_cum_histo[0] = b_histo[0]

        cum_histos = (r_cum_histo, g_cum_histo, b_cum_histo)


        for x in range(1, 256):
            for z in range(3):
                cum_histos[z][x] = histos[z][x] + cum_histos[z][x-1]

        r_l = 0
        g_l = 0
        b_l = 0

        ls = [r_l, g_l, b_l]

        for z in range(3):
            for bin in histos[z]:
                if bin > 0:
                    ls[z] += 1

        equalized = np.empty([img.shape[0], img.shape[1], img.shape[2]], dtype=np.uint8)

        rmin = 123456789
        gmin = 123456789
        bmin = 123456789

        cum_histo_mins = [rmin, gmin, bmin]

        for z in range(3):
            for bin in cum_histos[z]:
                if bin < cum_histo_mins[z] and bin != 0:
                    cum_histo_mins[z] = bin


        for x in range(img.shape[0]):
            for y in range(img.shape[1]):
                for z in range(img.shape[2]):
                    equalized[x][y][z] = ProcessImage.histo_equa_formula(img[x][y][z], cum_histos[z], cum_histo_mins[z], ls[z])

        return equalized

    def histo_equa_formula(value, cum_histo, min_cum_histo, l):
        numerator = float(cum_histo[value]) - float(min_cum_histo)
        denominator = float(max(cum_histo)) - float(min_cum_histo)
        fdsa = round(numerator / denominator * float(l-1))

        return fdsa

