import java.util.*;
//import java.lang.Math.*;


public class Board{

    private int[][] board;
    private int row;
    private int col;
    private int aiScore;
    private int playerScore;
    public String player;  
    public int value;
    public int treeDepth;
    public Board parent;
    public ArrayList<Board> children;

    // default constuctor for the board
    public Board(Board b){

        board = new int[b.board.length][b.board[0].length];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = b.board[i][j];
            }
        }

        player = b.getPlayer();
        row = b.getRow();
        col = b.getCol();
        aiScore = b.getMax();
        playerScore = b.getMin();
        parent = null;
        value = 0;
        treeDepth = 0;
        children = new ArrayList<Board>();

    }


    // initialize the board based on input 
    public Board(int a, int b){

        int tempRow = (a * 2) + 1;
        int tempCol = (b * 2) + 1;

        board = new int[tempRow][tempCol];
        row = 0;
        col = 0;
        aiScore = 0;
        playerScore = 0;
        player = "min";
        value = 0;
        treeDepth = 0;
        parent = null;
        children = new ArrayList<Board>();
        setBoard();
    }

    // creating random values
    private void setBoard(){
        Random rnd = new Random(System.currentTimeMillis());
        for(int i = 0; i < board.length; i++){
            if (i % 2 == 0){
                for (int j = 0; j < board[i].length; j++){
                    board[i][j] = 0;
                }
            }else{
                for (int j = 0; j < board[i].length; j++){
                    if (j % 2 == 0){
                        board[i][j] = 0;
                    }
                    else{
                        board[i][j] = rnd.nextInt(5) + 1;
                    }
                }
            }
        }
    }

    // check if no more moves are possible 
    public boolean gameOver(){
        for (int i = 0; i < board.length; i++){
            if (i % 2 == 0){
                for (int j = 1; j < board[i].length; j+=2){
                    if (board[i][j] == 0){
                        return false;
                    }
                }
            }else {
                for (int j = 0; j < board[i].length; j+=2){
                    if (board[i][j] == 0){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // print out the board interface
    public void printBoard(){
        System.out.print(" ");
        System.out.println("");
        for (int i = 0; i < board.length; i++){
            if (i % 2 == 0){
                for (int j = 0; j < board[i].length; j++){
                    if (j % 2 == 0){
                        System.out.print("*");
                    }else if(board[i][j]> 0){
                        System.out.print(" - ");
                    }
                    else {
                        System.out.print("   ");
                    }
                }
            }
            else{
                for (int j = 0; j < board[i].length; j++){
                    if (j % 2 != 0){ 
                        System.out.print(" " + board[i][j] + " ");
                    } else if(board[i][j]> 0) {
                        System.out.print("x");
                    }else {
                        System.out.print(" ");
                    }
                }

            }
            System.out.println("");
        }

        System.out.println("Player Score: " + playerScore + "\tAI Score: " + aiScore);
        System.out.println("");
    }


    // changing value whenevern it is called
    public void score(){
        value = aiScore - playerScore;
    }


    // checks to see if the it is possibe to move in a certain direction 
    public boolean validMove(int a, int b){ 
        if (a < 0 || a > board.length || b < 0 || b > board[0].length){
            return false;
        }
        if(!(a % 2 == 0 ^ b % 2 == 0)){ 
            return false;
        }
        if(board[a][b] > 0){
            return false;
        }

        board[a][b] = 1;
        row = a;
        col = b;
        scoreTracker(a, b);
        setPlayer(player);
        return true;
    }

    //Keep track of which player got the score
    public void scoreTracker(int a, int b){
        if (a % 2 == 0){
            if(boxed(a-1, b)){
                if (player == "max"){
                    aiScore += board[a-1][b];
                }else{
                    playerScore += board[a-1][b];
                }
            }if(boxed(a+1, b)){
                if (player == "max"){
                    aiScore += board[a+1][b];
                }else{
                    playerScore += board[a+1][b];
                }
            }
        }else {
            if(boxed(a, b-1)){
                if (player == "max"){
                    aiScore += board[a][b-1];
                }else{
                    playerScore += board[a][b-1];
                }
            }if(boxed(a, b+1)){
                if (player == "max"){
                    aiScore += board[a][b+1];
                }else{
                    playerScore += board[a][b+1];
                }
            }
        }
    }

    // check to see if the given area is boxed and if it is return true
    public boolean boxed(int a, int b){
        if (a > 0 && a < board.length && b > 0 && b < board[a].length){
            return board[a-1][b] > 0 && board[a+1][b] > 0 && board[a][b-1] > 0 && board[a][b+1] > 0;
        }
        return false;
    }



    public void addChildren(){
        for (int i = 0; i < board.length; i++){
            if (i % 2 == 0){
                for (int j = 1; j < board[i].length; j+=2){
                    if (board[i][j] < 1){
                        child(i, j);
                    }
                }
            }else {
                for (int j = 1; j < board[i].length; j+=2){
                    if (board[i][j] < 1){
                        child(i,j);
                    }
                }
            }
        }
    }


    public void child(int a, int b){
        Board c = new Board(this);
        c.treeDepth = treeDepth + 1;
        c.parent = this;
        boolean posMove = c.validMove(a, b);
        if(posMove == true){
            children.add(c);
        }
    }


    /******************************************
    Getters and setters
    Mainly to change the player type and to get values
    min = player, max = AI
    ********************************************/
    public void setPlayer(String player){
        if (player == "min"){ player = "max";}
        else {player = "min";}
    }

    public String getPlayer(){
        return player;
    }

    public int getMax(){
        return aiScore;
    }

    public int getMin(){
        return playerScore;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }
}