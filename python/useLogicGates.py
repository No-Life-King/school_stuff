#!/usr/bin/env python3
from LogicGate import *

"""
Not((A and B) or (C and D))
"""
g1 = AndGate('g1')
g2 = AndGate('g2')
g3 = OrGate('g3')
g4 = NotGate('g4')

c1 = Connector(g1, g3)
c2 = Connector(g2, g3)
c3 = Connector(g3, g4)

print(g4.getOutput())


"""
   _____
A--|    \    |\
   | and )---|not >-_   _____
B--|____/    |/      \--|    \
                        | and )o
   _____              --|____/
C--|    \    |\     _/
   | and )---|not >-
D--|____/    |/

Not(A and B) and Not(C and D)

"""

g1 = AndGate('g1')
g2 = AndGate('g2')
g3 = NotGate('g3')
g4 = NotGate('g4')
g5 = AndGate('g5')

c1 = Connector(g1, g3)
c2 = Connector(g2, g4)
c3 = Connector(g3, g5)
c4 = Connector(g4, g5)

print(g5.getOutput())

