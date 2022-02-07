
public class MinMaxAlgo{
    private static int max = Integer.MAX_VALUE;
    private static int min = Integer.MIN_VALUE;
    Board temp;


    // takes in board and the amount of plies the alogrithm will search ahead
    public static int[] bestMove(Board b, int a){
        int move[] = {0,0};
    
        Board root = new Board(b);
        int aiMove = minMax(root, a);

        for(Board temp: root.children){
            if (temp.value == aiMove){
                move[0] = temp.getRow();
                move[1] = temp.getCol();
                return move;
            }
        }

        /*
        for(int i =0; i < root.children.size(); i++){
            temp = root.children.get(i);
            if (temp.value == aiMove){
                move[0] = temp.getRow();
                move[1] = temp.getCol();
                return move;
            }

        }*/

        return move;
    }


    // returns the best possible move to make based on value 
    public static int minMax(Board b, int a){
        b.addChildren();
        if(b.treeDepth >= a || b.children.isEmpty()){
            b.score();
            return b.value;
        }

        if(b.getPlayer() == "max"){
            int bestMove = min;
            int maxMove; 
            for(Board temp: b.children){
                maxMove = minMax(temp, a-1);
                if(maxMove > bestMove){
                    bestMove = maxMove;
                }
            }


            /*
            for(int i = 0; i < b.children.size(); i++){
                temp = b.children.get(i);
                move = minMax(b, a-1);
                if (move > bestMove) {
                    bestMove = move;
                }
            }*/
            b.value = bestMove;
            return bestMove;
        }else {
            int bestMove = max;
            int minMove;
            for(Board temp: b.children){
                minMove = minMax(temp, a-1);
                if(minMove < bestMove){
                    bestMove = minMove;
                }
            }
            b.value = bestMove;
            return bestMove;
        }
    }
}