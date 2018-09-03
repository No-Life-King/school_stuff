import math
import random
import numpy as np


def init_weights(neurons, next_layer_neurons):
    weights = []
    for x in range(neurons):
        row = []
        for y in range(next_layer_neurons):
            row.append(np.random.normal())

        weights.append(row)

    return np.array(weights)


def sigmoid(z, scale=1.):
    return 1 / (1 + math.exp(-1*z*scale))


def loss_function(network_output, ground_truth):
    error_signals = []
    for x in range(len(network_output)):
        error_signals.append((ground_truth[x] - network_output[x]) * network_output[x] * (1 - network_output[x]))

    return error_signals


def train(training_data, labels, hidden_layer_neurons, epochs, learning_rate):
    output_layer_size = len(labels[0])
    #np.random.seed(1)
    weights_input_to_hidden = init_weights(len(training_data[0]), hidden_layer_neurons)
    weights_hidden_to_hidden = init_weights(hidden_layer_neurons, hidden_layer_neurons)
    weights_hidden_to_output = init_weights(hidden_layer_neurons, output_layer_size)
    hidden_bias = [1] * hidden_layer_neurons
    hidden_bias2 = [1] * hidden_layer_neurons
    output_bias = [1] * output_layer_size
    # print(weights_input_to_hidden)
    # print(weights_hidden_to_hidden)
    # print(weights_hidden_to_output)

    for e in range(epochs):
        tsse = 0
        rmse = 10
        np.random.seed(1)
        np.random.shuffle(training_data)
        np.random.seed(1)
        np.random.shuffle(labels)
        for i in range(len(training_data)):
            input = training_data[i]
            # print(input, labels[i])
            hidden_output, hidden_output2, output = feed_forward(hidden_bias, hidden_bias2, input, output_bias,
                                                                 weights_hidden_to_hidden, weights_hidden_to_output,
                                                                 weights_input_to_hidden)

            error_signals = loss_function(output, labels[i])

            # print(output)
            # print(error_signals)

            hidden_error_signals1 = hidden_error_signals(error_signals, hidden_output, weights_hidden_to_output)

            hidden_error_signals2 = hidden_error_signals(hidden_error_signals1, hidden_output2,
                                                         weights_hidden_to_hidden)

            weights_hidden_to_output, output_bias = adjust_weights(error_signals, hidden_output2, learning_rate,
                                                                   output_bias, weights_hidden_to_output)

            # print(weights_hidden_to_output)
            # print(output_bias)
            #
            # print(hidden_error_signals1)

            weights_hidden_to_hidden, hidden_bias2 = adjust_weights(hidden_error_signals1, hidden_output, learning_rate,
                                                                    hidden_bias2, weights_hidden_to_hidden)

            weights_input_to_hidden, hidden_bias = adjust_weights(hidden_error_signals2, input, learning_rate,
                                                                  hidden_bias, weights_input_to_hidden)

            for x in range(len(output)):
                tsse += (output[x] - labels[i][x])**2

            if e > 0 and e % 100 == 0 and i == 5:
                rmse = math.sqrt(tsse / 18)
                print(rmse)

            if rmse < .1:
                break

        if rmse < .1:
            break

    correct = 0
    for x in range(1000):
        index = random.randint(0, 5)
        character = training_data[index]
        label = labels[index]
        # for y in range(25):
            # if random.random() < .05:
            #     if character[y] == 0:
            #         character[y] = 1
            #     else:
            #         character[y] = 0

        hidden_output, hidden_output2, output = feed_forward(hidden_bias, hidden_bias2, character, output_bias,
                                                                     weights_hidden_to_hidden, weights_hidden_to_output,
                                                                     weights_input_to_hidden)

        result = []
        for out in output:
            if out > .5:
                result.append(1)
            else:
                result.append(0)

        error = False
        for z in range(len(result)):
            if result[z] != label[z]:
                error = True
                break

        if not error:
            correct += 1

        predicted = decode_char(result)
        actual = decode_char(label)

        #print("Predicted: " + predicted + ", Actual: " + actual)

    print("Accuracy: " + str(correct/1000))


def decode_char(label):
    char = ''
    code = 0
    for x in range(len(label)):
        code += label[2-x] * 2**x

    if code == 0:
        char = '+'
    elif code == 1:
        char = '-'
    elif code == 2:
        char = '\\'
    elif code == 3:
        char = '/'
    elif code == 4:
        char = 'X'
    elif code == 5:
        char = '|'

    return char


def feed_forward(hidden_bias, hidden_bias2, input, output_bias, weights_hidden_to_hidden, weights_hidden_to_output,
                 weights_input_to_hidden):
    hidden_output = []
    for x in range(len(weights_input_to_hidden[0])):
        node_output = hidden_bias[x]
        for y in range(len(input)):
            node_output += weights_input_to_hidden[y][x] * input[y]

        hidden_output.append(sigmoid(node_output))
    # print(hidden_output)
    hidden_output2 = []
    for x in range(len(weights_hidden_to_hidden[0])):
        node_output = hidden_bias2[x]
        for y in range(len(weights_hidden_to_hidden[0])):
            node_output += weights_hidden_to_hidden[y][x] * hidden_output[y]

        hidden_output2.append(sigmoid(node_output))
    # print(hidden_output2)
    output = []
    for x in range(len(weights_hidden_to_output[0])):
        node_output = output_bias[x]
        for y in range(len(hidden_output2)):
            node_output += weights_hidden_to_output[y][x] * hidden_output2[y]

        output.append(sigmoid(node_output))
    return hidden_output, hidden_output2, output


def hidden_error_signals(error_signals, hidden_output, weights_hidden_to_output):
    hidden_error_signals = []
    for x in range(len(hidden_output)):
        hidden_error_signal = 0
        for y in range(len(error_signals)):
            hidden_error_signal += error_signals[y] * weights_hidden_to_output[x][y]

        hidden_error_signal *= hidden_output[x] * (1 - hidden_output[x])
        hidden_error_signals.append(hidden_error_signal)

    return hidden_error_signals


def adjust_weights(previous_error_signal, layer_output, learning_rate, biases, weights):
    for x in range(len(previous_error_signal)):
        for y in range(len(weights)):
            weights[y][x] += learning_rate * previous_error_signal[x] * layer_output[y]

        biases[x] += learning_rate * previous_error_signal[x]

    return weights, biases


plus = [0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0]
minus = [0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0]
backslash = [1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1]
forward_slash = [0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0]
X = [1,0,0,0,1,0,1,0,1,0,0,0,1,0,0,0,1,0,1,0,1,0,0,0,1]
pipe = [0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0]

training_data = np.array([plus, minus, backslash, forward_slash, X, pipe])
labels = np.array([[0,0,0], [0,0,1], [0,1,0], [0,1,1], [1,0,0], [1,0,1]])

train(training_data, labels, 128, 10001, .3)

