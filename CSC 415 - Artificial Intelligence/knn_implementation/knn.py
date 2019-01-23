import math


class knn:

    k = 1
    data = []
    labels = []
    classes = {}

    def __init__(self, data, labels, k=1):
        if len(data) != len(labels):
            raise Exception("Data and labels should be the same length.\nData length: " + str(len(data)) + ", Label length: " + str(len(labels)))

        self.k = k
        self.data = data
        self.labels = labels

        class_id = 0
        for label in labels:
            if label not in self.classes:
                self.classes[label] = class_id
                class_id += 1


    def predict(self, new_point):
        if len(new_point) != len(self.data[0]):
            raise Exception("New point does not have correct number of dimensions.\nGot " + str(len(new_point)) + ", expected " + str(len(self.data[0])))

        distances = []

        for x in range(len(self.data)):
            distances.append((self.calc_distance(self.data[x], new_point), x))

        distances.sort()
        k=self.k
        if k == 1:
            return self.labels[distances[0][1]]

        else:
            tie = True

            while tie:
                class_counts = {}
                for x in range(k):
                    point_class = self.labels[distances[x][1]]
                    if point_class in class_counts:
                        class_counts[point_class] += 1
                    else:
                        class_counts[point_class] = 1

                highest = 0
                prediction = ''
                for key in class_counts:
                    if class_counts[key] > highest:
                        highest = class_counts[key]
                        prediction = key

                num_classes_equal_to_highest = 0
                for key in class_counts:
                    if class_counts[key] == highest:
                        num_classes_equal_to_highest += 1
                        if num_classes_equal_to_highest > 1:
                            break

                if num_classes_equal_to_highest == 1:
                    return prediction

                k -= 1


    def calc_distance(self, p1, p2):
        sum_of_squares = 0

        for x in range(len(p1)):
            sum_of_squares += (p1[x] - p2[x]) ** 2

        return math.sqrt(sum_of_squares)