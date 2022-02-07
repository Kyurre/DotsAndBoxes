import java.util.Scanner;

public class Game{

    static boolean possible; 

    public static void main (String[] args){
        Scanner scan = new Scanner(System.in);
        int rows = 0;
        int cols = 0;
        int plies = 0;
        int moveRow;
        int moveCol;
        int[] aiMove = new int[2];
        Board root;


        // asks the user of board size row and col 
        System.out.print("Enter the row size: ");
        rows = scan.nextInt();
        System.out.print("\nEnter the column size: ");
        cols = scan.nextInt();
        System.out.print("\nEnter plies for AI: ");
        plies = scan.nextInt();
        System.out.println("");
        

        // creating the board and initializing the random values
        root = new Board(rows, cols);
        root.printBoard();


        // playing the game
        while(!root.gameOver()){ 

            // asking player to make possible move will keep on asking if the move is not possible 
            do{
                System.out.print("Player move: \n");
                System.out.print("Enter your move row: ");
                moveRow = scan.nextInt();
                System.out.print("\nEnter your move col: ");
                moveCol = scan.nextInt(); 

                possible = true;
                possible = root.validMove(moveRow, moveCol);
            }while(possible != true);
            System.out.println("");
            root.printBoard();


            // AI's move
            if(!root.gameOver()){
                System.out.print("AI's Move: ");
                aiMove = MinMaxAlgo.bestMove(root, plies);
                System.out.println(aiMove[0]+ "," + aiMove[1]);
                root.validMove(aiMove[0], aiMove[1]);
                root.printBoard();
            }
        }

        // Printing the winner of the game
        if (root.getMax() > root.getMin()){
            System.out.println("AI WON");
        }
        if (root.getMin() > root.getMax()){
            System.out.println("Player Won");
        }
        if (root.getMin() == root.getMax()){
            System.out.println("Tie");
        }
        scan.close();
    }
}