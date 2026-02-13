import java.util.Random;

public class Main {
    public static void main(String[] args){
        int borderX = 23;
        int borderY= 53;
        //create board
        char[][] maze = new char[24][54];
        for(int i = 0;i<borderX;i++){
            for(int j = 0;j < borderY;j++)
            {
                if (j==1 || i==1 || j==52||i==22)
                {
                    maze[i][j]='#';

                }
                else maze[i][j] = ' ';

            }
        }

        //print board

        for(int i = 1;i<borderX;i++){
            for(int j = 1;j < borderY;j++)
            {
               System.out.print(maze[i][j]);

            }
            System.out.println();

        }

        Random rnd = new Random();
        int col = rnd.nextInt(2,53);
        int row =  rnd.nextInt(2,23);



    }
}
