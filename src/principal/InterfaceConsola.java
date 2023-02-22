package src.principal;
import java.util.Scanner;

import src.buscaminas.BuscaMinas;

public class InterfaceConsola {
    //imprimir tablero
    public void printBoardCool(char[][] board, int minesLeft){
        calcZeroes(minesLeft);
        for (int i = 0; i < board[0].length; i++) {
            System.out.print("|");
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
        Scanner sc = new Scanner(System.in);

        //Presentacion juego
        //Reglas
        System.out.println(" ____  __ __  __    ___  ___  ___  ___ __ __  __  ___   __ ");
        System.out.println(" || )) || || (( \\  //   // \\\\ ||\\\\//|| || ||\\ || // \\\\ (( \\");
        System.out.println(" ||=)  || ||  \\\\  ((    ||=|| || \\/ || || ||\\\\|| ||=||  \\\\ ");
        System.out.println(" ||_)) \\\\_// \\_))  \\\\__ || || ||    || || || \\|| || || \\_))");
        System.out.println("\nBienvenido al BuscaMinas, el objetivo del juego es descubrir todas las casillas numéricas del tablero sin acertar en ninguna mina. cada casilla numérica indica el número de minas con las que está en contacto, usa esta información para llegar a la victoria.");
        //Como jugar.
        System.out.println("\nNORMAS DEL JUEGO:");
        System.out.println("\nPara interactuar con el tablero introduce las coordenadas de la casilla correspondiente (primero columnas y luego filas) y un caracter según la acción que deseas tomar sobre ella: ");
        System.out.println("r - revelar el contenido de la casilla");
        System.out.println("f - poner una bandera para indicar una posible mina");
        System.out.println("? - Poner una marca de uso orientativo");
        System.out.println("Ejemplo de entrada: 5 6 r");
        System.out.println("\nEn la parte superior izquierda del tablero se indica el número de minas que quedarían según las que ya hayas marcado con banderas.");
        System.out.println("También puedes seleccionar una casilla numérica ya desvelada con la acción (r), para así desvelar todas las casillas que la rodeen que no estén marcadas con una bandera como atajo. ");
        System.out.println("\n¡A Jugar!");

        //Seleccionar dimensiones del tablero y crearlo
        System.out.println("\nSelecciona el modo de juego:");
        System.out.println("1 - Principiante");
        System.out.println("2 - Intermedio");
        System.out.println("3 - Experto");
        System.out.println("4 - Personalizado");
        System.out.println();
        byte select = sc.nextByte();
        switch (select) {
            case 1:
                bm.setUpBoards(8, 8, 10);
            break;
            case 2:
                bm.setUpBoards(16, 16, 40);
            break;
            case 3:
                bm.setUpBoards(31, 16, 99);
            break;
            case 4:
                System.out.println("Selecciona el número de columnas, filas y minas que tendrá tu tablero personalizado en este mismo orden: ");
                bm.setUpBoards(sc.nextInt(), sc.nextInt(), sc.nextInt());
            break;       
            default:
                break;
        }

        //Crear tablero de juego
        bm.placeMines();
        bm.placeNums();
        ic.printBoardCool(bm.getUserBoard(), bm.getMinesLeft());

        //Bucle Jugable
        int cx=sc.nextInt()-1;
        int cy=sc.nextInt()-1;
        bm.setAction(sc.next().charAt(0));
        boolean win=false;

        while (bm.getGameBoard()[cy][cx]!=bm.getMina() || bm.getAction()!=bm.getReveal()) { 
                
            bm.chooseAction(bm.getAction(), cx, cy);
            ic.printBoardCool(bm.getUserBoard(), bm.getMinesLeft());

            if (bm.winCon()) {
                win=true;
                break;
            }
            if (bm.getLoseCheck()) {
                win=false;
                break;
            }
            cx=sc.nextInt()-1;
            cy=sc.nextInt()-1;
            bm.setAction(sc.next().charAt(0));
        }
        sc.close();

        //Final Juego
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
            if (bm.getLoseCheck()==false){
                bm.getUserBoard()[cy][cx]=bm.getLoseMineOut();
            }           
            ic.printBoardCool(bm.getUserBoard(), bm.getMinesLeft());
            System.out.println("You Lose!");
        }
    }
}
