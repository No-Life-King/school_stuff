def read_file(filename):
    """
    Reads a file and returns two lists and two vote totals
    """
    clinton_votes = 0
    trump_votes = 0
    
    clinton_states = []
    trump_states = []
    
    file = open(filename, 'r')
    file.readline()
    for state in file:
        state = state.strip()
        state, ev, c, t = state.split(',')
        
        if c == '1':
            clinton_votes += int(ev)
            clinton_states.append(state)
        else:
            trump_votes += int(ev)
            trump_states.append(state)
            
    return clinton_votes, trump_votes, clinton_states, trump_states
        
        
        
        
        
cv, tv, cs, ts = read_file('electoralVotes.txt')
print("Clinton's Electoral Votes: \t" + str(cv))
print("Trump's Electoral Votes: \t" + str(tv))
print()
print("\t~Clinton's States~")
print(cs)
print("\n\t~Trump's States~")
print(ts)