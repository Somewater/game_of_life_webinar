import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class GameMultithreading {
    public static void main(String[] args) throws IOException,
            InterruptedException, TimeoutException, ExecutionException {
        String input = args[0];
        String output = args[1];
        long iterations = Long.parseLong(args[2]);
        boolean interactive = args.length > 3;

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println(cores + " cores");
        ExecutorService ex = Executors.newFixedThreadPool(cores);

        Grid grid = Game.readGrid(input);
        grid = iterateGridInParallel(grid, iterations, interactive, cores, ex);
        Game.writeGrid(grid, output);
        ex.shutdownNow();
    }

    private static Grid iterateGridInParallel(Grid grid, long iterations, boolean interactive,
                                              int parallel, ExecutorService ex) throws InterruptedException,
                                                                                       TimeoutException,
                                                                                       ExecutionException {
        Grid grid2 = new Grid(grid.width, grid.height);

        for (long i = 0; i < iterations; i++) {
            stepInParallel(grid, grid2, parallel, ex);

            Grid tmp = grid;
            grid = grid2;
            grid2 = tmp;

            if (interactive) {
                System.out.print((char) 27);
                System.out.print("[2J");
                System.out.println(grid);
                //Thread.sleep(100);
            }
        }

        return grid;
    }

    private static void stepInParallel(Grid current, Grid next, int parallel, ExecutorService ex)
            throws InterruptedException, TimeoutException, ExecutionException {
        List<Callable<Integer>> tasks = new ArrayList<>();
        final int chunkSize = (int) Math.ceil(((float) current.width) / parallel);
        for (int chunk = 0; chunk < current.width; chunk += chunkSize) {
            final int currentChunk = chunk;
            tasks.add(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    current.step(next,
                            currentChunk,
                            Math.min(currentChunk + chunkSize, current.width),
                            0,
                            current.height);
                    return 0;
                }
            });
        }
        for (var future : ex.invokeAll(tasks)) {
            future.get(1, TimeUnit.HOURS);
        }
    }
}
