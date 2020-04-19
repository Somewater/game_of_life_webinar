#!/usr/bin/env python3
import sys

if __name__ == '__main__':
    output = sys.argv[1]
    size = int(sys.argv[2])

    grid = dict()

    # . . . . .
    # . X X X .
    # . . . X .
    # . . X . .
    # . . . . .
    grid[(1, 1)] = True
    grid[(2, 1)] = True
    grid[(3, 1)] = True
    grid[(3, 2)] = True
    grid[(2, 3)] = True

    with open(output, 'w') as f:
        for y in range(size):
            for x in range(size):
                alive = grid.get((x, y))
                if alive:
                    f.write('X ')
                else:
                    f.write('. ')
            f.write('\n')

    print('Grid %s with size %dx%d' % (output, size, size))