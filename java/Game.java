import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Game {
    public static void main(String[] args) throws IOException, InterruptedException {
        String input = args[0];
        String output = args[1];
        long iterations = Long.parseLong(args[2]);
        boolean interactive = args.length > 3;

        int width = 0;
        int height = 0;

        try (var reader = Files.newBufferedReader(new File(input).toPath())) {
            while (reader.ready()) {
                String line = reader.readLine();
                if (width == 0) {
                    width = line.length() / 2;
                }
                if (!line.isEmpty()) {
                    height++;
                }
            }
        }

        Grid grid = new Grid(width, height);

        try (var reader = Files.newBufferedReader(new File(input).toPath())) {
            int y = 0;
            while (reader.ready()) {
                String line = reader.readLine();
                for (int x = 0; x < width; x++) {
                    char c = line.charAt(x * 2);
                    boolean alive = c == 'X';
                    grid.setAlive(x, y, alive);
                }
                y++;
            }
        }

        Grid grid2 = new Grid(width, height);

        for (long i = 0; i < iterations; i++) {
            grid.stepAll(grid2);

            Grid tmp = grid;
            grid = grid2;
            grid2 = tmp;

            if (interactive) {
                System.out.print((char) 27);
                System.out.print("[2J");
                System.out.println(grid);
                Thread.sleep(100);
            }
        }

        try (var writer = Files.newBufferedWriter(new File(output).toPath())) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    boolean a = grid.getAlive(x, y);
                    writer.write(a ? 'X' : '.');
                    writer.write(' ');
                }
                writer.write('\n');
            }
        }
    }
}
