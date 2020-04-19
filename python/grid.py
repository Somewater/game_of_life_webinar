class Grid:
    def __init__(self, width, height):
        self.width = width
        self.height = height
        self.cells = [[False] * height for _ in range(width)]

    def set_alive(self, x, y, alive):
        x = x % self.width
        y = y % self.height
        self.cells[x][y] = alive

    def get_alive(self, x, y):
        x = x % self.width
        y = y % self.height
        return self.cells[x][y]

    def alive_neighbours(self, x, y):
        result = 0
        for i in range(x - 1, x + 2):
            for j in range(y - 1, y + 2):
                if (i != x or j != y) and self.get_alive(i, j):
                    result += 1
        return result

    def step_all(self, next):
        self.step(next, 0, self.width, 0, self.height)

    def step(self, next, startX, endX, startY, endY):
        for x in range(startX, endX):
            for y in range(startY, endY):
                a = self.alive_neighbours(x, y)
                if a == 3:
                    next.set_alive(x, y, True)
                elif a == 2:
                    next.set_alive(x, y, self.get_alive(x, y))
                else:
                    next.set_alive(x, y, False)

    def __str__(self):
        return '\n'.join([
            ''.join([
                'X ' if self.get_alive(x, y) else '. '
                for x in range(self.width)
            ])
            for y in range(self.height)
        ])
