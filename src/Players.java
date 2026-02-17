import enigma.console.Console;
import java.awt.event.KeyEvent;
import enigma.console.TextAttributes;
import java.awt.Color;

public class Players {
    private int aX, aY;
    private int bX, bY;
    private int life = 1000;
    private int score = 0;
    private int mode = -1;
    private Console console;

    public Players(Console console, int startX, int startY) {
        this.console = console;
        this.aX = startX;
        this.aY = startY;
        this.bX = startX;
        this.bY = startY;
    }

    public void render() {
        TextAttributes attr;
        if(mode == 1) {
            attr = new TextAttributes(Color.GREEN, Color.BLACK); // Mod 1: Yeşil
        } else {
            attr = new TextAttributes(Color.MAGENTA, Color.BLACK); // Mod -1: Eflatun
        }
        if (aX == bX && aY == bY) {
            console.getTextWindow().output(aX, aY, 'A',attr);
        } else {
            console.getTextWindow().output(aX, aY, 'A',attr);
            console.getTextWindow().output(bX, bY, 'B',attr);
        }
    }

    private void clear() {
        console.getTextWindow().output(aX, aY, ' ');
        console.getTextWindow().output(bX, bY, ' ');
    }

    public void move(int key, Maze maze) {

        clear();

        int dx = 0, dy = 0;
        if (key == KeyEvent.VK_LEFT) dx = -1;
        else if (key == KeyEvent.VK_RIGHT) dx = 1;
        else if (key == KeyEvent.VK_UP) dy = -1;
        else if (key == KeyEvent.VK_DOWN) dy = 1;

        if (maze.isValidMove(aX + dx, aY + dy)) {
            aX += dx;
            aY += dy;
        }
        int dxB = dx * mode;
        int dyB = dy * mode;

        if (maze.isValidMove(bX + dxB, bY + dyB)) {
            bX += dxB;
            bY += dyB;
        }

        render();
    }
}