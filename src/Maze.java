import enigma.console.Console;
import enigma.core.Enigma;
import java.util.Random;

public class Maze {

    private char[][] maze;
    private int rows = 23;
    private int cols = 53;
    private Console console;

    public Maze(Console console) {
        this.console = console;
        this.maze = new char[rows][cols];
    }

    public void createMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    maze[i][j] = '#';
                } else {
                    maze[i][j] = ' ';
                }
            }
        }
    }
    public void render() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                console.getTextWindow().output(j, i, maze[i][j]);
            }
        }
    }
    public boolean isValidMove(int r, int c) {
        // Sınır ve duvar kontrolü
        if (r < 0 || r >= rows || c < 0 || c >= cols) return false;
        if (maze[r][c] == '#') return false; // Duvar kontrolü
        return true;
    }
    public char[][] getMazeData () {
        return maze;
    }
}
