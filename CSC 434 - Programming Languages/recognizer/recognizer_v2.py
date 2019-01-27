import re

from lexical_analyzer import Lexical_Analyzer


def recognizer(tokens):
    if tokens.endswith('error'):
        return False

    #print('Token String:', tokens)

    assignment_pattern = re.compile('identifier assignment expression')
    expression_pattern = re.compile('((identifier|number) ?operator expression)|(identifier|number) $')

    while expression_pattern.search(tokens):
        tokens = expression_pattern.sub('expression', tokens)
        #print(tokens)

    tokens = assignment_pattern.sub('assign_stmt', tokens)
    #print(tokens)

    if tokens == 'assign_stmt' or tokens == 'expression':
        return True

    return False


analyzer = Lexical_Analyzer()
analyzer.read_table('state_transition_table')
code_file = open('examples.txt', 'r')

for line in code_file:
    line = line.rstrip('\n')
    tokens = analyzer.tokenize(line)
    print(line + ' tokenizes as ' + tokens)

    if not tokens.endswith('error'):
        is_valid = recognizer(tokens)
        if is_valid:
            print('\t' + line + ' is a valid statement')
        else:
            print('\t' + line + ' is an invalid statement')
