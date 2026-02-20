import enigma.console.Console;
import enigma.core.Enigma;
import java.util.Random;


public class Lasers {

    // --- laser block bilgileri ---
    // aktif laser bloklarinin pozisyonlari
    int[] laserRows;      // her laser blogunun satiri
    int[] laserCols;      // her laser blogunun sutunu
    int[] laserLifetimes; // her laser blogunun kalan omru (100'den geriye sayar)
    int activeLaserCount; // su an haritada kac aktif laser blogu var

    int maxLasers;        // maximum kac laser blogu olabilir ayni anda

    // constructor
    public Lasers(int maxLasers) {
        this.maxLasers = maxLasers;
        laserRows = new int[maxLasers];
        laserCols = new int[maxLasers];
        laserLifetimes = new int[maxLasers];
        activeLaserCount = 0;
    }

    // --- Laser atesle: A'dan B'ye dogru + bloklari yerlestir ---
    // player'dan A ve B pozisyonlarini alir, maze uzerinde + koyar
    public void fire(Player player, char[][] maze) {

        // player'da laser yoksa atesleyemez
        if (player.laserCount <= 0) {
            return;
        }

        // bir laser harca
        player.laserCount = player.laserCount - 1;

        int aRow = player.aRow;
        int aCol = player.aCol;
        int bRow = player.bRow;
        int bCol = player.bCol;

        // A'dan B'ye yon hesapla
        // laser sadece duz cizgide gider (yatay veya dikey)
        int dirRow = 0;
        int dirCol = 0;

        if (bRow > aRow) {
            dirRow = 1;       // asagi
        } else if (bRow < aRow) {
            dirRow = -1;      // yukari
        }

        if (bCol > aCol) {
            dirCol = 1;       // saga
        } else if (bCol < aCol) {
            dirCol = -1;      // sola
        }

        // eger A ve B ayni yerdeyse ateslenemez
        if (dirRow == 0 && dirCol == 0) {
            return;
        }

        // A'dan B'ye dogru ilerle, + bloklari koy
        int currentRow = aRow + dirRow;
        int currentCol = aCol + dirCol;

        // maze sinirlari icinde kal (23 satir, 53 sutun)
        while (currentRow > 0 && currentRow < 22 && currentCol > 0 && currentCol < 52) {

            // laser herhangi bir objeden gecer ama + sadece bos karelere konur
            if (maze[currentRow][currentCol] == ' ') {
                // yeni laser blogu ekle (yer varsa)
                if (activeLaserCount < maxLasers) {
                    laserRows[activeLaserCount] = currentRow;
                    laserCols[activeLaserCount] = currentCol;
                    laserLifetimes[activeLaserCount] = 100; // 100 time unit omur
                    activeLaserCount = activeLaserCount + 1;

                    maze[currentRow][currentCol] = '+'; // haritaya + koy
                }
            }

            // bir sonraki kareye ilerle
            currentRow = currentRow + dirRow;
            currentCol = currentCol + dirCol;
        }
    }

    // --- Her time unit'te cagir: omru biten laserlari kaldir ---
    public void updateLifetimes(char[][] maze) {

        // her laser blogunun omrunu 1 azalt
        for (int i = 0; i < activeLaserCount; i++) {
            laserLifetimes[i] = laserLifetimes[i] - 1;

            // omru bittiyse haritadan kaldir
            if (laserLifetimes[i] <= 0) {
                // haritadan + isareti sil
                maze[laserRows[i]][laserCols[i]] = ' ';

                // bu laseri diziden cikar: sondakini buraya koy
                activeLaserCount = activeLaserCount - 1;
                laserRows[i] = laserRows[activeLaserCount];
                laserCols[i] = laserCols[activeLaserCount];
                laserLifetimes[i] = laserLifetimes[activeLaserCount];

                // ayni index'i tekrar kontrol etmemiz lazim
                i = i - 1;
            }
        }
    }

    // --- Packed laser (@) toplama kontrolu ---
    // player A veya B, @ isaretinin ustune basarsa toplar
    public void checkPickup(Player player, char[][] maze) {

        // A'nin pozisyonunda @ var mi?
        if (maze[player.aRow][player.aCol] == '@') {
            player.collectLaser();              // player'a laser ekle
            maze[player.aRow][player.aCol] = ' '; // haritadan kaldir
        }

        // B'nin pozisyonunda @ var mi?
        if (maze[player.bRow][player.bCol] == '@') {
            player.collectLaser();
            maze[player.bRow][player.bCol] = ' ';
        }
    }

    // --- Laser blogu (+) komsuluk hasari kontrolu ---
    // robotlara (C veya X) komsuysa 50 hasar verir
    // robotRow, robotCol: kontrol edilecek robotun pozisyonu
    // true dondururse robot hasar aldi demek
    public boolean isLaserNeighbor(int robotRow, int robotCol, char[][] maze) {

        // 4 yon kontrol: yukari, asagi, sol, sag
        if (maze[robotRow - 1][robotCol] == '+') {
            return true;  // yukarisinda laser var
        }
        if (maze[robotRow + 1][robotCol] == '+') {
            return true;  // asagisinda laser var
        }
        if (maze[robotRow][robotCol - 1] == '+') {
            return true;  // solunda laser var
        }
        if (maze[robotRow][robotCol + 1] == '+') {
            return true;  // saginda laser var
        }

        return false; // komsuda laser yok
    }
}

