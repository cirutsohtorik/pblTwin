import enigma.console.Console;
import enigma.core.Enigma;
import java.util.Random;

public class MazE{

    int rows = 23;
    int cols= 53;
    public void asad (){
        //create board
        char[][] maze = new char[23][53];
        for(int i = 0;i<rows;i++){
            for(int j = 0;j < cols;j++)
            {
                if (i==0|| i ==rows - 1|| j==0||j==cols-1 )
                {
                    maze[i][j]='#';
                }
                else maze[i][j] = ' ';
            }
        }
        Console console = Enigma.getConsole();
        //print board
        for(int i = 0;i<rows;i++){
            for(int j = 0;j < cols;j++)
            {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
        Random rnd = new Random();
        int col = rnd.nextInt(1,rows-1);
        int row =  rnd.nextInt(1,cols-1);

    }

}
