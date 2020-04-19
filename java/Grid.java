public class Grid {
    public final int width;
    public final int height;
    private final boolean[][] cells;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new boolean[width][height];
    }

    void setAlive(int x, int y, boolean alive) {
        x = x % width;
        y = y % height;
        if (x < 0) x += width;
        if (y < 0) y += height;
        cells[x][y] = alive;
    }

    boolean getAlive(int x, int y) {
        x = x % width;
        y = y % height;
        if (x < 0) x += width;
        if (y < 0) y += height;
        return cells[x][y];
    }

    int aliveNeighbours(int x, int y) {
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

    void stepAll(Grid next) {
        step(next, 0, width, 0, height);
    }

    void step(Grid next, int startX, int endX, int startY, int endY) {
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                int a = aliveNeighbours(x, y);
                if (a == 3) {
                    next.setAlive(x, y, true);
                } else if (a == 2) {
                    next.setAlive(x, y, getAlive(x, y));
                } else {
                    next.setAlive(x, y, false);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (getAlive(x, y)) {
                    sb.append('X');
                } else {
                    sb.append('.');
                }
                sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
