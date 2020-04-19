import sys
from grid import Grid
import time

if __name__ == '__main__':
    input = sys.argv[1]
    output = sys.argv[2]
    iterations = int(sys.argv[3])
    interactive = len(sys.argv) > 4

    width = 0
    height = 0

    with open(input) as f:
        for line in f:
            if (width == 0):
                width = len(line) // 2
            if line:
                height += 1

    grid = Grid(width, height)

    with open(input) as f:
        y = 0
        for l in f:
            x = 0
            for i, c in enumerate(l):
                if i % 2 == 0:
                    alive = c == 'X'
                    grid.set_alive(x, y, alive)
                    x += 1
            y += 1

    grid2 = Grid(width, height)
    for _ in range(iterations):
        grid.step_all(grid2)
        grid, grid2 = grid2, grid
        if interactive:
            print(chr(27) + "[2J")
            print(grid)
            time.sleep(0.1)

    with open(output, 'w') as f:
        for y in range(grid.height):
            for x in range(grid.width):
                a = grid.get_alive(x, y)
                f.write('X' if a else '.')
                f.write(' ')
            f.write('\n')