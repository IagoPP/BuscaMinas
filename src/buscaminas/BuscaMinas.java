package src.buscaminas;
import java.util.Random;

/** Logica necesaria para crear un programa de buscaminas.*/
public class BuscaMinas {
    private int tMinas;
    private char mina;
    private char blank;
    private int minesLeft;
    private char loseMineOut;
    private char reveal;
    private char flagin;
    private char questionin;
    private char flagout;
    private char questionout;
    private char action;
    private boolean loseCheck;
    private char[][] gameBoard;
    private char[][] userBoard;

    /** Inicializa valores predeterminados de los atributos que pueden tomar las casillas del tablero */
    public BuscaMinas() {
        mina = '*';
        blank = ' ';
        minesLeft = tMinas;
        reveal = 'r';
        flagin = 'f';
        questionin = '?';
        action = reveal;
        flagout = '¶';
        questionout= '?';
        loseMineOut= '¤';
        loseCheck = false;
    }

    /** Crea tableros de juego y usuario vacíos (todos los valores equivaldran a un caracter de espacio) dados el número de columnas, filas y el total de minas */
    public void setUpBoards(int x, int y, int minas){
        userBoard = new char[y][x];
        gameBoard = new char[y][x];
        tMinas=minas;
        minesLeft=tMinas;

        createBlankBoard(userBoard);
        createBlankBoard(gameBoard);
    }
    /** Rellena las casillas de un tablero con el atributo "blank" */
    private void createBlankBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = blank;
            }
        }
    }

    /** Rellena las casillas del tablero de juego con el valor del atributo "mina" aleatoriamente */
    public void placeMines() {
        Random rng = new Random();
        int count = 0;

        while (count < tMinas) {
            int miney = rng.nextInt(0, gameBoard.length);
            int minex = rng.nextInt(0, gameBoard[0].length);

            if (gameBoard[miney][minex] != mina) {
                gameBoard[miney][minex] = mina;
                count++;
            }
        }
    }

    /** Rellena cada casilla del tablero de juego que no sea una mina con un número según con cuantas minas esté en contacto */
    public void placeNums() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                byte nMinas = 0;
                if (gameBoard[i][j] == blank) {
                    if (i > 0) {
                        if (gameBoard[i - 1][j] == mina) {
                            nMinas++;
                        }
                    }
                    if (i < gameBoard.length - 1) {
                        if (gameBoard[i + 1][j] == mina) {
                            nMinas++;
                        }
                    }
                    if (j > 0) {
                        if (gameBoard[i][j - 1] == mina) {
                            nMinas++;
                        }
                    }
                    if (j < gameBoard[0].length - 1) {
                        if (gameBoard[i][j + 1] == mina) {
                            nMinas++;
                        }
                    }
                    if (i > 0 && j > 0) {
                        if (gameBoard[i - 1][j - 1] == mina) {
                            nMinas++;
                        }
                    }
                    if (i < gameBoard.length - 1 && j < gameBoard[0].length - 1) {
                        if (gameBoard[i + 1][j + 1] == mina) {
                            nMinas++;
                        }
                    }
                    if (i > 0 && j < gameBoard[0].length - 1) {
                        if (gameBoard[i - 1][j + 1] == mina) {
                            nMinas++;
                        }
                    }
                    if (i < gameBoard.length - 1 && j > 0) {
                        if (gameBoard[i + 1][j - 1] == mina) {
                            nMinas++;
                        }
                    }
                    gameBoard[i][j] = Integer.toString(nMinas).charAt(0);
                }
            }
        }
    }

    /** Recoge coordenadas de una casilla del tablero, y un caracter para decidir que tipo de accion se hara sobre ella
     * (revelar una casilla: 'r', poner una bandera: 'f' y poner una interrogación: '?')
     */
    public void chooseAction(char action, int cx, int cy){
        if (action==reveal){
            if (userBoard[cy][cx]==flagout){
                minesLeft++;
            }
            if (Character.isDigit(userBoard[cy][cx]) && Character.getNumericValue(userBoard[cy][cx])>0){
                revealAround(cy, cx);
            }
            userBoard[cy][cx]=gameBoard[cy][cx];         
            if (gameBoard[cy][cx]=='0'){
                revealZeroes(cx, cy);
            }
        }else if (action==flagin){
            if (userBoard[cy][cx]==flagout){
                userBoard[cy][cx]=blank;
                minesLeft++;
            }else if (Character.isDigit(userBoard[cy][cx])==false){
                userBoard[cy][cx]=flagout;
                minesLeft--;
            }
        }else if (action==questionin){
            if (userBoard[cy][cx]==flagout){
                minesLeft++;
            }
            if (userBoard[cy][cx]==questionout){
                userBoard[cy][cx]=blank;
            }else if (Character.isDigit(userBoard[cy][cx])==false){
                userBoard[cy][cx]=questionout;
            }

        }
    }

    /** Pasa al el tablero de usuario desde el de juego todos los numeros 0 que toquen a un 0 determinado por unas coordenadas dadas del tablero */
    private void revealZeroes(int cx, int cy) {

        int sx = cx;
        int sy = cy;
        checkTouching(sy, sx);
        while (true) {
            if (sy < gameBoard.length - 1 && (gameBoard[sy + 1][sx] == '0')) {            
                userBoard[sy][sx]='0';
                sy++;
                checkTouching(sy, sx);
                gameBoard[sy][sx] = 'A';
            } else if (sx < gameBoard[0].length - 1 && (gameBoard[sy][sx + 1] == '0')) {
                
                userBoard[sy][sx]='0';
                sx++;
                checkTouching(sy, sx);
                gameBoard[sy][sx] = 'C';
            } else if (sy > 0 && (gameBoard[sy - 1][sx] == '0')) {
                
                userBoard[sy][sx]='0';
                sy--;
                checkTouching(sy, sx);
                gameBoard[sy][sx] = 'V';
            } else if (sx > 0 && (gameBoard[sy][sx - 1] == '0')) {
                
                userBoard[sy][sx]='0';
                sx--;
                checkTouching(sy, sx);
                gameBoard[sy][sx] = 'D';
            } else if (sy < gameBoard.length - 1 && gameBoard[sy][sx] == 'V') {
                gameBoard[sy][sx]='X';
                userBoard[sy][sx]='0';
                sy++;
            } else if (sx < gameBoard[0].length - 1 && gameBoard[sy][sx] == 'D') {
                gameBoard[sy][sx]='X';
                userBoard[sy][sx]='0';
                sx++;
            } else if (sy > 0 && gameBoard[sy][sx] == 'A') {
                gameBoard[sy][sx]='X';
                userBoard[sy][sx]='0';
                sy--;
            } else if (sx > 0 && gameBoard[sy][sx] == 'C') {
                gameBoard[sy][sx]='X';
                userBoard[sy][sx]='0';
                sx--;
            } else {
                break;
            }
        }
    }

    /** Comprueba todas las casillas que rodeen a un 0 dadas sus cordenadas y pasa sus valores del tablero de juego al de usuario  */
    private void checkTouching(int sy, int sx){
        if (sy < gameBoard.length - 1 && Character.isDigit(gameBoard[sy+1][sx]) && Character.getNumericValue(gameBoard[sy+1][sx])>0) {
            userBoard[sy+1][sx]=gameBoard[sy+1][sx];
        }
        if (sx < gameBoard[0].length - 1  && Character.isDigit(gameBoard[sy][sx+1]) && Character.getNumericValue(gameBoard[sy][sx+1])>0) {
            userBoard[sy][sx+1]=gameBoard[sy][sx+1];
        }
        if (sy > 0  && Character.isDigit(gameBoard[sy-1][sx]) && Character.getNumericValue(gameBoard[sy-1][sx])>0) {
            userBoard[sy-1][sx]=gameBoard[sy-1][sx];
        }
        if (sx > 0  && Character.isDigit(gameBoard[sy][sx-1]) && Character.getNumericValue(gameBoard[sy][sx-1])>0) {
            userBoard[sy][sx-1]=gameBoard[sy][sx-1];
        }
        if (sy < gameBoard.length - 1 && sx < gameBoard[0].length - 1  && Character.isDigit(gameBoard[sy+1][sx+1]) && Character.getNumericValue(gameBoard[sy+1][sx+1])>0) {
            userBoard[sy+1][sx+1]=gameBoard[sy+1][sx+1];
        }
        if (sy > 0 && sx > 0  && Character.isDigit(gameBoard[sy-1][sx-1]) && Character.getNumericValue(gameBoard[sy-1][sx-1])>0) {
            userBoard[sy-1][sx-1]=gameBoard[sy-1][sx-1];
        }
        if (sy < gameBoard.length - 1 && sx > 0 && Character.isDigit(gameBoard[sy+1][sx-1]) && Character.getNumericValue(gameBoard[sy+1][sx-1])>0) {
            userBoard[sy+1][sx-1]=gameBoard[sy+1][sx-1];
        }
        if (sy > 0 && sx < gameBoard[0].length - 1 && Character.isDigit(gameBoard[sy-1][sx+1]) && Character.getNumericValue(gameBoard[sy-1][sx+1])>0) {
            userBoard[sy-1][sx+1]=gameBoard[sy-1][sx+1];
        }
    }

    /** Dadas las coordenadas de una casilla pasa al tablero de usuario los parametros contenidos en 
    tablero de juego a todas las que la rodeen siempre que su contenido no coincida con el atributo "flagout"  */
    private void revealAround(int sy, int sx){
        if (sy < gameBoard.length - 1 && (Character.isDigit(gameBoard[sy+1][sx]) || gameBoard[sy+1][sx]=='*') && userBoard[sy+1][sx]!=flagout) {
            userBoard[sy+1][sx]=gameBoard[sy+1][sx];
            if (userBoard[sy+1][sx]=='*') {checkedMine(sy+1, sx);}
            if (userBoard[sy+1][sx]=='0') {revealZeroes(sx, sy+1);}
        }
        if (sx < gameBoard[0].length - 1  && (Character.isDigit(gameBoard[sy][sx+1]) || gameBoard[sy][sx+1]=='*') && userBoard[sy][sx+1]!=flagout) {
            userBoard[sy][sx+1]=gameBoard[sy][sx+1];
            if (userBoard[sy][sx+1]=='*') {checkedMine(sy, sx+1);}
            if (userBoard[sy][sx+1]=='0') {revealZeroes(sx+1, sy);}
        }
        if (sy > 0  && (Character.isDigit(gameBoard[sy-1][sx]) || gameBoard[sy-1][sx]=='*') && userBoard[sy-1][sx]!=flagout) {
            userBoard[sy-1][sx]=gameBoard[sy-1][sx];
            if (userBoard[sy-1][sx]=='*') {checkedMine(sy-1, sx);}
            if (userBoard[sy-1][sx]=='0') {revealZeroes(sx, sy-1);}
        }
        if (sx > 0  && (Character.isDigit(gameBoard[sy][sx-1]) || gameBoard[sy][sx-1]=='*') && userBoard[sy][sx-1]!=flagout) {
            userBoard[sy][sx-1]=gameBoard[sy][sx-1];
            if (userBoard[sy][sx-1]=='*') {checkedMine(sy, sx-1);}
            if (userBoard[sy][sx-1]=='0') {revealZeroes(sx-1, sy);}
        }
        if (sy < gameBoard.length - 1 && sx < gameBoard[0].length - 1  && (Character.isDigit(gameBoard[sy+1][sx+1]) || gameBoard[sy+1][sx+1]=='*') && userBoard[sy+1][sx+1]!=flagout) {
            userBoard[sy+1][sx+1]=gameBoard[sy+1][sx+1];
            if (userBoard[sy+1][sx+1]=='*') {checkedMine(sy+1, sx+1);}
            if (userBoard[sy+1][sx+1]=='0') {revealZeroes(sx+1, sy+1);}
        }
        if (sy > 0 && sx > 0  && (Character.isDigit(gameBoard[sy-1][sx-1]) || gameBoard[sy-1][sx-1]=='*') && userBoard[sy-1][sx-1]!=flagout) {
            userBoard[sy-1][sx-1]=gameBoard[sy-1][sx-1];
            if (userBoard[sy-1][sx-1]=='*') {checkedMine(sy-1, sx-1);}
            if (userBoard[sy-1][sx-1]=='0') {revealZeroes(sx-1, sy-1);}
        }
        if (sy < gameBoard.length - 1 && sx>0 && (Character.isDigit(gameBoard[sy+1][sx-1]) ||  gameBoard[sy+1][sx-1]=='*') && userBoard[sy+1][sx-1]!=flagout) {
            userBoard[sy+1][sx-1]=gameBoard[sy+1][sx-1];
            if (userBoard[sy+1][sx-1]=='*') {checkedMine(sy+1, sx-1);}
            if (userBoard[sy+1][sx-1]=='0') {revealZeroes(sx-1, sy+1);}
        }
        if (sy > 0 && sx < gameBoard[0].length - 1 && (Character.isDigit(gameBoard[sy-1][sx+1]) || gameBoard[sy-1][sx+1]=='*') && userBoard[sy-1][sx+1]!=flagout) {
            userBoard[sy-1][sx+1]=gameBoard[sy-1][sx+1];
            if (userBoard[sy-1][sx+1]=='*') {checkedMine(sy-1, sx+1);}
            if (userBoard[sy-1][sx+1]=='0') {revealZeroes(sx+1, sy-1);}
        }
    }

    /** Determina si se encontro una mina al usar el metodo "revealAround" */
    private void checkedMine(int sy, int sx){
        userBoard[sy][sx]=loseMineOut;
        loseCheck=true;
    }

    /** Determina si el juego ha acabado en victoria */
    public boolean winCon(){
        int numCells=0;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (Character.isDigit(userBoard[i][j]) || userBoard[i][j]=='X') {
                    numCells++;
                }
            }
        }
        if (numCells==(userBoard.length*userBoard[0].length)-tMinas) {
            return true;
        }else{
            return false;
        }
    }

    /** Enseña las minas que no se han cubierto con una bandera, la mina que ha hecho perder al jugador y las banderas mal puestas con un caracter dado */
    public void loseBoard(char failedMine){
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                if (userBoard[i][j]==flagout && gameBoard[i][j]!=mina) {
                    userBoard[i][j]=failedMine;
                }
                if (userBoard[i][j]!=flagout && (gameBoard[i][j]==mina && userBoard[i][j]!=loseMineOut)) {
                    userBoard[i][j]=mina;
                }
            }
        }
    }

    /** Devuelve el array bidimensional en el que se almacenan los parametros del Tablero de Usuario */
    public char[][] getUserBoard() {
        return this.userBoard;
    }
    /** Devuelve el array bidimensional en el que se almacenan los parametros del Tablero de Juego */
    public char[][] getGameBoard() {
        return this.gameBoard;
    }
    /** Devuelve el numero de minas que quedan por marcar con una casilla bandera */
    public int getMinesLeft() {
        return minesLeft;
    }
    /** Devuelve el parametro asociado al atributo "mina" que determina si una casilla del tablero de juego es una mina */
    public char getMina() {
        return mina;
    }
    /** Devuelve el parametro asociado a la accion de revelar casillas del tablero de usuario usado por el metodo "chooseAction" */
    public char getReveal() {
        return reveal;
    }
    /** Devuelve el valor del atributo "action" usado por el metodo "chooseAction" para determinar la accion que se hara sobre una casilla dadas sus coordenadas*/
    public char getAction() {
        return action;
    }
    /** Devuelve true si se ha revelado una mina al seleccionar una casilla numérica ya revelada */
    public boolean getLoseCheck(){
        return loseCheck;
    }
    /** Devuelve el caracter usado para marcar una mina que se ha revelado por el usuario */
    public char getLoseMineOut() {
        return loseMineOut;
    }

    /** Recoge el valor del atributo "action" que determinara la accion a tomar en el metodo "chooseAction" */
    public void setAction(char action) {
        this.action = action;
    }
}