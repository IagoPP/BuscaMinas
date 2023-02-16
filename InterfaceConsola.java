public class InterfaceConsola {
    public void printBoardCool(char[][] board){
        System.out.print("   ");
        for (int i = 0; i < board[0].length; i++) {
            System.out.print("|");
            //make this a sepparate method for increased post-interface compatibility?
            if ((i+1)/100<1) {System.out.print("0");}
            if ((i+1)/10<1) {System.out.print("0");}
            System.out.print(i+1);
        }
        System.out.println("|");
        printSpaceLine(board);
        for (int i = 0; i < board.length; i++) {
            if ((i+1)/100<1) {System.out.print("0");}
            if ((i+1)/10<1) {System.out.print("0");}
            System.out.print((i+1)+"|");
            for (int j = 0; j < board[0].length; j++) {
                System.out.printf(" %c |", board[i][j]);
            }
            System.out.println();
            printSpaceLine(board);
        }  
    }

    public void printSpaceLine(char[][] board){
        for (int j = 0; j <= board[0].length; j++) {
            System.out.print("---+");
        }         
        System.out.println(); 
    }

    public void printBoard(char[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.printf("|%c", board[i][j]);
            }
            System.out.println("|");
        }  
    }

    public static void main(String[] args) {
        BuscaMinas bm = new BuscaMinas();
        InterfaceConsola ic = new InterfaceConsola();

        bm.placeMines();
        bm.placeNums();
        ic.printBoardCool(bm.getBoard());
    }
}
