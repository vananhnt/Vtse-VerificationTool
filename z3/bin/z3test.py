import doctest
import z3

r = doctest.testmod(z3)
if r.failed != 0:
    exit(1)
