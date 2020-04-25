#include <iostream>
#include <fstream>
#include "grid.h"
#include "math.h"

using namespace std;

int main(int argc, char *argv[]) {
    char* input = argv[1];
    char* output = argv[2];
    int iterations = stoi(argv[3]);
    cout << "input: " << input << ", output: " << output << ", iterations: " << iterations << endl;

    ifstream input_file(input);
    string line;
    int width = 0;
    int height = 0;
    while (getline(input_file, line)) {
        if (line.size() > 0) {
            width = ceil(line.size() / 2.0);
            height++;
        }
    }
    input_file.close();
    Grid* grid = new Grid(width, height);
    Grid* grid2 = new Grid(width, height);

    ifstream input_file2(input);
    int y = 0;
    while (getline(input_file2, line)) {
        int x = 0;
        for (int i = 0; i < line.size(); i+=2) {
            char c = line[i];
            bool alive = c == 'X';
            grid->setAlive(x, y, alive);
            x++;
        }
        y++;
    }
    input_file2.close();

    for (int j = 0; j < iterations; ++j) {
        grid->stepAll(grid2);
        Grid* tmp = grid2;
        grid2 = grid;
        grid = tmp;
    }

    ofstream output_file(output);
    for (int k = 0; k < grid->height; ++k) {
        for (int i = 0; i < grid->width; ++i) {
            bool alive = grid->getAlive(i, k);
            output_file << (alive ? 'X' : '.');
            output_file << ' ';
        }
        output_file << '\n';
    }
    output_file.close();

    return 0;
}
