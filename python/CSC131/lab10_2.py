teams_file = open('teams.txt', 'r')
teams = []
points = [0]*20

for team in teams_file:
    teams.append(team.strip())
    
games_file = open('games.txt', 'r')

for match in games_file:
    
    clubs, score = match.strip().split(' , ')
    team1, team2 = clubs.strip().split(' - ')
    team1 = team1.split(' . ')
    team1 = team1.pop(1)
    team1_score, team2_score = score.strip().split(' - ')
    team1_score = int(team1_score)
    team2_score = int(team2_score)
    
    team1_index = teams.index(team1)
    team2_index = teams.index(team2)
    
    if team1_score > team2_score:
        points[team1_index] += 3
    elif team1_score < team2_score:
        points[team2_index] += 3
    else:
        points[team1_index] += 1
        points[team2_index] += 1

print('Current Standings:')
for x in range(20):
    print("\t" + teams[x] + " - " + str(points[x]))

print('\nTop 3:')
for x in range(3):
    largest = points[0]
    largest_index = 0
    for x in range(len(points)):
        if points[x] > largest:
            largest = points[x]
            largest_index = x
            
    print('\t' + teams.pop(largest_index) + " - " + str(points.pop(largest_index)))
    
print('\nBottom 3:')
for x in range(3):
    smallest = points[0]
    smallest_index = 0
    for x in range(len(points)):
        if points[x] < smallest:
            smallest = points[x]
            smallest_index = x
            
    print('\t' + teams.pop(smallest_index) + ' - ' + str(points.pop(smallest_index)))
    