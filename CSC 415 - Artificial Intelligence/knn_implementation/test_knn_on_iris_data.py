import knn
import random


data_file = open('iris.data.txt', 'r')
training_data = []
training_labels = []

for line in data_file:
    line = line.split(',')
    if len(line) < 5:
        continue

    training_data.append([float(x) for x in line[:4]])
    training_labels.append(line[4].strip('\n'))


# for k in range(1, 51):
#     count = 0
#     for x in range(len(training_data)):
#         train, labels = training_data.copy(), training_labels.copy()
#         test_point = train.pop(x)
#         test_label = labels.pop(x)
#         knn_model = knn.knn(train, labels, k=k)
#         predicted = knn_model.predict(test_point)
#
#         if predicted != test_label:
#             # for y in range(len(test_point)):
#             #     print(test_point[y], end='\t')
#             #
#             # print()
#             count += 1
#
#     print(k, count)

mins = [4.3, 2.0, 1.0, 0.1]
maxes = [7.9, 4.4, 6.9, 2.5]

for x in range(3000):
    test_point = []

    for d in range(len(mins)):
        test_point.append((maxes[d] - mins[d]) * random.random() + mins[d])

    knn_model = knn.knn(training_data, training_labels, k=19)
    predicted = knn_model.predict(test_point)

    if predicted == 'Iris-virginica':
        print(test_point[0], '\t', test_point[1], '\t', test_point[2], '\t', test_point[3])

