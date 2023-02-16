import java.util.Random;

public class BuscaMinas{
    private int x;
    private int y;
    private int nminas;
    private char mina;
    private char blank;
    private int numCell;
    private char[][] board;

    BuscaMinas(){
        x=12;
        y=10;
        nminas=10;
        mina='*';
        blank=' ';
        numCell=0;
        board = new char[y][x];
        createBlankBoard();
    }

    public void createBlankBoard(){  
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = blank;
            }
        }
    }

    public void placeMines() {
        Random rng = new Random();
        int count = 0;

        while (count < nminas) {
            int miney = rng.nextInt(0, board.length);
            int minex = rng.nextInt(0, board[0].length);

            if (board[miney][minex] != mina) {
                board[miney][minex] = mina;
                count++;
            }
        }
    }

    public void placeNums(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++){
                int minen=0;
                if (board[i][j]==blank){
                    if(i>0){
                        if(board[i-1][j]==mina) {minen++;}
                    }
                    if(i<board.length-1){
                        if(board[i+1][j]==mina) {minen++;}
                    }
                    if(j>0){
                        if(board[i][j-1]==mina) {minen++;}
                    }
                    if(j<board.length-1){
                        if(board[i][j+1]==mina) {minen++;}
                    }
                    if(i>0 && j>0){
                        if(board[i-1][j-1]==mina) {minen++;}
                    }
                    if(i<board.length-1 && j<board[0].length-1){
                        if(board[i+1][j+1]==mina) {minen++;}
                    }
                    if(i>0 && j<board[0].length-1){
                        if(board[i-1][j+1]==mina) {minen++;}
                    }
                    if(i<board.length-1 && j>0){
                        if(board[i+1][j-1]==mina) {minen++;}
                    }
                    numCell=minen;
                    board[i][j]=Integer.toString(numCell).charAt(0);
                }
            }
        }  
    }

    //getters e setters
    public char[][] getBoard() {
        return board;
    }
}