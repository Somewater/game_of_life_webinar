class Grid {
private:
    bool* cells;

public:
    const int width;
    const int height;

    Grid(const int _width, const int _height) : width(_width), height(_height) {
        cells = new bool[width * height]();
        for (int i = 0; i < width * height; ++i) {
            cells[i] = false;
        }
    }

    void setAlive(int x, int y, bool alive) {
        x = x % width;
        y = y % height;
        if (x < 0) x += width;
        if (y < 0) y += height;
        cells[x * width + y] = alive;
    }

    bool getAlive(int x, int y) const {
        x = x % width;
        y = y % height;
        if (x < 0) x += width;
        if (y < 0) y += height;
        return cells[x * width + y];
    }

    int aliveNeighbours(int x, int y) const {
        int result = 0;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if ((i != x || j != y) && getAlive(i, j)) {
                    result += 1;
                }
            }
        }
        return result;
    }

    void stepAll(Grid* next) {
        step(next, 0, width, 0, height);
    }

    void step(Grid* next, int startX, int endX, int startY, int endY) {
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                int a = aliveNeighbours(x, y);
                if (a == 3) {
                    next->setAlive(x, y, true);
                } else if (a == 2) {
                    next->setAlive(x, y, getAlive(x, y));
                } else {
                    next->setAlive(x, y, false);
                }
            }
        }
    }
};