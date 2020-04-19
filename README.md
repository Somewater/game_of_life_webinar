#### Run as:
```
cd java && javac *.java && cd ..
time python python/game.py grid-10.txt grid-out.txt 100000
time java -cp java Game grid-10.txt grid-out.txt 100000
time java -cp java GameMultithreading grid-1000.txt grid-out-1000.txt 1000
```