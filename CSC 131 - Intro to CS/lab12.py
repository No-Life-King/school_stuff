import random


def get_data(filename):
    """
    This function retrieves the names of the cities and the distances between
    them from a file. It builds and returns a dictionary of city names and a
    list of distances.
    """
    indicies = {}
    
    distances_file = list(open(filename, 'r'))
    # this list comprehension builds a square array with values initialized as 0
    distances = [[0 for x in range(len(distances_file))] for y in range(len(distances_file))]
    
    # gets the city name and distances from each line and populates the dictionary and array
    index = 0
    for line in distances_file:
        line = line.strip().split(' ')
        city = line.pop(0)
        indicies[city] = index
        for x in range(len(line)):
            distances[x][index] = int(line[x])
            distances[index][x] = int(line[x])
        index += 1
    
    return indicies, distances
    

def test_distances(indicies, distances):
    """
    This is just some debugging code that helped me make sure the array
    of distances was being built properly. It should print the same distance
    from both directions.
    """
    
    # pick random cities
    city1 = random.randint(0, 9)
    city2 = random.randint(0, 9)
    
    # get the city names by their index values
    city1_name = ''
    for key in indicies:
        if indicies[key] == city1:
            city1_name = key
    
    city2_name = ''
    for key in indicies:
        if indicies[key] == city2:
            city2_name = key
    
    # print the city names and the distance between them
    # distance should be the same whether it's A->B or B->A
    print("The distance between " + city1_name + " and " + city2_name + " is " +
        str(distances[city1][city2]) + " miles or " + str(distances[city2][city1]) + " miles.")
    
    
def tripLength(indicies, distances, cities):
    """
    This function calculates the total distance of a trip, given a list of city names.
    """
    distance = 0
    # copy the list so that the original isn't destroyed
    the_cities = cities[:]
    # until you're at the last city
    while len(the_cities) > 1:
        # get the index value of the first city and remove it from the list
        x = indicies[the_cities.pop(0)]
        # get the index value of the second city
        y = indicies[the_cities[0]]
        # use the array of distances to find the corresponding distance
        distance += distances[x][y]
        
    return distance
   
    
def display_results(distance, cities):
    """
    Prints the results of the distance calculation in a nice and neat way.
    """
    print("The distance to go from ", end='')
    last_city = cities.pop()
    for city in cities:
        print(city + " to ", end='')
        
    print(last_city + " is " + str(distance) + " miles.")
    

# get the indicies dictionary and the distances array from a text file
indicies, distances = get_data("distances.txt")
# a list of cities to be traveled to
some_cities = ['Greensboro', 'Raleigh', 'Chapel-Hill', 'Elizabeth-City', 'Wilmington']
# get the distance of the trip
distance = tripLength(indicies, distances, some_cities)
# print the results
display_results(distance, some_cities)


