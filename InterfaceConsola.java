import java.util.Scanner;

public class InterfaceConsola {
    public void printBoardCool(char[][] board, int minesLeft){
        calcZeroes(minesLeft);
        for (int i = 0; i < board[0].length; i++) {
            System.out.print("|");
            //make this a sepparate method for increased post-interface compatibility?
            calcZeroes(i+1);
        }
        System.out.println("|");
        printSpaceLine(board);
        for (int i = 0; i < board.length; i++) {
            calcZeroes(i+1);
            System.out.print("|");
            for (int j = 0; j < board[0].length; j++) {
                System.out.printf(" %c |", board[i][j]);
            }
            System.out.println();
            printSpaceLine(board);
        }  
    }

    public void calcZeroes(int num){
        if ((num)/100<1) {System.out.print("0");}
        if ((num)/10<1) {System.out.print("0");}
        System.out.print(num);
    }

    public void printSpaceLine(char[][] board){
        for (int j = 0; j <= board[0].length; j++) {
            System.out.print("---+");
        }         
        System.out.println(); 
    }

    public static void main(String[] args) {
        BuscaMinas bm = new BuscaMinas();
        InterfaceConsola ic = new InterfaceConsola();

        //game setup
        bm.placeMines();
        bm.placeNums();
        ic.printBoardCool(bm.getUserBoard(), bm.getMinesLeft());    

        //gameplay loop
        Scanner sc = new Scanner(System.in);
        int cx=sc.nextInt()-1;
        int cy=sc.nextInt()-1;
        bm.setAction(sc.next().charAt(0));
        boolean win=false;

        while (cx>-1 && (bm.getGameBoard()[cy][cx]!=bm.getMina() || bm.getAction()!=bm.getReveal())) {          
            bm.chooseAction(bm.getAction(), cx, cy);
            ic.printBoardCool(bm.getUserBoard(), bm.getMinesLeft());
            if (bm.winCon()) {
                win=true;
                break;
            }
            cx=sc.nextInt()-1;
            cy=sc.nextInt()-1;
            bm.setAction(sc.next().charAt(0));
        }

        //game finish
        if (win==true){
            System.out.println("$$\\     $$\\                                       $$\\           $$\\ ");
            System.out.println("\\$$\\   $$  |                                      \\__|          $$ |");
            System.out.println(" \\$$\\ $$  /$$$$$$\\  $$\\   $$\\       $$\\  $$\\  $$\\ $$\\ $$$$$$$\\  $$ |");    
            System.out.println("  \\$$$$  /$$  __$$\\ $$ |  $$ |      $$ | $$ | $$ |$$ |$$  __$$\\ $$ |");
            System.out.println("   \\$$  / $$ /  $$ |$$ |  $$ |      $$ | $$ | $$ |$$ |$$ |  $$ |\\__|");
            System.out.println("    $$ |  $$ |  $$ |$$ |  $$ |      $$ | $$ | $$ |$$ |$$ |  $$ |    ");
            System.out.println("    $$ |  \\$$$$$$  |\\$$$$$$  |      \\$$$$$\\$$$$  |$$ |$$ |  $$ |$$\\ ");
            System.out.println("    \\__|   \\______/  \\______/        \\_____\\____/ \\__|\\__|  \\__|\\__|");
        }else{
            bm.loseBoard('X');
            bm.getUserBoard()[cy][cx]='¤';
            ic.printBoardCool(bm.getUserBoard(), bm.getMinesLeft());
            System.out.println("You Lose!");
        }
    }
}
