#### Run as:
```
# Compile all versions:
cd java && javac *.java && cd ..
cd cpp && g++ -O3 grid.h main.cpp && cd ..

# Prepare initial grids:
generate_grid.py grid-1000.txt 1000
generate_grid.py grid-10.txt 10

# Compare 10x10 grid calculation:
time python python/game.py grid-10.txt grid-out.txt 100000
time java -cp java Game grid-10.txt grid-out.txt 100000
time cpp/a.out grid-10.txt grid-out.txt 100000

# Compare 1000x1000 grid calculation:
time java -cp java Game grid-1000.txt grid-out-1000.txt 1000
time java -cp java GameMultithreading grid-1000.txt grid-out-1000.txt 1000
```