import enigma.console.Console;
import enigma.core.Enigma;

public class Player {

  

    int aRow;       // A's row position (up-down)
    int aCol;       // A's column position (left-right)
    int bRow;       // B's row position
    int bCol;       // B's column position
    int life;       // player's health points
    int score;      // player's score
    int mode;       // B's movement mode: 1 or -1
    int laserCount; // collected laser count

    Console console;  // enigma console for keyboard input

    

    public Player(int startRow, int startCol) {
        aRow = startRow;
        aCol = startCol;
        bRow = startRow;   // A and B start at the same spot
        bCol = startCol;
        life = 1000;       // starts with 1000 HP
        score = 0;         // starts with 0 points
        mode = 1;          // default mode: B moves same direction as A
        laserCount = 0;    // no lasers at the beginning

        console = Enigma.getConsole();  // get the enigma console
    }

    // --- Move A with arrow keys, B follows based on mode ---
    // dirX and dirY: direction from keyboard (-1, 0 or 1)
    // maze: the game board array for wall checking

    public void move(int dirX, int dirY, char[][] maze) {

        // calculate where A wants to go
        int newARow = aRow + dirX;
        int newACol = aCol + dirY;

        // wall check for A: only move if it's not a wall
        if (maze[newARow][newACol] != '#') {
            aRow = newARow;
            aCol = newACol;
        }

        // calculate B's movement based on mode
        // mode=1: same direction, mode=-1: opposite direction
        int newBRow = bRow + (dirX * mode);
        int newBCol = bCol + (dirY * mode);

        // wall check for B
        if (maze[newBRow][newBCol] != '#') {
            bRow = newBRow;
            bCol = newBCol;
        }
    }

    // mode 1 and mode -1

    public void changeMode() {
        if (mode == 1) {
            mode = -1;     // switch to opposite direction
        } else {
            mode = 1;      // switch to same direction
        }
    }

    //lose health when contact with enemy

    public void loseLife(int amount) {
        life = life - amount;
    }

    // check if dead

    public boolean isDead() {
        if (life <= 0) {
            return true;   // dead
        }
        return false;       // still alive
    }

    // treasure

    public void collectTreasure(int treasureType) {
        if (treasureType == 1) {
            score = score + 3;       // treasure '1' gives 3 points
        } else if (treasureType == 2) {
            score = score + 10;      // treasure '2' gives 10 points
        } else if (treasureType == 3) {
            score = score + 30;      // treasure '3' gives 30 points
        }
    }

    //collect packed laser

    public void collectLaser() {
        laserCount = laserCount + 1;
    }

    // check if a and b are in the same square

    public boolean areTogether() {
        if (aRow == bRow && aCol == bCol) {
            return true;
        }
        return false;
    }
}
